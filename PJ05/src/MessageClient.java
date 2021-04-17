import Field.Credential;
import Field.User;
import Request.AuthenticateRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.http.WebSocket;

/**
 * PJ5-MessageClient
 * This class enables connections between the client and
 * the server. It also displays the log-in user interface.
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
    private String username;
    private String password;
    private static BufferedReader bfr = null;
    private static PrintWriter pw = null;

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

        //actionListener
        ActionListener actionListener = e -> {
            if (e.getSource() == registerButton) {
                SwingUtilities.invokeLater(new RegisterInterface());
                frame.dispose();
            }
            if (e.getSource() == okButton) {
                //TODO send authentication request
                username = usernameTf.getText();
                password = passwordTf.getText();
                authenticateRequest(username, password);
                if (authenticateResponse()) {
                    SwingUtilities.invokeLater(new MainInterface());
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(frame, "response message", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        };
        registerButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);

    }

    public static void main(String[] args) {
        String hostname;
        String portString;
        int port;
        Socket socket;


        try {
            hostname = JOptionPane.showInputDialog(null, "Please enter the hostname:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            portString = JOptionPane.showInputDialog(null, "Please enter the port number:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);

            port = Integer.parseInt(portString);

            socket = new Socket(hostname, port);

            if (socket.isConnected()) {
                SwingUtilities.invokeLater(new MessageClient());
                bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                pw = new PrintWriter(socket.getOutputStream());
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

    }

    /**
     * The method creates an authenticate request to the server
     * @param username entered by the user
     * @param password entered by the user
     */
    public void authenticateRequest(String username, String password) {
        Credential credential = new Credential(username, password);
        AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
        pw.println(authenticateRequest.toString());
    }

    /**
     * The method reads the authenticate response
     * @return true if passes the authentication
     */
    public boolean authenticateResponse() {
        String response;
        try {
            response = bfr.readLine();
        } catch (IOException e) {
            return false;
        }
        return response.equals("true");
    }

}

/**
 * PJ5-RegisterInterface
 * This class is a interface that allows the user to register accounts
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class RegisterInterface implements Runnable {
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
            if (e.getSource() == okBtn) {
                //TODO send a register request
                SwingUtilities.invokeLater(new MessageClient());
                registerFrame.dispose();
            }
        };

        cancelBtn.addActionListener(actionListener);
        okBtn.addActionListener(actionListener);

    }
}

/**
 * PJ5-MainInterface
 * This class is the main interface after the user logged in.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class MainInterface implements Runnable {
    private final Button logOutBtn = new Button("Log out");
    private final Button profileBtn = new Button("PROFILE");
    private Button manageProfileBtn = new Button("MANAGE PROFILE");
    private final Button chatBtn = new Button("CHATROOM");
    //private final Button addBtn = new Button("ADD A FRIEND");
    //private final Button startGroupBtn = new Button("START A GROUP");
    //private final Button joinGroupBtn = new Button("JOIN A GROUP");

    @Override
    public void run() {
        //set up frame
        JFrame mainFrame = new JFrame("Main");
        mainFrame.setSize(600, 400);
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
        midP.add(manageProfileBtn);
        midP.add(chatBtn);
//        midP.add(addBtn);
//        midP.add(startGroupBtn);
//        midP.add(joinGroupBtn);

        //add panels to box then to frame
        Box box = Box.createVerticalBox();
        box.add(topP);
        box.add(midP);
        mainFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == profileBtn) {
                    JOptionPane.showMessageDialog(mainFrame, "userProfile", "Profile",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                if (e.getSource() == manageProfileBtn) {
                    SwingUtilities.invokeLater(new ManageProfileInterface());
                    mainFrame.dispose();
                }
                if (e.getSource() == chatBtn) {
                    //TODO start a chat window
                }
                if (e.getSource() == logOutBtn) {
                    mainFrame.dispose();
                }
//                if (e.getSource() == addBtn) {
//                    //TODO pop up window
//                }
//                if (e.getSource() == startGroupBtn) {
//                    //TODO start a group
//                }
//                if (e.getSource() == joinGroupBtn) {
//                    //TODO join group window
//                }
            }
        };

        profileBtn.addActionListener(actionListener);
        manageProfileBtn.addActionListener(actionListener);
        chatBtn.addActionListener(actionListener);
        logOutBtn.addActionListener(actionListener);
//        addBtn.addActionListener(actionListener);
//        startGroupBtn.addActionListener(actionListener);
//        joinGroupBtn.addActionListener(actionListener);


    }
}

class ManageProfileInterface implements Runnable {
    private JLabel nameLb = new JLabel("Name");
    private JLabel ageLb = new JLabel("age");
    private TextField nameTf = new TextField(20);
    private TextField ageTf = new TextField(20);
    private Button okBtn = new Button("OK");
    private Button cancelBtn = new Button("Cancel");

    public void run() {
        //set frame
        JFrame profileFrame = new JFrame("Manage Profile");
        profileFrame.setSize(600,400);
        profileFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        profileFrame.setLocationRelativeTo(null);
        profileFrame.setVisible(true);

        //set panel
        Box box = Box.createVerticalBox();
        Panel nameP = new Panel();
        Panel ageP = new Panel();
        Panel bottomP = new Panel();
        box.add(Box.createVerticalStrut(100));
        box.add(nameP);
        box.add(ageP);
        box.add(bottomP);
        box.add(Box.createVerticalStrut(100));

        //add to name panel
        nameP.add(nameLb);
        nameP.add(nameTf);

        //add to age panel
        ageP.add(ageLb);
        ageP.add(ageTf);

        //add to bottom panel
        bottomP.add(okBtn);
        bottomP.add(cancelBtn);

        //add to frame
        profileFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == okBtn) {
                    //TODO change profile request
                }
                if (e.getSource() == cancelBtn) {
                    SwingUtilities.invokeLater(new MainInterface());
                    profileFrame.dispose();
                }
            }
        };

        okBtn.addActionListener(actionListener);
        cancelBtn.addActionListener(actionListener);
    }
}


