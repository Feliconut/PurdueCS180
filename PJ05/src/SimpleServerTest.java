import Request.*;

import java.io.*;
import java.net.*;


public class SimpleServerTest {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(9867);

        Socket socket = serverSocket.accept();
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        for (int i = 0; i < 10; i++) {
            Request request = (Request) objectInputStream.readObject();
            Response response = new Response(true, "", request.uuid);
            objectOutputStream.writeObject(response);

        }

        objectOutputStream.close();
    }
}

