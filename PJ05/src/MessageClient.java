import Field.User;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageClient {

    private User user;

    public static void main(String[] args) {
        String hostname;
        String portString;
        int port;
        Socket socket = null;
        BufferedReader bfr = null;
        PrintWriter pw = null;

        try {
            hostname = JOptionPane.showInputDialog(null, "Please enter the hostname:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            portString = JOptionPane.showInputDialog(null, "Please enter the port number:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            port = Integer.parseInt(portString);

            socket = new Socket(hostname, port);

            bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            pw = new PrintWriter(socket.getOutputStream());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection failed!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } finally {
            try {
                if (bfr != null) {
                    bfr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (pw != null) {
                pw.close();
            }
            try {
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } //connection established





        //TODO use calculator client code, establish connection


        //TODO display main UI, popup sign-in window.

        //TODO query for new events every few seconds.

    }
}
