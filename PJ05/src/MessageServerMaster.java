import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessageServerMaster {
    public static void main(String[] args) throws IOException {


        //Starts the system.
        MessageSystem messageSystem = new MessageSystem("users.txt", "messages.txt", "conversations.txt");
        //TODO starts the server, listens to new connects, and start a thread for each new client.



        ServerSocket serverSocket = new ServerSocket(9866);
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