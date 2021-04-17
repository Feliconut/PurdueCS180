import Field.Credential;
import Field.User;
import Request.*;
import com.sun.jdi.InternalException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageServerWorker extends Thread
{

    private final Socket socket;
    private final MessageSystem system;
    private User currentUser;

    public MessageServerWorker(Socket socket, MessageSystem system)
    {
        this.socket = socket;
        this.system = system;
    }

    @Override
    public void run()
    {
        BufferedReader reader;
        PrintWriter writer;


        try{
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException e){
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
        String requestStr = ""; // Read from the socket
        BaseClientRequest request = null;
        try
        {
            request = BaseClientRequest.parseRequest(requestStr);
            Response res = null;
            if (request instanceof AuthenticateRequest)
            {
                res = authenticate((AuthenticateRequest) request);

            } else
            {
                res = new Response(false, "Invalid Format", request);
            }
            writer.println(res.toString());
        } catch (RequestParsingException e)
        {
            e.printStackTrace();
        }


    }


    // Below we define all APIs.

    Response authenticate(AuthenticateRequest authenticateRequest)
    {
        //TODO if succeed, return user. if not succeed, return null.
        Credential credential = authenticateRequest.credential;
        try
        {
            User user = system.getUser(credential);
            if (user != null)
            {
                return new Response(true, "Login successful!", authenticateRequest);
            }
        }
        catch (UserNotFoundException e)
        {
            return new Response(false, "User not found", authenticateRequest);
        }
        catch (InvalidPasswordException e)
        {
            return new Response(false, "Wrong password", authenticateRequest);

        }

        throw new InternalException();
    }
    // Messaging

    boolean postMessage(User requester, PostMessageRequest postMessageRequest) throws NotLoggedInException
    {
        //TODO
        return true;
    }

    boolean updateMessage(User requester, UpdateMessageRequest updateMessageRequest) throws NotLoggedInException
    {
        //TODO
        return true;
    }

//    boolean getMessageHistory(User requester, GetMessageHistoryRequest getMessageHistoryRequest) throws NotLoggedInException
//    {
//        //TODO
//        return true;
//    }
//
//    boolean listAllMessages(User requester, ListAllMessagesRequest listAllMessagesRequest) throws NotLoggedInException
//    {
//        //TODO
//        return true;
//    }
//
//    boolean deleteMessage(User requester, DeleteMessageRequest deleteMessageRequest) throws NotLoggedInException
//    {
//        //TODO
//        return true;
//    }
//
//    // Locating Users
//
//    boolean getAllUserNames(User requester, GetAllUserNamesRequest getAllUserNamesRequest) throws NotLoggedInException
//    {
//        //TODO
//        return true;
//    }
//
//    boolean getUserProfile(User requester, GetUserProfileRequest getUserProfileRequest) throws NotLoggedInException
//    {
//        //TODO
//        return true;
//    }

}
