import Exceptions.RequestFailedException;
import Exceptions.RequestParsingException;
import Request.LogOutRequest;
import Request.Request;
import Request.Response;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleClientTest {
    public static Response send(Request request, Socket socket) throws IOException,
            RequestFailedException {
        Response response;
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(request);
        oos.flush();
        oos.close();

        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

        try {
            response = (Response) ois.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            throw new RequestParsingException();
        }

        if (response.state) {
            return response;

        } else {
            if (response.exception != null) {
                throw response.exception;
            } else {
                throw new RequestFailedException();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String hostname = "localhost";
        int port = 9867;
        Socket socket = new Socket(hostname, port);

        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        for (int i = 0; i < 10; i++) {
            objectOutputStream.writeObject(new LogOutRequest());
            objectOutputStream.flush();
//        objectInputStream.wait();
            Response response = (Response) objectInputStream.readObject();
            System.out.println(response);
        }

        objectInputStream.close();
    }
}
