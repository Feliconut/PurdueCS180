import Exceptions.*;
import Field.Credential;
import Field.Message;
import Field.Profile;
import Field.User;
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
            ConversationNotFoundException, AuthorizationException, IllegalContentException {


        return new PostMessageResponse(true, "", postMessageRequest.uuid, postMessageRequest.message.time);
    }

    //editMessageRequest
    EditMessageResponse process(EditMessageRequest editMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException, IllegalContentException {
        return null;
    }

    //deleteMessageRequest
    Response process(DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException,
            MessageNotFoundException, AuthorizationException {

        UUID uuid = deleteMessageRequest.uuid;

        Message message = system.deleteMessage(uuid);
        return new Response(true, "Delete message successfully", deleteMessageRequest.uuid);
    }

    //createConversationRequest
    Response process(CreateConversationRequest createConversationRequest) throws NotLoggedInException,
            InvalidConversationNameException, UserNotFoundException {
        return null;
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
        return null;
    }

    //addUser2ConversationRequest
    Response process(AddUser2ConversationRequest addUser2ConversationRequest) throws NotLoggedInException,
            UserNotFoundException, ConversationNotFoundException, AuthorizationException {
        return null;
    }

    //removeUserFromConversationRequest
    //"There is a more general exception, 'Exceptions.RequestFailedException', in the throws list already. "
    Response process(RemoveUserFromConversationRequest removeUserFromConversationRequest) throws
            RequestFailedException {
        return null;
    }

    //setConversationAdminRequest
    Response process(SetConversationAdminRequest setConversationAdminRequest) throws NotLoggedInException, UserNotFoundException,
            ConversationNotFoundException, AuthorizationException {
        return null;
    }

    //quitConversationRequest
    Response process(QuitConversationRequest quitConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        return null;
    }

    //listAllUsersRequest
    Response process(ListAllUsersRequest listAllUsersRequest) throws NotLoggedInException {
        return null;
    }

    //listAllConversationsRequest
    Response process(ListAllConversationsRequest listAllConversationsRequest) throws NotLoggedInException {
        return null;
    }

    //listAllMessagesRequest
    Response process(ListAllMessagesRequest listAllMessagesRequest) throws NotLoggedInException, ConversationNotFoundException {
        return null;
    }

    //getUserRequest
    Response process(GetUserRequest getUserRequest) throws NotLoggedInException, UserNotFoundException {
        return null;
    }

    //getConversationRequest
    Response process(GetConversationRequest getConversationRequest) throws NotLoggedInException, ConversationNotFoundException {
        return null;
    }

    //getMessageRequest
    Response process(GetMessageRequest getMessageRequest) throws NotLoggedInException, MessageNotFoundException {
        return null;
    }

    //getMessageHistoryRequest
    Response process(GetMessageHistoryRequest getMessageHistoryRequest) throws NotLoggedInException,
            ConversationNotFoundException {
        return null;
    }

    //GetEventFeedResponse
    Response process(GetEventFeedResponse getEventFeedResponse) throws NotLoggedInException {
        return null;
    }

// 没在documentation里找到
//
//    //updateMessageRequest
//    UpdateMessageResponse process(UpdateMessageRequest updateMessageRequest) throws NotLoggedInException {
//        return null;
//    }
//
//
//
//    // Locating Users
//
//    //getAllUserNamesRequest
//    Response process(GetAllUserNamesRequest getAllUserNamesRequest) throws NotLoggedInException {
//        return null;
//    }
}
