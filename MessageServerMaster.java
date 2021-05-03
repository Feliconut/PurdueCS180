import java.io.IOException;
import java.net.ServerSocket;

/**
 * Project5-- server master
 * <p>
 * This class will make sure every user have their own thread to connect to server
 *
 * @author team 84
 * @version 04/30/2021
 */
public class MessageServerMaster {
    public static void main(String[] args) throws IOException {


        //Starts the system.
        MessageSystem messageSystem = new MessageSystem(
                "users.txt",
                "messages.txt",
                "conversations.txt");


        // port number
        ServerSocket serverSocket = new ServerSocket(9866);

        // keep create new thread if there are user needs to log in to system.
        //disadvantage: needs a lot memory
        try {
            while (true) {
                java.net.Socket socket;
                socket = serverSocket.accept();
                MessageServerWorker requestHandler = new MessageServerWorker(socket, messageSystem);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}