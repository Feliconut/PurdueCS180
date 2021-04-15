import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

/**
 * A Simple Calculator Server.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class CalculatorServer
{
    public static void main(String[] args)
    {
        try
        {
            ServerSocket serverSocket = new ServerSocket(4242);

            System.out.println("Waiting for the client to connect...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected!");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream());

            while (true)
            {
                String message;
                try
                {
                    message = reader.readLine();
                } catch (SocketException ignored)
                {
                    System.out.println("Socket Closed. Shutdown.");
                    break;
                }
                System.out.printf("Received from client:\n%s\n", message);

                if (message.equals("SHUTDOWN"))
                {
                    System.out.println("shutting down");
                    break;
                }

                String response = doCalculation(message);

                writer.write(response);
                writer.println();
                writer.flush();
                System.out.printf("Sent to client:\n%s\n", response);
            }
            writer.close();
            reader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private static String doCalculation(String message)
    {
        double num1;
        double num2;
        double res;

        try
        {
            if (message.contains(" + "))
            {
                String[] messageSplit = message.split(" \\+ ");
                num1 = Double.parseDouble(messageSplit[0]);
                num2 = Double.parseDouble(messageSplit[1]);
                res = num1 + num2;
            } else if (message.contains(" - "))
            {
                String[] messageSplit = message.split(" - ");
                num1 = Double.parseDouble(messageSplit[0]);
                num2 = Double.parseDouble(messageSplit[1]);
                res = num1 - num2;
            } else if (message.contains(" * "))
            {
                String[] messageSplit = message.split(" \\* ");
                num1 = Double.parseDouble(messageSplit[0]);
                num2 = Double.parseDouble(messageSplit[1]);
                res = num1 * num2;
            } else if (message.contains(" / "))
            {
                String[] messageSplit = message.split(" / ");
                num1 = Double.parseDouble(messageSplit[0]);
                num2 = Double.parseDouble(messageSplit[1]);
                res = num1 / num2;
            } else if (message.contains(" ^ "))
            {
                String[] messageSplit = message.split(" \\^ ");
                num1 = Double.parseDouble(messageSplit[0]);
                num2 = Double.parseDouble(messageSplit[1]);
                res = Math.pow(num1, num2);
            } else
            {
                return "ERR";
            }
        } catch (Exception ignored)
        {
            return "ERR";
        }

        return String.format("%.2f", res);
    }
}
