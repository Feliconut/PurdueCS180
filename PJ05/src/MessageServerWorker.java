import Exceptions.*;
import Field.*;
import Request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Set;
import java.util.UUID;

public class MessageServerWorker extends Thread {

    private final Socket socket;
    private final MessageSystem system;
    private User currentUser;

    public MessageServerWorker(Socket socket, MessageSystem system) {
        this.socket = socket;
        this.system = system;
    }

    @Override
    public void run() {
        System.out.println(socket.toString() + " start to run");
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        while (true) {
            try {
                Request request;
                // Read a new request from socket.
                if (objectInputStream == null) {
                    objectInputStream = new ObjectInputStream(socket.getInputStream());
                }
                request = (Request) objectInputStream.readObject();
                System.out.println(request.getClass().getName());
                System.out.println(request.toString());
                Response response;
                try {
                    // Determine type of request, and process the request.

                    if (request instanceof AuthenticateRequest) {
                        response = process((AuthenticateRequest) request);
                    } else if (request instanceof GetUserNameRequest) {
                        response = process((GetUserNameRequest) request);
                    } else if (request instanceof EditProfileRequest) {
                        response = process((EditProfileRequest) request);
                    } else if (request instanceof AddUser2ConversationRequest) {
                        response = process((AddUser2ConversationRequest) request);
                    } else if (request instanceof CreateConversationRequest) {
                        response = process((CreateConversationRequest) request);
                    } else if (request instanceof DeleteAccountRequest) {
                        response = process((DeleteAccountRequest) request);
                    } else if (request instanceof DeleteConversationRequest) {
                        response = process((DeleteConversationRequest) request);
                    } else if (request instanceof DeleteMessageRequest) {
                        response = process((DeleteMessageRequest) request);
                    } else if (request instanceof EditMessageRequest) {
                        response = process((EditMessageRequest) request);
//                    } else if (request instanceof GetAllUserNamesRequest) {
//                        response = process(request);
                    } else if (request instanceof GetConversationRequest) {
                        response = process((GetConversationRequest) request);
                    } else if (request instanceof GetMessageRequest) {
                        response = process((GetMessageRequest) request);
                    } else if (request instanceof GetMessageHistoryRequest) {
                        response = process((GetMessageHistoryRequest) request);
                    } else if (request instanceof GetUserRequest) {
                        response = process((GetUserRequest) request);
                    } else if (request instanceof ListAllConversationsRequest) {
                        response = process((ListAllConversationsRequest) request);
                    } else if (request instanceof ListAllMessagesRequest) {
                        response = process((ListAllMessagesRequest) request);
                    } else if (request instanceof ListAllUsersRequest) {
                        response = process((ListAllUsersRequest) request);
                    } else if (request instanceof LogOutRequest) {
                        response = process((LogOutRequest) request);
                    } else if (request instanceof PostMessageRequest) {
                        response = process((PostMessageRequest) request);
                    } else if (request instanceof QuitConversationRequest) {
                        response = process((QuitConversationRequest) request);
                    } else if (request instanceof RegisterRequest) {
                        response = process((RegisterRequest) request);
                    } else if (request instanceof RemoveUserFromConversationRequest) {
                        response = process((RemoveUserFromConversationRequest) request);
                    } else if (request instanceof RenameConversationRequest) {
                        response = process((RenameConversationRequest) request);
                    } else if (request instanceof SetConversationAdminRequest) {
                        response = process((SetConversationAdminRequest) request);
                    } else if (request instanceof UpdateMessageRequest) {
                        response = process(request);
                    } else {
                        response = process(request);
                    }

                    // exceptions are regarding handling of a specific request
                } catch (RequestFailedException e) {
                    response = new Response(false, "", request.uuid, e);
                }

                if (objectOutputStream == null) {
                    objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                }
                System.out.println(response.toString());
                objectOutputStream.writeObject(response);

                // exceptions are regarding system failure
            } catch (SocketException ignored) {
                break;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }

        if (currentUser != null) {
            try {
                system.signOut(currentUser.uuid);
            } catch (NotLoggedInException e) {
                e.printStackTrace();
            }
        }
        System.out.println(socket.toString() + " terminate");
    }

    private Response process(Request request) throws
            RequestFailedException {

        throw new RequestFailedException();
    }

    // Below we define all APIs.

    //log in
    //authenticateRequest. To test user's login circumstances.
    AuthenticateResponse process(AuthenticateRequest authenticateRequest) throws UserNotFoundException, InvalidPasswordException,
            InvalidUsernameException, LoggedInException {
        Credential credential = authenticateRequest.credential;

        User user = system.signIn(credential); // Validates the credential.
        currentUser = user; // Mark this ServerWorker as authenticated.
        return new AuthenticateResponse(true, "Login successful!", authenticateRequest.uuid, user.uuid);

    }

    //getUserNameRequest
    GetUserNameResponse process(GetUserNameRequest getUserNameRequest) throws UserNotFoundException {
        String name = getUserNameRequest.name;
        if (name == null) {
            throw new UserNotFoundException();
        } else {
            User user = system.getUser(name);
            return new GetUserNameResponse(true, "", getUserNameRequest.uuid, user.uuid);
        }

    }

    //editProfileRequest
    Response process(EditProfileRequest editProfileRequest) throws NotLoggedInException {
        Profile profile = editProfileRequest.profile;
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else {
            try {
                system.editProfile(currentUser.uuid, profile);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
            return new Response(true, "", editProfileRequest.uuid);
        }
    }

    //logOutRequest
    Response process(LogOutRequest logOutRequest) throws NotLoggedInException {

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else {
            system.signOut(currentUser.uuid);
            currentUser = null;
            return new Response(true, "Logout successfully!", logOutRequest.uuid);
        }
    }

    //registerRequest
    RegisterResponse process(RegisterRequest registerRequest) throws UserExistsException,
            InvalidUsernameException, InvalidPasswordException {

        User new_user = system.addUser(registerRequest.credential, registerRequest.profile);
        return new RegisterResponse(true, "Register success", registerRequest.uuid, new_user.uuid);

    }

    //deleteAccountRequest
    Response process(DeleteAccountRequest deleteAccountRequest) throws AuthorizationException, UserNotFoundException, InvalidPasswordException {

        if (deleteAccountRequest.credential.usrName.equals(currentUser.credential.usrName)) {
            system.getUser(deleteAccountRequest.credential);
            UUID uuid = currentUser.uuid;
            system.deleteUser(uuid);
        } else {
            throw new AuthorizationException();
        }
        return new Response(true, "", deleteAccountRequest.uuid);
    }


    //postMessageRequest
    PostMessageResponse process(PostMessageRequest postMessageRequest) throws NotLoggedInException,
            ConversationNotFoundException, AuthorizationException, IllegalContentException, MessageNotFoundException {

        UUID conversation_uuid = postMessageRequest.conversation_uuid;
        Message message = postMessageRequest.message;
        Conversation conversation = system.getConversation(conversation_uuid);

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (Set.of(conversation.user_uuids).contains(currentUser.uuid)) {
            Message new_message = system.addMessage(postMessageRequest.conversation_uuid, currentUser.uuid, message.content);
            return new PostMessageResponse(true, "", postMessageRequest.uuid, new_message.time, new_message.uuid);
        } else {
            throw new ConversationNotFoundException();
        }
    }

    //editMessageRequest
    EditMessageResponse process(EditMessageRequest editMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, IllegalContentException, ConversationNotFoundException {

        Message message = system.getMessage(editMessageRequest.messsage_uuid);
        String content = editMessageRequest.content;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (content.length() > 1000 || content.contains("**")) {
            throw new IllegalContentException();
        } else {
            Message replaced_message = system.editMessage(content, message.uuid);
            return new EditMessageResponse(true, "", editMessageRequest.uuid, replaced_message.time);
        }

    }

    //deleteMessageRequest
    Response process(DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, UserNotFoundException, ConversationNotFoundException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        }
        UUID message_uuid = deleteMessageRequest.message_uuid;
        Message message = system.getMessage(message_uuid);
        if (message.sender_uuid == currentUser.uuid) {
            system.deleteMessage(message_uuid);
        } else {
            throw new AuthorizationException();
        }
        return new Response(true, "", deleteMessageRequest.uuid);
    }

    //createConversationRequest
    CreateConversationResponse process(CreateConversationRequest createConversationRequest) throws NotLoggedInException,
            InvalidConversationNameException, UserNotFoundException {
        //qun yuan
        UUID[] createConversation_uuid = createConversationRequest.user_uuids;
        String name = createConversationRequest.name;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (name.contains("**") || name.length() > 1000) {
            throw new InvalidConversationNameException();
        } else {
            Conversation conversation = system.createConversation(name, createConversation_uuid, currentUser.uuid);
            return new CreateConversationResponse(true, "", createConversationRequest.uuid, conversation.uuid);
        }
    }

    //deleteConversationRequest
    Response process(DeleteConversationRequest deleteConversationRequest) throws NotLoggedInException, ConversationNotFoundException {

        Conversation conversation = system.deleteConversation(deleteConversationRequest.conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        }
        return new Response(true, "", deleteConversationRequest.uuid);
    }

    //renameConversationRequest
    Response process(RenameConversationRequest renameConversationRequest) throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, InvalidConversationNameException {
        String name = renameConversationRequest.name;
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (name == null || name.equals("")) {
            throw new InvalidConversationNameException();
        } else if (renameConversationRequest.conversation_uuid == null) {
            throw new UserNotFoundException();
        } else {
            system.renameConversation(name, renameConversationRequest.conversation_uuid);
            return new Response(true, "", renameConversationRequest.uuid);
        }
    }

    //addUser2ConversationRequest
    Response process(AddUser2ConversationRequest addUser2ConversationRequest) throws NotLoggedInException,
            UserNotFoundException, ConversationNotFoundException, AuthorizationException {
        User user = system.getUser(addUser2ConversationRequest.user_uuid);
        Conversation conversation = system.getConversation(addUser2ConversationRequest.conversation_uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID user_uuid = addUser2ConversationRequest.user_uuid;
            system.addUser2Conversation(user_uuid, addUser2ConversationRequest.conversation_uuid);
            return new Response(true, "", addUser2ConversationRequest.uuid);
        }

    }

    //removeUserFromConversationRequest
    Response process(RemoveUserFromConversationRequest removeUserFromConversationRequest) throws
            RequestFailedException {
        User user = system.getUser(removeUserFromConversationRequest.user_uuid);
        Conversation conversation = system.getConversation(removeUserFromConversationRequest.conversation_uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else if (currentUser.uuid != conversation.admin_uuid) {
            throw new AuthorizationException();
        } else {
            UUID uuid = removeUserFromConversationRequest.user_uuid;
            if (removeUserFromConversationRequest.user_uuid != currentUser.uuid) {
                system.quitConversation(uuid, removeUserFromConversationRequest.conversation_uuid);
                return new Response(true, "", removeUserFromConversationRequest.conversation_uuid);
            } else {
                throw new AuthorizationException();
            }
        }
    }

    //setConversationAdminRequest
    Response process(SetConversationAdminRequest setConversationAdminRequest) throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, AuthorizationException {
        Conversation conversation = system.getConversation(setConversationAdminRequest.conversation_uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (currentUser.uuid != conversation.admin_uuid) {
            throw new AuthorizationException();
        } else {
            UUID conversation_uuid = setConversationAdminRequest.conversation_uuid;
            system.setAdmin(setConversationAdminRequest.admin_uuid, conversation_uuid);
            return new Response(true, "", setConversationAdminRequest.uuid);
        }
    }

    //quitConversationRequest
    Response process(QuitConversationRequest quitConversationRequest) throws NotLoggedInException,
            ConversationNotFoundException, UserNotFoundException, AuthorizationException {
        User user = system.getUser(currentUser.uuid);
        Conversation conversation = system.getConversation(quitConversationRequest.conversation_uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID uuid = quitConversationRequest.conversation_uuid;
            if (conversation.admin_uuid != currentUser.uuid) {
                system.quitConversation(uuid, quitConversationRequest.conversation_uuid);
                return new Response(true, "", quitConversationRequest.uuid);
            } else {
                throw new AuthorizationException();
            }
        }

    }

    //listAllUsersRequest
    ListAllUsersResponse process(ListAllUsersRequest listAllUsersRequest) throws NotLoggedInException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        }
        UUID[] users = system.getAllUserUUIDs();
        return new ListAllUsersResponse(true, "", listAllUsersRequest.uuid, users);
    }

    //listAllConversationsRequest
    ListAllConversationsResponse process(ListAllConversationsRequest listAllConversationsRequest) throws NotLoggedInException {
        try {
            UUID[] uuids = system.getUserConversations(currentUser);
            return new ListAllConversationsResponse(true, "", listAllConversationsRequest.uuid, uuids);
        } catch (UserNotFoundException e) {
            throw new NotLoggedInException();
        }
    }

    //listAllMessagesRequest
    ListAllMessagesResponse process(ListAllMessagesRequest listAllMessagesRequest) throws NotLoggedInException, ConversationNotFoundException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (system.deleteConversation(listAllMessagesRequest.conversation_uuid) == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID conversation_uuid = listAllMessagesRequest.conversation_uuid;
            UUID[] user_uuid = system.listAllUUID(conversation_uuid);
            return new ListAllMessagesResponse(true, "", listAllMessagesRequest.uuid, user_uuid);
        }

    }

    //getUserRequest
    GetUserResponse process(GetUserRequest getUserRequest) throws NotLoggedInException, UserNotFoundException {
        UUID uuid = getUserRequest.user_uuid;
        User user = system.getUser(uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else {
            return new GetUserResponse(true, "", getUserRequest.uuid, user);
        }
    }

    //getConversationRequest
    GetConversationResponse process(GetConversationRequest getConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = getConversationRequest.conversation_uuid;
        Conversation conversation = system.getConversation(uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return new GetConversationResponse(true, "", getConversationRequest.uuid, conversation);
        }
    }

    //getMessageRequest
    GetMessageResponse process(GetMessageRequest getMessageRequest) throws NotLoggedInException, MessageNotFoundException {
        UUID uuid = getMessageRequest.message_uuid;
        Message message = system.getMessage(uuid);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (message == null) {
            throw new MessageNotFoundException();
        } else {
            return new GetMessageResponse(true, "", getMessageRequest.uuid, message);
        }
    }

    //getMessageHistoryRequest
    GetMessageHistoryResponse process(GetMessageHistoryRequest getMessageHistoryRequest) throws NotLoggedInException,
            ConversationNotFoundException, MessageNotFoundException {
        Message[] message = system.getConversationMessages(getMessageHistoryRequest.conversation_uuId);

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (message == null) {
            throw new MessageNotFoundException();
        } else if (system.getConversation(getMessageHistoryRequest.conversation_uuId) == null) {
            throw new ConversationNotFoundException();
        } else {
            return new GetMessageHistoryResponse(true, "", getMessageHistoryRequest.uuid, message);
        }
    }


    //GetEventFeedResponse
    GetEventFeedResponse process(GetEventFeedRequest getEventFeedRequest) throws NotLoggedInException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else {

            return new GetEventFeedResponse(true, "", getEventFeedRequest.uuid,
                    system.getEventBag(currentUser.uuid));
        }
    }

}
