import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServerMaster {
    public static void main(String[] args) throws IOException {


        //Starts the system.
        MessageSystem messageSystem = new MessageSystem("users.txt", "messages.txt", "conversations.txt");


        // port number
        ServerSocket serverSocket = new ServerSocket(9866);

        // keep create new thread if there are user needs to log in to system.
        //disadvantage: needs a lot memory
        try {
            while (true) {
                Socket socket;
                socket = serverSocket.accept();
                MessageServerWorker requestHandler = new MessageServerWorker(socket, messageSystem);
                requestHandler.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}