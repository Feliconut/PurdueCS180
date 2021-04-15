import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * A Simple Calculator.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class CalculatorClient
{
    public static void main(String[] args)
    {
        String hostName;
        int port;
        Socket socket;
        BufferedReader reader;
        PrintWriter writer;


        JOptionPane.showMessageDialog(null, "Welcome",
                "Calculator", JOptionPane.INFORMATION_MESSAGE);

        while (true)
        {
            hostName = JOptionPane.showInputDialog(null, "Enter Host Name", "Calculator",
                    JOptionPane.QUESTION_MESSAGE);
            if (hostName == null)
            {

            } else
            {
                break;
            }
        }
        while (true)
        {
            try
            {
                port = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Port Number", "Calculator",
                        JOptionPane.QUESTION_MESSAGE));
                break;
            } catch (NumberFormatException ignored)
            {
            }

        }

        try
        {
            socket = new Socket(hostName, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e)
        {
            JOptionPane.showMessageDialog(null, "Connection Failed",
                    "Calculator", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(null, "Connection Established",
                "Calculator", JOptionPane.INFORMATION_MESSAGE);


        while (true)
        {
            while (true)
            {
                String res;
                String inputEqn;

                inputEqn = JOptionPane.showInputDialog(null, "Enter Equation", "Calculator",
                        JOptionPane.QUESTION_MESSAGE);
                if (inputEqn == null)
                {
                    break;
                }
                try
                {
                    writer.write(inputEqn);
                    writer.println();
                    writer.flush();

                    res = reader.readLine();
                } catch (Exception e)
                {
                    JOptionPane.showMessageDialog(null, "Connection Error. Ending Session",
                            "Calculator", JOptionPane.ERROR_MESSAGE);
                    return;

                }
                if (res.equals("ERR"))
                {
                    JOptionPane.showMessageDialog(null, "Invalid Equation",
                            "Calculator", JOptionPane.ERROR_MESSAGE);

                } else
                {
                    JOptionPane.showMessageDialog(null, "result is " + res,
                            "Calculator", JOptionPane.INFORMATION_MESSAGE);
                    break;
                }
            }


            int option = JOptionPane.showConfirmDialog(null,
                    "Would you like to do another calculation?", "Calculator", JOptionPane.YES_NO_OPTION);
            if (option != 0)
            {
                JOptionPane.showMessageDialog(null, "Bye bye",
                        "Calculator", JOptionPane.INFORMATION_MESSAGE);
                writer.write("SHUTDOWN");
                writer.println();
                writer.flush();
                break;
            }
        }
        try
        {
            writer.close();
            reader.close();
            socket.close();
        } catch (Exception ignored)
        {
        }
    }
}
