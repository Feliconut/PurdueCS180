import Exceptions.*;
import Field.*;
import Request.AuthenticateRequest;
import Request.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.http.WebSocket;
import java.util.ResourceBundle;
import java.util.UUID;

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
    private static BufferedReader bfr = null;
    private static PrintWriter pw = null;

    public void run() {
        new SignInWindow();
    }

    public static void main(String[] args) {
        new ClientWorker().connectToSocket();
    }

}

/**
 * PJ5-RegisterInterface
 * This class is an user interface that allows the user to register accounts
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
class RegisterWindow {
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


    public RegisterWindow() {
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

        ClientWorker clientWorker = new ClientWorker(this);
        ActionListener actionListener = e -> {
            if (e.getSource() == cancelBtn) {
                new SignInWindow();
                registerFrame.dispose();
            }
            if (e.getSource() == okBtn) {
                UUID uid = clientWorker.register();
                if (uid != null) {
                    new SignInWindow();
                    registerFrame.dispose();
                } else {
                    ageTf.setText(null);
                    nameTf.setText(null);
                    userTf.setText(null);
                    passTf.setText(null);
                }

            }
        };

        cancelBtn.addActionListener(actionListener);
        okBtn.addActionListener(actionListener);

    }

    public String getUsername() {
        return userTf.getText();
    }

    public String getPassword() {
        return passTf.getText();
    }

    public String getName() {
        return nameTf.getText();
    }

    public String getAgeString() {
        return ageTf.getText();
    }

}

/**
 * PJ5-MainInterface
 * This class is the main user interface after the user logged in.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
class MainWindow {
    private final Button logOutBtn = new Button("LOG OUT");
    private final Button settingBtn = new Button("SETTING");
    private JLabel chatroomLb = new JLabel("Chat Rooms");
    private JLabel newChatLb = new JLabel("Start a new chat");
    private JLabel invitePromptLb = new JLabel("Invite people to chat:");
    private JLabel invitedLb = new JLabel("No one has been invited yet!"); //need to be updated
    private TextField searchTf = new TextField(20);
    private Button addBtn = new Button("Add");
    private Button startBtn = new Button("Start Chatting!");


    public MainWindow() {
        //set up frame
        JFrame mainFrame = new JFrame("Main");
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
        Box vBoxOut = Box.createVerticalBox();

        //set up top panel
        Panel topP = new Panel();
        topP.setLayout(new FlowLayout(FlowLayout.LEFT));
        topP.add(logOutBtn);
        topP.add(settingBtn);
        vBoxOut.add(topP);

        //set up middle panel
        Box hBox = Box.createHorizontalBox();
        Panel chatroomP = new Panel();
        Panel newChatP = new Panel();
        hBox.add(chatroomP);
        hBox.add(newChatP);
        vBoxOut.add(hBox);
        vBoxOut.add(Box.createVerticalStrut(50));

        //add to chatroomP
        Box vBoxInLeft = Box.createVerticalBox();
        chatroomP.add(vBoxInLeft);
        Panel chatroomLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        chatroomLb.setBorder(new LineBorder(Color.gray));
        chatroomLbP.add(chatroomLb);
        vBoxInLeft.add(chatroomLbP);
        Panel conversationP = new Panel();
        vBoxInLeft.add(conversationP);

        //add to newChatP
        Box vBoxInRight = Box.createVerticalBox();
        newChatP.add(vBoxInRight);
        Panel newChatLbP = new Panel(new FlowLayout(FlowLayout.CENTER));
        newChatLbP.add(newChatLb);
        vBoxInRight.add(newChatLbP);
        Panel labelP = new Panel();
        Panel updateP = new Panel();
        Panel addP = new Panel();
        Panel startP = new Panel();
        vBoxInRight.add(Box.createVerticalStrut(10));
        vBoxInRight.add(labelP);
        vBoxInRight.add(updateP);
        vBoxInRight.add(Box.createVerticalStrut(50));
        vBoxInRight.add(addP);
        vBoxInRight.add(startP);
        labelP.add(invitePromptLb);
        updateP.add(invitedLb);
        addP.add(searchTf);
        addP.add(addBtn);
        startP.add(startBtn);

        //add panels to frame
        mainFrame.add(vBoxOut);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == settingBtn) {
                    new SettingWindow();
                    mainFrame.dispose();
                }
                if (e.getSource() == logOutBtn) {
                    //TODO log out request
                    mainFrame.dispose();
                }
                if (e.getSource() == addBtn) {
                    //TODO add people to chat request
                    //TODO update the invitedLb if the person is successfully added
                    JOptionPane.showMessageDialog(mainFrame, "Successfully added!",
                            "Invitation", JOptionPane.INFORMATION_MESSAGE);
                    //TODO if the person does not exist throw exception
                }
                if (e.getSource() == startBtn) {
                    //TODO start a chatting window
                    new ChatWindow();
                }
            }
        };

        logOutBtn.addActionListener(actionListener);
        settingBtn.addActionListener(actionListener);
        addBtn.addActionListener(actionListener);
        startBtn.addActionListener(actionListener);

    }
}

/**
 * PJ5-ManageProfileInterface
 * This class is an user interface that is used to mange profile
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */
class ManageProfileWindow {
    private JLabel nameLb = new JLabel("Name");
    private JLabel ageLb = new JLabel("age");
    private TextField nameTf = new TextField(20);
    private TextField ageTf = new TextField(20);
    private Button okBtn = new Button("OK");
    private Button cancelBtn = new Button("Cancel");

    public ManageProfileWindow() {
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
                    new SettingWindow();
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
class SettingWindow {
    private Button backBtn = new Button("Back");
    private Button deleteBtn = new Button("DELETE ACCOUNT");
    private Button manageProfileBtn = new Button("MANAGE PROFILE");
    private Button exportBtn = new Button("EXPORT CHAT HISTORY");
    private Button profileBtn = new Button("MY PROFILE");

    public SettingWindow() {
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
                    new MainWindow();
                    settingFrame.dispose();
                }
                if (e.getSource() == manageProfileBtn) {
                    new ManageProfileWindow();
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
 * PJ5-ChatWindow
 * This class in an user interface that allows the
 * user to chat
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

class ChatWindow {
    private JTextArea display = new JTextArea(15, 40);
    private TextField inputTf = new TextField(30);
    private Button sendBtn = new Button("SEND");
    private Button deleteBtn = new Button("Delete the group");
    private Button renameBtn = new Button("Rename the group");
    private String groupName;


    public ChatWindow() {
        //set frame
        JFrame chatFrame = new JFrame("Chat");
        chatFrame.setSize(400, 400);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chatFrame.setVisible(true);

        //set display window
        display.setLineWrap(true);
        display.setEditable(false);
        JScrollPane jsp = new JScrollPane(display);
        jsp.setBounds(display.getX(), display.getY(), display.getWidth(), display.getHeight());

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

class SignInWindow {
    private Button registerButton;
    private JLabel titleLb;
    private JLabel usernameLb;
    private JLabel passwordLb;
    private Button okButton;
    private TextField usernameTf;
    private TextField passwordTf;
    private String username;
    private String password;
    private ClientWorker clientWorker;

    public SignInWindow() {
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

        clientWorker = new ClientWorker(this);
        //actionListener
        ActionListener actionListener = e -> {
            if (e.getSource() == registerButton) {
                new RegisterWindow();
                frame.dispose();
            }
            if (e.getSource() == okButton) {
                if (clientWorker.signIn()) {
                    new MainWindow();
                    frame.dispose();
                } else {
                    usernameTf.setText(null);
                    passwordTf.setText(null);
                }
            }
        };
        registerButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);
    }

    public String getUsername() {
        return usernameTf.getText();
    }

    public String getPassword() {
        return passwordTf.getText();
    }
}

class ClientWorker {
    private SignInWindow signInWindow;
    private RegisterWindow registerWindow;
    private ManageProfileWindow manageProfileWindow;
    private MainWindow mainWindow;
    private ChatWindow chatWindow;
    private SettingWindow settingWindow;
    private MessageClient messageClient;
    private static Socket socket;

    public ClientWorker() {

    }

    public ClientWorker(SignInWindow signInWindow) {
        this.signInWindow = signInWindow;
    }

    public ClientWorker(RegisterWindow registerWindow) {
        this.registerWindow = registerWindow;
    }

    public ClientWorker(ManageProfileWindow manageProfileWindow) {
        this.manageProfileWindow = manageProfileWindow;
    }

    public ClientWorker(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public ClientWorker(ChatWindow chatWindow) {
        this.chatWindow = chatWindow;
    }

    public ClientWorker(SettingWindow settingWindow) {
        this.settingWindow = settingWindow;
    }

    public ClientWorker(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    /**
     * The method is used to send requests
     *
     * @param request some kind of request used
     * @param socket  the socket that connects the client and server
     * @return a response
     * @throws IOException
     * @throws RequestParsingException
     * @throws RequestFailedException
     */
    public static Response send(Request request, Socket socket) throws IOException, RequestParsingException,
            RequestFailedException {
        Response response;
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

        oos.writeObject(request);
        oos.flush();
        oos.close();

        try {
            response = (Response) ois.readObject();
        } catch (ClassNotFoundException | ClassCastException e) {
            e.printStackTrace();
            throw new RequestParsingException();
        }

        if (response.state) {
            return response;
        } else {
            throw new RequestFailedException();
        }
//        else {
//            if (response.exception != null) {
//                throw (RequestFailedException) response.exception;
//            } else {
//                throw new RequestFailedException();
//            }
//        }
    }

    /**
     * The method sends an authenticate request and receives
     * a response
     * @return true if the user passed the authentication, false
     * if the user is not registered or entered wrong password
     */
    public boolean signIn() {
        String username = signInWindow.getUsername();
        String password = signInWindow.getPassword();

        try {
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(new Credential(username, password));
            send(authenticateRequest, socket);
            return true;
        } catch (UserNotFoundException | InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (RequestParsingException e) {
            e.printStackTrace();
            return false;
        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * The method sends a register request to the server and receives a
     * registerResponse that contains the user's uuid.
     *
     * @return return UUID if the success, else return null.
     */
    public UUID register() {
        RegisterResponse response;
        String username = registerWindow.getUsername();
        String password = registerWindow.getPassword();
        String name = registerWindow.getName();
        try {
            Credential credential = new Credential(username, password);

            Profile profile = new Profile(name, Integer.parseInt(registerWindow.getAgeString()));

            RegisterRequest registerRequest = new RegisterRequest(credential, profile);

            response = (RegisterResponse) send(registerRequest, socket);

            UUID my_uuid = response.uuid;

            JOptionPane.showMessageDialog(null, "Registered successfully",
                    "Register", JOptionPane.INFORMATION_MESSAGE);
            return my_uuid;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (RequestParsingException | UserExistsException |
                InvalidUsernameException | InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Used to connect to the server
     */
    public void connectToSocket() {
        String hostname;
        String portString;
        int port;

        try {
            hostname = JOptionPane.showInputDialog(null, "Please enter the hostname:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);
            if (hostname == null) {
                return;
            }

            portString = JOptionPane.showInputDialog(null, "Please enter the port number:",
                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);
            if (portString == null) {
                return;
            }
            port = Integer.parseInt(portString);

            socket = new Socket(hostname, port);

            if (socket.isConnected()) {
                SwingUtilities.invokeLater(new MessageClient());
            }


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection failed!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
            return;
        } //connection established
    }
}


