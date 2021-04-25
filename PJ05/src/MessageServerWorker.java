import Exceptions.*;
import Field.*;
import Request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        ObjectOutputStream objectOutputStream;
        ObjectInputStream objectInputStream;
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                Request request;
                // Read a new request from socket.
                request = (Request) objectInputStream.readObject();
                Response response;
                try {
                    // Determine type of request, and process the request.


                    if (request instanceof AuthenticateRequest) {
                        response = process((AuthenticateRequest) request);
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
                    } else if (request instanceof GetAllUserNamesRequest) {
                        response = process(request);
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
                objectOutputStream.writeObject(response);

                // exceptions are regarding system failure
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }
    }

    private Response process(Request request) throws
            RequestFailedException {

        throw new RequestFailedException();
    }

    // Below we define all APIs.

    //log in
    //authenticateRequest. To test user's login circumstances.
    Response process(AuthenticateRequest authenticateRequest) throws UserNotFoundException, InvalidPasswordException,
            InvalidUsernameException {
        Credential credential = authenticateRequest.credential;

        User user = system.getUser(credential);
        currentUser = user;
        return new Response(true, "Login successful!", authenticateRequest.uuid);

    }

    //logOutRequest
    Response process(LogOutRequest logOutRequest) throws NotLoggedInException {

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else {
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
    Response process(DeleteAccountRequest deleteAccountRequest) throws AuthorizationException, UserNotFoundException {
        UUID uuid = deleteAccountRequest.uuid;

        Profile profile = system.deleteUser(uuid);

        return new Response(true, "Delete successfully", deleteAccountRequest.uuid);
    }


    //postMessageRequest
    PostMessageResponse process(PostMessageRequest postMessageRequest) throws NotLoggedInException,
            ConversationNotFoundException, AuthorizationException, IllegalContentException, MessageNotFoundException {

        //打算搞简单一点 handout没说权限这个事
        Message post_message = system.getMessage(postMessageRequest.uuid);
        String message = String.valueOf(postMessageRequest.message);
        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (message.length() > 1000) {
            throw new IllegalContentException();
        } else if (post_message == null) {
            throw new MessageNotFoundException();
        } else {
            Message add_message = system.addMessage(postMessageRequest.uuid);
            return new PostMessageResponse(true, "", postMessageRequest.uuid, postMessageRequest.message.time);

        }
    }

    //editMessageRequest
    EditMessageResponse process(EditMessageRequest editMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, IllegalContentException {

        Message edit_message = system.getMessage(editMessageRequest.uuid);
        String message = edit_message.content;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (edit_message.content.length() > 1000 || edit_message.content.contains("**")) {
            throw new IllegalContentException();
        } else {
            Message replaced_message = system.editMessage(message, editMessageRequest.date, editMessageRequest.messsage_uuid);
            return new EditMessageResponse(true, "", editMessageRequest.uuid, editMessageRequest.date);
        }
    }

    //deleteMessageRequest
    Response process(DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException {

        UUID uuid = deleteMessageRequest.uuid;

        Message message = system.deleteMessage(uuid);
        return new Response(true, "Delete message successfully", deleteMessageRequest.uuid);
    }

    //createConversationRequest
    CreateConversationResponse process(CreateConversationRequest createConversationRequest) throws NotLoggedInException,
            InvalidConversationNameException, UserNotFoundException {
        //群主
        UUID createConversation_uuid = createConversationRequest.uuid;
        //群员ID
        UUID[] particpant_uuid = createConversationRequest.user_uuids;

        String name = createConversationRequest.name;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (particpant_uuid.length == 0) {
            //有个问题(GOOGLE DOC)
            //
            throw new UserNotFoundException();
        } else {
            Conversation conversation = system.createConversation(name, createConversation_uuid, particpant_uuid);
            return new CreateConversationResponse(true, "", createConversation_uuid, particpant_uuid);
        }
    }

    //deleteConversationRequest
    Response process(DeleteConversationRequest deleteConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = deleteConversationRequest.uuid;

        //Conversation conversation = system.deleteConversation(deleteConversationRequest.conversation, deleteConversationRequest.uuid);

        return new Response(true, "", deleteConversationRequest.uuid);
    }

    //renameConversationRequest
    Response process(RenameConversationRequest renameConversationRequest) throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, InvalidConversationNameException {
        String name = renameConversationRequest.name;

        if (currentUser == null) {
            throw new NotLoggedInException();
        } else if (name.contains("**")) {
            throw new InvalidConversationNameException();
        } else if (renameConversationRequest.conversation_uuid == null) {
            throw new UserNotFoundException();
        } else {
            Conversation conversation = system.renameConversation(name, renameConversationRequest.conversation_uuid);
            return new Response(true, "", renameConversationRequest.uuid);
        }
    }

    //addUser2ConversationRequest
    Response process(AddUser2ConversationRequest addUser2ConversationRequest) throws NotLoggedInException,
            UserNotFoundException, ConversationNotFoundException, AuthorizationException {

        UUID user_uuid = addUser2ConversationRequest.user_uuid;
        Conversation conversation = system.addUser2Conversation(user_uuid, addUser2ConversationRequest.conversation_uuid);
        return new Response(true, "", addUser2ConversationRequest.uuid);
    }

    //removeUserFromConversationRequest
    Response process(RemoveUserFromConversationRequest removeUserFromConversationRequest) throws
            RequestFailedException {
        UUID uuid = removeUserFromConversationRequest.user_uuid;
        Conversation conversation = system.removeUserFormConversation(uuid, removeUserFromConversationRequest.conversation_uuid);
        return new Response(true, "", removeUserFromConversationRequest.conversation_uuid);
    }

    //setConversationAdminRequest
    Response process(SetConversationAdminRequest setConversationAdminRequest) throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, AuthorizationException {

        return null;
    }

    //quitConversationRequest
    Response process(QuitConversationRequest quitConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = quitConversationRequest.uuid;
        Conversation conversation = system.quitConversation(uuid, quitConversationRequest.conversation_uuid);
        return new Response(true, "", quitConversationRequest.uuid);
    }

    //listAllUsersRequest
    ListAllUsersResponse process(ListAllUsersRequest listAllUsersRequest) throws NotLoggedInException {
        //不确定这行啥意思
        UUID[] users = system.allUser().toArray(new UUID[0]);
        return new ListAllUsersResponse(true, "", listAllUsersRequest.uuid, users);
    }

    //listAllConversationsRequest
    ListAllConversationsResponse process(ListAllConversationsRequest listAllConversationsRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = listAllConversationsRequest.uuid;
        UUID[] uuids = system.getUserConversations(uuid);
        return new ListAllConversationsResponse(true, "", listAllConversationsRequest.uuid, uuids);
    }

    //listAllMessagesRequest
    ListAllMessagesResponse process(ListAllMessagesRequest listAllMessagesRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID conversation_uuid = listAllMessagesRequest.conversation_uuid;
        UUID[] user_uuid = system.listAllUUID(conversation_uuid);
        return new ListAllMessagesResponse(true, "", listAllMessagesRequest.uuid, user_uuid);
    }

    //getUserRequest
    GetUserResponse process(GetUserRequest getUserRequest) throws NotLoggedInException, UserNotFoundException {
        UUID uuid = getUserRequest.user_uuid;
        User user = system.getUser(uuid);
        return new GetUserResponse(true, "", getUserRequest.uuid, user);
    }

    //getConversationRequest
    GetConversationResponse process(GetConversationRequest getConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        UUID uuid = getConversationRequest.conversation_uuid;
        Conversation conversation = system.getConversation(uuid);
        return new GetConversationResponse(true, "", getConversationRequest.uuid, conversation);
    }

    //getMessageRequest
    GetMessageResponse process(GetMessageRequest getMessageRequest) throws NotLoggedInException, MessageNotFoundException, ConversationNotFoundException {
        UUID uuid = getMessageRequest.message_uuid;
        Message message = system.getMessages(uuid);
        return new GetMessageResponse(true, "", getMessageRequest.uuid, message);
    }

    //getMessageHistoryRequest
    GetMessageHistoryResponse process(GetMessageHistoryRequest getMessageHistoryRequest) throws NotLoggedInException,
            ConversationNotFoundException, MessageNotFoundException {
        Message[] message = system.getConversationMessages(getMessageHistoryRequest.uuid);

        return new GetMessageHistoryResponse(true, "", getMessageHistoryRequest.uuid, message);
    }

    //GetEventFeedResponse
    GetEventFeedResponse process(GetEventFeedRequest getEventFeedRequest) throws NotLoggedInException {
        return null;
    }
}
