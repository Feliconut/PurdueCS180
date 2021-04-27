import Field.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        JFrame frame = new JFrame("sign-in");
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

        //actionListener
        ActionListener actionListener = e -> {
            if (e.getSource() == registerButton) {
                SwingUtilities.invokeLater(new Register());
                frame.dispose();
            }
        };

        //set buttons and labels
        registerButton = new Button("Register");
        registerButton.addActionListener(actionListener);

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

class Register implements Runnable {
    private final JLabel nameLb = new JLabel("    Name");
    private final JLabel ageLb = new JLabel("     Age");
    private final JLabel userLb = new JLabel("Username");
    private final JLabel passLb = new JLabel("Password");
    private final TextField nameTf = new TextField(20);
    private final TextField ageTf = new TextField(20);
    private final TextField userTf = new TextField(20);
    private final TextField passTf = new TextField(20);
    private final Button okBtn = new Button("OK");
    private final Button cancelBtn = new Button("Cancel");

    @Override
    public void run() {
        //set frame
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(600, 400);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        registerFrame.setVisible(true);

        //set panel
        Box box = Box.createVerticalBox();
        Panel nameP = new Panel();
        Panel ageP = new Panel();
        Panel userP = new Panel();
        Panel passP = new Panel();
        Panel bottomP = new Panel();
        box.add(Box.createVerticalStrut(70));
        box.add(nameP);
        box.add(ageP);
        box.add(userP);
        box.add(passP);
        box.add(bottomP);
        box.add(Box.createVerticalStrut(70));

        //add to nameP
        nameP.add(nameLb);
        nameP.add(Box.createHorizontalStrut(5));
        nameP.add(nameTf);

        //add to ageP
        ageP.add(ageLb);
        nameP.add(Box.createHorizontalStrut(10));
        ageP.add(ageTf);

        //add to userP
        userP.add(userLb);
        userP.add(userTf);

        //add to passP
        passP.add(passLb);
        passP.add(passTf);

        //add to bottomP
        bottomP.add(okBtn);
        bottomP.add(cancelBtn);

        //add to frame
        registerFrame.add(box);

        ActionListener actionListener = e -> {
            if (e.getSource() == cancelBtn) {
                SwingUtilities.invokeLater(new MessageClient());
                registerFrame.dispose();
            }
        };

        cancelBtn.addActionListener(actionListener);

    }
}

class main implements Runnable {
    private final Button logOutBtn = new Button("Log out");
    private final Button profileBtn = new Button("PROFILE");
    private final Button chatBtn = new Button("CHATROOM");
    private final Button addBtn = new Button("ADD A FRIEND");
    private final Button startGroupBtn = new Button("START A GROUP");
    private final Button joinGroupBtn = new Button("JOIN A GROUP");

    @Override
    public void run() {
        //set up frame
        JFrame mainFrame = new JFrame("Main");
        mainFrame.setSize(600,400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);

        //set up panel
        Panel topP = new Panel();
        topP.setLayout(new FlowLayout(FlowLayout.LEFT));
        Panel midP = new Panel();

        //add to topP
        topP.add(logOutBtn);

        //add to midP
        midP.add(profileBtn);
        midP.add(chatBtn);
        midP.add(addBtn);
        midP.add(startGroupBtn);
        midP.add(joinGroupBtn);

        //add panels to box then to frame
        Box box = Box.createVerticalBox();
        box.add(topP);
        box.add(midP);
        mainFrame.add(box);
    }
}
