import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


/**
 * A Simple Client.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version January 28, 2021
 */
public class SimpleClient
{
    public static void main(String[] args) throws IOException, ClassNotFoundException
    {
        Scanner scan = new Scanner(System.in);
        Socket socket = new Socket("localhost", 4242);
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter writer = new PrintWriter(socket.getOutputStream());

        System.out.println("What do you want to send to the server?");
        String response = scan.nextLine();

        writer.write(response);
        writer.println();
        writer.flush();
        System.out.printf("Sent to server:\n%s\n", response);

        String s1 = reader.readLine();
        System.out.printf("Received from server:\n%s\n", s1);

        writer.close();
        reader.close();
    }
}
