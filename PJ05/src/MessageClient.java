import Field.User;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * PJ5-MessageClient
 * This class enables connections between the client and
 * the server and display user interface.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

public class MessageClient implements Runnable {
    private User user;
    private Button registerButton;
    private JLabel titleLb;
    private JLabel usernameLb;
    private JLabel passwordLb;
    private Button okButton;
    private TextField usernameTf;
    private TextField passwordTf;

    public void run() {
        //set frame
        JFrame frame = new JFrame("main");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        //set panels
        Box vertical = Box.createVerticalBox();
        Panel topPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        Panel titlePanel = new Panel();
        Panel userPanel = new Panel();
        Panel passwordPanel = new Panel();
        Panel okPanel = new Panel();

        //set buttons and labels
        registerButton = new Button("Register");
        okButton = new Button("OK");
        titleLb = new JLabel("<html><center><font size='20'>PJ5 Messaging</font></center></html>");
        usernameLb = new JLabel("Username");
        passwordLb = new JLabel("Password");
        usernameTf = new TextField(20);
        passwordTf = new TextField(20);

        //add to top panel
        topPanel.add(registerButton);

        //add to title panel
        titlePanel.add(titleLb);

        //add to username panel
        userPanel.add(usernameLb);
        userPanel.add(usernameTf);

        //add to password panel
        passwordPanel.add(passwordLb);
        passwordPanel.add(passwordTf);

        //add to ok panel
        okPanel.add(okButton);

        // add to frame
        vertical.add(topPanel);
        vertical.add(Box.createVerticalStrut(50));
        vertical.add(titlePanel);
        vertical.add(userPanel);
        vertical.add(passwordPanel);
        vertical.add(okPanel);
        vertical.add(Box.createVerticalStrut(50));
        frame.add(vertical);

    }

    public static void main(String[] args) {
        String hostname;
        String portString;
        int port;
        Socket socket;
        BufferedReader bfr = null;
        PrintWriter pw = null;

        try {
            hostname = JOptionPane.showInputDialog(null, "Please enter the hostname:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            portString = JOptionPane.showInputDialog(null, "Please enter the port number:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            port = Integer.parseInt(portString);

            socket = new Socket(hostname, port);

            if (socket.isConnected()) {
                SwingUtilities.invokeLater(new MessageClient());
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            //return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection failed!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            //return;
        } //connection established


        //TODO display main UI, popup sign-in window.

        //TODO query for new events every few seconds.

    }
}
