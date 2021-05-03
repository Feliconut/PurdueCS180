import Exceptions.*;
import Field.*;
import Request.*;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Project5-- server worker
 * <p>
 * This class will deal with a bunch of request send from client and
 * give a specific response based on its request
 *
 * @author team 84
 * @version 04/30/2021
 */
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
                    } else if (request instanceof GetUserByNameRequest) {
                        response = process((GetUserByNameRequest) request);
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
                    } else if (request instanceof GetConversationRequest) {
                        response = process((GetConversationRequest) request);
                    } else if (request instanceof GetEventFeedRequest) {
                        response = process((GetEventFeedRequest) request);
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
                } catch (Exception e) {
                    e.printStackTrace();
                    response = new Response(false, "", request.uuid, new RequestFailedException());
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
    AuthenticateResponse process(AuthenticateRequest authenticateRequest)
            throws UserNotFoundException, InvalidPasswordException,
            InvalidUsernameException, LoggedInException {
        Credential credential = authenticateRequest.credential;

        User user = system.signIn(credential); // Validates the credential.
        currentUser = user; // Mark this ServerWorker as authenticated.
        return new AuthenticateResponse(true, "Login successful!", authenticateRequest.uuid, user.uuid);

    }

    //getUserNameRequest
    GetUserByNameResponse process(GetUserByNameRequest getUserByNameRequest) throws UserNotFoundException {
        String name = getUserByNameRequest.username;
        if (name == null) {
            throw new UserNotFoundException();
        } else {
            User user = system.getUser(name);
            return new GetUserByNameResponse(true, "", getUserByNameRequest.uuid, user);
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

        User newUser = system.addUser(registerRequest.credential, registerRequest.profile);
        return new RegisterResponse(true, "Register success", registerRequest.uuid, newUser.uuid);

    }

    //deleteAccountRequest
    Response process(DeleteAccountRequest deleteAccountRequest)
            throws AuthorizationException, UserNotFoundException, InvalidPasswordException {

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

        UUID conversationUUID = postMessageRequest.conversationUUID;
        Message message = postMessageRequest.message;
        Conversation conversation = system.getConversation(conversationUUID);

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (Set.of(conversation.userUUIDs).contains(currentUser.uuid)) {
            Message newMessage = system
                    .addMessage(postMessageRequest.conversationUUID, currentUser.uuid, message.content);
            return new PostMessageResponse(true, "", postMessageRequest.uuid, newMessage.time, newMessage.uuid);
        } else {
            throw new ConversationNotFoundException();
        }
    }

    //editMessageRequest
    EditMessageResponse process(EditMessageRequest editMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, IllegalContentException, ConversationNotFoundException {

        Message message = system.getMessage(editMessageRequest.messsageUUID);
        String content = editMessageRequest.content;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (content == null) {
            throw new IllegalContentException("Message cannot be empty");
        } else if (content.length() > 1000 || content.equals("")) {
            throw new IllegalContentException("Message cannot be empty or too long");
        } else if (!message.senderUUID.equals(currentUser.uuid)) {
            throw new AuthorizationException("You are not the author of this message");
        } else {
            content = content.strip();
            Message replacedMessage = system.editMessage(content, message.uuid);
            return new EditMessageResponse(true, "", editMessageRequest.uuid, replacedMessage.time);
        }

    }

    //deleteMessageRequest
    Response process(DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, UserNotFoundException, ConversationNotFoundException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        }
        UUID messageUUID = deleteMessageRequest.messageUUID;
        Message message = system.getMessage(messageUUID);
        if (message.senderUUID.equals(currentUser.uuid)) {
            system.deleteMessage(messageUUID);
        } else {
            throw new AuthorizationException();
        }
        return new Response(true, "", deleteMessageRequest.uuid);
    }

    //createConversationRequest
    CreateConversationResponse process(CreateConversationRequest createConversationRequest) throws NotLoggedInException,
            InvalidConversationNameException, UserNotFoundException {
        //qun yuan
        UUID[] createconversationUUID = createConversationRequest.userUUIDs;
        String name = createConversationRequest.name;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (name.contains("**") || name.length() > 1000) {
            throw new InvalidConversationNameException();
        } else {
            Conversation conversation = system.createConversation(name, createconversationUUID, currentUser.uuid);
            return new CreateConversationResponse(true, "", createConversationRequest.uuid, conversation.uuid);
        }
    }

    //deleteConversationRequest
    Response process(DeleteConversationRequest deleteConversationRequest)
            throws NotLoggedInException, ConversationNotFoundException, AuthorizationException {

        if (currentUser == null) {
            throw new NotLoggedInException();
        }
        Conversation conversation = system.getConversation(deleteConversationRequest.conversationUUID);
        if (conversation.adminUUID.equals(currentUser.uuid) && conversation.userUUIDs.length == 1) {
            system.deleteConversation(deleteConversationRequest.conversationUUID);
        } else {
            try {
                system.quitConversation(currentUser.uuid, conversation.uuid);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
        return new Response(true, "", deleteConversationRequest.uuid);
    }

    //renameConversationRequest
    Response process(RenameConversationRequest renameConversationRequest)
            throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, InvalidConversationNameException {
        String name = renameConversationRequest.name;
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (name == null || name.equals("")) {
            throw new InvalidConversationNameException();
        } else if (renameConversationRequest.conversationUUID == null) {
            throw new UserNotFoundException();
        } else {
            system.renameConversation(name, renameConversationRequest.conversationUUID);
            return new Response(true, "", renameConversationRequest.uuid);
        }
    }

    //addUser2ConversationRequest
    Response process(AddUser2ConversationRequest addUser2ConversationRequest) throws NotLoggedInException,
            UserNotFoundException, ConversationNotFoundException, AuthorizationException {
        User user = system.getUser(addUser2ConversationRequest.userUUID);
        Conversation conversation = system.getConversation(addUser2ConversationRequest.conversationUUID);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID userUUID = addUser2ConversationRequest.userUUID;
            system.addUser2Conversation(userUUID, addUser2ConversationRequest.conversationUUID);
            return new Response(true, "", addUser2ConversationRequest.uuid);
        }

    }

    //removeUserFromConversationRequest
    Response process(RemoveUserFromConversationRequest removeUserFromConversationRequest) throws
            RequestFailedException {
        User user = system.getUser(removeUserFromConversationRequest.userUUID);
        Conversation conversation = system.getConversation(removeUserFromConversationRequest.conversationUUID);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else if (currentUser.uuid != conversation.adminUUID) {
            throw new AuthorizationException();
        } else {
            UUID uuid = removeUserFromConversationRequest.userUUID;
            if (removeUserFromConversationRequest.userUUID != currentUser.uuid) {
                system.quitConversation(uuid, removeUserFromConversationRequest.conversationUUID);
                return new Response(true, "", removeUserFromConversationRequest.conversationUUID);
            } else {
                throw new AuthorizationException();
            }
        }
    }

    //setConversationAdminRequest
    Response process(SetConversationAdminRequest setConversationAdminRequest)
            throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, AuthorizationException {
        Conversation conversation = system.getConversation(setConversationAdminRequest.conversationUUID);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (currentUser.uuid != conversation.adminUUID) {
            throw new AuthorizationException();
        } else {
            UUID conversationUUID = setConversationAdminRequest.conversationUUID;
            system.setAdmin(setConversationAdminRequest.adminUUID, conversationUUID);
            return new Response(true, "", setConversationAdminRequest.uuid);
        }
    }

    //quitConversationRequest
    Response process(QuitConversationRequest quitConversationRequest) throws NotLoggedInException,
            ConversationNotFoundException, UserNotFoundException, AuthorizationException {
        User user = system.getUser(currentUser.uuid);
        Conversation conversation = system.getConversation(quitConversationRequest.conversationUUID);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            if (conversation.adminUUID != currentUser.uuid) {
                system.quitConversation(currentUser.uuid, quitConversationRequest.conversationUUID);
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
    ListAllConversationsResponse process(ListAllConversationsRequest listAllConversationsRequest)
            throws NotLoggedInException {
        try {
            UUID[] uuids = system.getUserConversations(currentUser);
            return new ListAllConversationsResponse(true, "", listAllConversationsRequest.uuid, uuids);
        } catch (UserNotFoundException e) {
            throw new NotLoggedInException();
        }
    }

    //listAllMessagesRequest
    ListAllMessagesResponse process(ListAllMessagesRequest listAllMessagesRequest)
            throws NotLoggedInException, ConversationNotFoundException {
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (system.deleteConversation(listAllMessagesRequest.conversationUUID) == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID conversationUUID = listAllMessagesRequest.conversationUUID;
            UUID[] userUUID = system.listAllUUID(conversationUUID);
            return new ListAllMessagesResponse(true, "", listAllMessagesRequest.uuid, userUUID);
        }

    }

    //getUserRequest
    GetUserResponse process(GetUserRequest getUserRequest) throws NotLoggedInException, UserNotFoundException {
        UUID uuid = getUserRequest.userUUID;
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
    GetConversationResponse process(GetConversationRequest getConversationRequest)
            throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = getConversationRequest.conversationUUID;
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
    GetMessageResponse process(GetMessageRequest getMessageRequest)
            throws NotLoggedInException, MessageNotFoundException {
        UUID uuid = getMessageRequest.messageUUID;
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
        Message[] message = system.getConversationMessages(getMessageHistoryRequest.conversationUUID);

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (message == null) {
            throw new MessageNotFoundException();
        } else if (system.getConversation(getMessageHistoryRequest.conversationUUID) == null) {
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
