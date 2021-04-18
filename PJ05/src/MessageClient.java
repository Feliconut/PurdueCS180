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
     *
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
     *
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
 * This class is an user interface that allows the user to register accounts
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class RegisterInterface implements Runnable {
    private final JLabel nameLb = new JLabel("Name");
    private final JLabel ageLb = new JLabel("Age");
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
        nameP.add(Box.createHorizontalStrut(20));
        nameP.add(nameTf);

        //add to ageP
        ageP.add(ageLb);
        ageP.add(Box.createHorizontalStrut(30));
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
 * This class is the main user interface after the user logged in.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
class MainInterface implements Runnable {
    private final Button logOutBtn = new Button("Log out");
    private final Button chatBtn = new Button("CHATROOM");
    private final Button startChatBtn = new Button("START CHAT");
    private final Button settingBtn = new Button("SETTING");


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
        midP.add(chatBtn);
        midP.add(startChatBtn);
        midP.add(settingBtn);


        //add panels to box then to frame
        Box box = Box.createVerticalBox();
        box.add(topP);
        box.add(midP);
        mainFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == settingBtn) {
                    SwingUtilities.invokeLater(new SettingInterface());
                    mainFrame.dispose();
                }
                if (e.getSource() == chatBtn) {
                    //TODO start a chatroom window
                }
                if (e.getSource() == startChatBtn) {
                    SwingUtilities.invokeLater(new StartChatInterface());
                   // mainFrame.dispose();
                }
                if (e.getSource() == logOutBtn) {
                    //TODO log out request
                    mainFrame.dispose();
                }
            }
        };

        chatBtn.addActionListener(actionListener);
        startChatBtn.addActionListener(actionListener);
        logOutBtn.addActionListener(actionListener);
        settingBtn.addActionListener(actionListener);


    }
}

/**
 * PJ5-ManageProfileInterface
 * This class is an user interface that is used to mange profile
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
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
        profileFrame.setSize(600, 400);
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
        ageP.add(Box.createHorizontalStrut(5));
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
                    SwingUtilities.invokeLater(new SettingInterface());
                    profileFrame.dispose();
                }
            }
        };

        okBtn.addActionListener(actionListener);
        cancelBtn.addActionListener(actionListener);
    }
}


/**
 * PJ5-SettingInterface
 * This class is an user interface for user
 * to set profile and delete account
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
class SettingInterface implements Runnable {
    private Button backBtn = new Button("Back");
    private Button deleteBtn = new Button("DELETE ACCOUNT");
    private Button manageProfileBtn = new Button("MANAGE PROFILE");
    private Button exportBtn = new Button("EXPORT CHAT HISTORY");
    private Button profileBtn = new Button("MY PROFILE");


    @Override
    public void run() {
        //set frame
        JFrame settingFrame = new JFrame("Setting");
        settingFrame.setSize(600, 400);
        settingFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        settingFrame.setLocationRelativeTo(null);
        settingFrame.setVisible(true);

        //set panel
        Box box = Box.createVerticalBox();
        Panel topP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel midP = new Panel();
        box.add(topP);
        box.add(Box.createVerticalStrut(50));
        box.add(midP);
        box.add(Box.createVerticalStrut(50));

        //add to topP
        topP.add(backBtn);

        //add to midP
        midP.add(profileBtn);
        midP.add(manageProfileBtn);
        midP.add(deleteBtn);
        midP.add(exportBtn);

        //add to frame
        settingFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtn) {
                    SwingUtilities.invokeLater(new MainInterface());
                    settingFrame.dispose();
                }
                if (e.getSource() == manageProfileBtn) {
                    SwingUtilities.invokeLater(new ManageProfileInterface());
                    settingFrame.dispose();
                }
                if (e.getSource() == deleteBtn) {
                    int answer = JOptionPane.showConfirmDialog(settingFrame,
                            "Are you sure to delete your account? " +
                                    "All account information will be deleted" +
                                    "and cannot be recovered.",
                            "Delete Account", JOptionPane.OK_CANCEL_OPTION);
                    if (answer == JOptionPane.OK_OPTION) {
                        //TODO sent delete account request
                        JOptionPane.showMessageDialog(settingFrame,
                                "Successfully deleted!", "Delete Account",
                                JOptionPane.INFORMATION_MESSAGE);
                        settingFrame.dispose();
                    }
                }
                if (e.getSource() == exportBtn) {
                    //TODO export request
                }
                if (e.getSource() == profileBtn) {
                    JOptionPane.showMessageDialog(settingFrame, "userProfile", "Profile",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        };
        manageProfileBtn.addActionListener(actionListener);
        backBtn.addActionListener(actionListener);
        deleteBtn.addActionListener(actionListener);
        exportBtn.addActionListener(actionListener);
        profileBtn.addActionListener(actionListener);
    }
}

/**
 * PJ5-StartChatInterface
 * This class in an user interface that allows the
 * user to add people to a chat
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class StartChatInterface implements Runnable {
    private JLabel invitePromptLb = new JLabel("Invite people to chat:");
    private JLabel invitedLb = new JLabel("No one has been invited yet!"); //need to be updated
    private TextField searchTf = new TextField(20);
    private Button addBtn = new Button("Add");
    private Button startBtn = new Button("Start Chatting!");
    @Override
    public void run() {
        //set frame
        JFrame startFrame = new JFrame("Start Chat");
        startFrame.setSize(400,350);
        startFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);

        //set panels
        Box box = Box.createVerticalBox();
        Panel labelP = new Panel();
        Panel updateP = new Panel();
        Panel addP = new Panel();
        Panel startP = new Panel();
        box.add(Box.createVerticalStrut(50));
        box.add(labelP);
        box.add(updateP);
        box.add(Box.createVerticalStrut(50));
        box.add(addP);
        box.add(startP);
        box.add(Box.createVerticalStrut(50));

        //add to panels
        labelP.add(invitePromptLb);
        updateP.add(invitedLb);
        addP.add(searchTf);
        addP.add(addBtn);
        startP.add(startBtn);

        //add to frame
        startFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == addBtn) {
                    //TODO add people to chat request
                    //TODO update the invitedLb if the person is successfully added
                    JOptionPane.showMessageDialog(startFrame, "Successfully added!",
                            "Invitation", JOptionPane.INFORMATION_MESSAGE);
                    //TODO if the person does not exist throw exception
                }
                if (e.getSource() == startBtn) {
                    //TODO start a chatting window
                    SwingUtilities.invokeLater(new ChatWindow());
                    startFrame.dispose();
                }
            }
        };
        addBtn.addActionListener(actionListener);
        startBtn.addActionListener(actionListener);
    }
}
/**
 * PJ5-ChatWindow
 * This class in an user interface that allows the
 * user to chat
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class ChatWindow implements Runnable {
    private JTextArea display = new JTextArea(15,40);
    private TextField inputTf = new TextField(30);
    private Button sendBtn = new Button("SEND");
    private Button deleteBtn = new Button("Delete the group");
    private Button renameBtn = new Button("Rename the group");
    private String groupName;
    @Override
    public void run() {
        //set frame
        JFrame chatFrame = new JFrame("Chat");
        chatFrame.setSize(400,400);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chatFrame.setVisible(true);

        //set display window
        display.setLineWrap(true);
        display.setEditable(false);
        JScrollPane jsp = new JScrollPane(display);
        jsp.setBounds(display.getX(),display.getY(),display.getWidth(),display.getHeight());

        //set panels
        Box box = Box.createVerticalBox();
        Panel topP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel midP = new Panel();
        Panel bottomP = new Panel();
        box.add(topP);
        box.add(midP);
        box.add(bottomP);

        //add to panels
        topP.add(renameBtn);
        topP.add(deleteBtn);
        midP.add(jsp);
        bottomP.add(inputTf);
        bottomP.add(sendBtn);

        //add to frame
        chatFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == deleteBtn) {
                    //TODO send verification request
                   int answer = JOptionPane.showConfirmDialog(chatFrame, "Are you sure to delete" +
                            "the conversation?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                    if (answer == JOptionPane.OK_OPTION) {
                        //TODO send delete request
                    }
                }
                if (e.getSource() == renameBtn) {
                    groupName = JOptionPane.showInputDialog(chatFrame, "Enter new group name:",
                             "Rename", JOptionPane.PLAIN_MESSAGE);
                    chatFrame.setTitle(groupName);
                }
                if (e.getSource() == sendBtn) {
                    //TODO send message request
                }
            }
        };
        deleteBtn.addActionListener(actionListener);
        renameBtn.addActionListener(actionListener);
        sendBtn.addActionListener(actionListener);
    }
}


