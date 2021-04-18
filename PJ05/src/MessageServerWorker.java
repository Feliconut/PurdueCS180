import Field.Credential;
import Field.User;
import Request.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

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
        //TODO Read the authentication from client. If success then proceed. If fail then let the client retry.
//        try{
//
//            BaseClientRequest request = BaseClientRequest.parseRequest("");
//            currentUser = API.authenticate((AuthenticateRequest) request);
//        }
//        catch (Exception e)
//        {
//
//        }

        //TODO Use a while loop to listen to all requests and perform action/provide response.


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

                    } else if (request instanceof PostMessageRequest) {
                        response = process((PostMessageRequest) request);
                    } else {
                        response = process(request);
                    }

                    // exceptions are regarding handling of a specific request
                } catch (NotLoggedInException e) {
                    response = new Response(false, "Not Logged In", request.uuid);
                } catch (InvalidRequestException e) {
                    e.printStackTrace();
                    response = new Response(false, "request invalid", request.uuid);
                } catch (UserNotFoundException e) {
                    response = new Response(false, "user not found", request.uuid);
                } catch (InvalidPasswordException e) {
                    e.printStackTrace();
                    response = new Response(false, "password incorrect", request.uuid);
                }
                objectOutputStream.writeObject(response);

                // exceptions are regarding system failure
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                break;
            }

        }
    }

    private Response process(Request request) throws InvalidRequestException {
        throw new InvalidRequestException(request);
    }

    // Below we define all APIs.

    Response process(AuthenticateRequest authenticateRequest) throws UserNotFoundException, InvalidPasswordException {
        Credential credential = authenticateRequest.credential;

        User user = system.getUser(credential);
        currentUser = user;

        return new Response(true, "Login successful!", authenticateRequest.uuid);
    }
    // Messaging

    //postMessageRequest
    PostMessageResponse process(PostMessageRequest postMessageRequest) throws NotLoggedInException {
        //TODO
        return null;
    }


    //createConversationRequest
    Response process(CreateConversationRequest createConversationRequest) throws NotLoggedInException {
        return null;
    }

    //deleteConversationRequest
    Response process(DeleteConversationRequest deleteConversationRequest) throws NotLoggedInException {
        return null;
    }

    //renameConversationRequest
    Response process(RenameConversationRequest renameConversationRequest) throws NotLoggedInException {
        return null;
    }

    //updateMessageRequest
    Response process(UpdateMessageRequest updateMessageRequest) throws NotLoggedInException {
        return null;
    }

    //addUser2ConversationRequest
    Response process(AddUser2ConversationRequest addUser2ConversationRequest) throws NotLoggedInException {
        return null;
    }

    //removeUserFromConversationRequest
    Response process(RemoveUserFromConversationRequest removeUserFromConversationRequest) throws NotLoggedInException {
        return null;
    }

    //setConversationAdminRequest
    Response process(SetConversationAdminRequest setConversationAdminRequest) throws NotLoggedInException {
        return null;
    }

    //quitConversationRequest
    Response process(QuitConversationRequest quitConversationRequest) throws NotLoggedInException {
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

    //getUserRequest
    Response process(GetUserRequest getUserRequest) throws NotLoggedInException {
        return null;
    }

    //getConversationRequest
    Response process(GetConversationRequest getConversationRequest) throws NotLoggedInException {
        return null;
    }

    //getMessageRequest
    Response process(GetMessageRequest getMessageRequest) throws NotLoggedInException {
        return null;
    }


    //registerRequest
    RegisterResponse process(RegisterRequest registerRequest) throws NotLoggedInException {
        return null;
    }

    //editMessageRequest
    EditMessageResponse process(EditMessageRequest editMessageRequest) throws NotLoggedInException {
        return null;
    }

    //deleteAccountRequest
    Response process(DeleteAccountRequest deleteAccountRequest) throws NotLoggedInException {
        return null;
    }

    //deleteMessageRequest
    Response process(DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException {
        //TODO
        return null;
    }

    //getMessageHistoryRequest
    Response process(GetMessageHistoryRequest getMessageHistoryRequest) throws NotLoggedInException {
        return null;
    }

    //listAllMessagesRequest
    Response process(ListAllMessagesRequest listAllMessagesRequest) throws NotLoggedInException {
        //TODO
        return null;
    }

    // Locating Users

    //getAllUserNamesRequest
    Response process(GetAllUserNamesRequest getAllUserNamesRequest) throws NotLoggedInException {
        //TODO
        return null;
    }

    //getUserProfileRequest
    Response process(GetUserProfileRequest getUserProfileRequest) throws NotLoggedInException {
        //TODO
        return null;
    }

}
