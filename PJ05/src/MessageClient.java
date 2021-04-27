import Exceptions.*;
import Field.*;
import Request.AuthenticateRequest;
import Request.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * PJ5-MessageClient
 * This class enables connections between the client and
 * the server. It also displays the log-in user interface.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

public class MessageClient implements Runnable {
    private static BufferedReader bfr = null;
    private static PrintWriter pw = null;

    public void run() {
        new Window();
    }

    public static void main(String[] args) {
        new ClientWorker().connectToSocket();
    }

}


class Window {
    private Button registerButtonSign;
    private JLabel titleLbSign;
    private JLabel usernameLbSign;
    private JLabel passwordLbSign;
    private Button okButtonSign;
    private TextField usernameTfSign;
    private JPasswordField passwordTfSign;
    private ClientWorker clientWorker = new ClientWorker(this);

    public Window() {
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
        registerButtonSign = new Button("Register");
        okButtonSign = new Button("OK");
        titleLbSign = new JLabel("<html><center><font size='20'>PJ5 Messaging</font></center></html>");
        usernameLbSign = new JLabel("Username");
        passwordLbSign = new JLabel("Password");
        usernameTfSign = new TextField(20);
        passwordTfSign = new JPasswordField(20);
        passwordTfSign.setEchoChar('*');

        //add to top panel
        topPanel.add(registerButtonSign);

        //add to title panel
        titlePanel.add(titleLbSign);

        //add to username panel
        userPanel.add(usernameLbSign);
        userPanel.add(usernameTfSign);

        //add to password panel
        passwordPanel.add(passwordLbSign);
        passwordPanel.add(passwordTfSign);

        //add to ok panel
        okPanel.add(okButtonSign);

        // add to frame
        vertical.add(topPanel);
        vertical.add(Box.createVerticalStrut(50));
        vertical.add(titlePanel);
        vertical.add(userPanel);
        vertical.add(passwordPanel);
        vertical.add(okPanel);
        vertical.add(Box.createVerticalStrut(50));
        frame.add(vertical);

        // clientWorker = new ClientWorker(this);
        //actionListener
        ActionListener actionListener = e -> {
            if (e.getSource() == registerButtonSign) {
                registerWindow();
            }
            if (e.getSource() == okButtonSign) {
                if (clientWorker.signIn()) {
                    mainWindow();
                    frame.dispose();
                } else {
                    usernameTfSign.setText(null);
                    passwordTfSign.setText(null);
                }
            }
        };
        registerButtonSign.addActionListener(actionListener);
        okButtonSign.addActionListener(actionListener);
    }


    private final JLabel rgNameLb = new JLabel("Name");
    private final JLabel rgAgeLb = new JLabel("Age");
    private final JLabel rgUserLb = new JLabel("Username");
    private final JLabel rgPassLb = new JLabel("Password");
    private final TextField rgNameTf = new TextField(20);
    private final TextField rgAgeTf = new TextField(20);
    private final TextField rgUserTf = new TextField(20);
    private final JPasswordField rgPassTf = new JPasswordField(20);
    private final Button rgOkBtn = new Button("OK");
    private final Button rgCancelBtn = new Button("Cancel");


    public void registerWindow() {
        //set frame
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(600, 400);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
        nameP.add(rgNameLb);
        nameP.add(Box.createHorizontalStrut(20));
        nameP.add(rgNameTf);

        //add to ageP
        ageP.add(rgAgeLb);
        ageP.add(Box.createHorizontalStrut(30));
        ageP.add(rgAgeTf);

        //add to userP
        userP.add(rgUserLb);
        userP.add(rgUserTf);

        //add to passP
        passP.add(rgPassLb);
        passP.add(rgPassTf);

        //add to bottomP
        bottomP.add(rgOkBtn);
        bottomP.add(rgCancelBtn);

        //add to frame
        registerFrame.add(box);

        //ClientWorker clientWorker = new ClientWorker(this);
        ActionListener actionListener = e -> {
            if (e.getSource() == rgCancelBtn) {
                registerFrame.dispose();
            }
            if (e.getSource() == rgOkBtn) {
                UUID uid = clientWorker.register();
                if (uid != null) {
                    registerFrame.dispose();
                } else {
                    rgAgeTf.setText(null);
                    rgNameTf.setText(null);
                    rgUserTf.setText(null);
                    rgPassTf.setText(null);
                }

            }
        };

        rgCancelBtn.addActionListener(actionListener);
        rgOkBtn.addActionListener(actionListener);

    }


    private final Button logOutBtnM = new Button("LOG OUT");
    private final Button settingBtnM = new Button("SETTING");
    private JLabel chatroomLbM = new JLabel("Chat Rooms");
    private JLabel newChatLbM = new JLabel("Create a new chat");
    private JLabel groupNameLbM = new JLabel("Enter a group name");
    private TextField groupNameTfM = new TextField(20);
    private JLabel inviteLbM = new JLabel("Enter people's uuids you want to invite\n(Separated by commas)");
    private JTextField inviteTfM = new JTextField(20);
   // private Button addBtnM = new Button("ADD");
    private Button startBtnM = new Button("CREATE");
    private JLabel groupInfoLb = new JLabel();
    private ArrayList<UUID> my_conversation_uuids;
    private DefaultListModel<UUID> conversationModel = new DefaultListModel<>();
    JList<UUID> list;


    public void mainWindow() {
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
        topP.add(logOutBtnM);
        topP.add(settingBtnM);
        vBoxOut.add(topP);

        //set up middle panel
        Box hBox = Box.createHorizontalBox();
        Panel chatroomP = new Panel();
        Panel newChatP = new Panel();
        hBox.add(chatroomP);
        hBox.add(newChatP);
        vBoxOut.add(hBox);
        vBoxOut.add(Box.createVerticalStrut(50));

        //set up the conversation list
        my_conversation_uuids = clientWorker.getConversation_uuid_list();
        for (UUID my_conversation_uuid : my_conversation_uuids) {
            conversationModel.addElement(my_conversation_uuid);
        }
        list = new JList<>(conversationModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //add to chatroomP
        Panel chatroomLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        chatroomLbP.add(chatroomLbM);
        chatroomP.add(chatroomLbP);
        JScrollPane jsp = new JScrollPane(list);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        chatroomP.add(jsp);
        Panel groupInfoP = new Panel();
        groupInfoP.add(groupInfoLb);
        chatroomP.add(groupInfoP);

        //set up new chat panel
        Box vBoxInRight = Box.createVerticalBox();
        newChatP.add(vBoxInRight);
        Panel newChatLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel labelP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addP = new Panel();
        Panel groupNameP = new Panel();
        Panel startP = new Panel();

        //add panels to box
        vBoxInRight.add(newChatLbP);
        vBoxInRight.add(Box.createVerticalStrut(10));
        vBoxInRight.add(labelP);
        vBoxInRight.add(addLbP);
        vBoxInRight.add(addP);
        vBoxInRight.add(groupNameP);
        vBoxInRight.add(startP);

        newChatLbP.add(newChatLbM);
        labelP.add(groupNameLbM);
        groupNameP.add(groupNameTfM);
        addLbP.add(inviteLbM);
        addP.add(inviteTfM);
        //addP.add(addBtnM);
        startP.add(startBtnM);

        //add panels to frame
        mainFrame.add(vBoxOut);

        //if the list is clicked twice open up the selected conversation
        //if the list is clicked once show the conversation name in the label below
        //if the list is right-clicked pop up delete message
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Conversation conversation = clientWorker.getConversation(list.getSelectedValue());
                    chatWindow(conversation);
                    clientWorker.importCurrentHistory();
                }

                if (e.getClickCount() == 1) {
                    groupInfoLb.setText(clientWorker.getGroupName(list.getSelectedValue()));
                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    int answer = JOptionPane.showConfirmDialog(null,
                            "Delete selected conversation from your list?",
                            "Delete", JOptionPane.YES_NO_OPTION);

                    if (answer == JOptionPane.YES_OPTION) {
                        if (clientWorker.deleteConversation(list.getSelectedValue())) {
                            conversationModel.remove(list.getSelectedIndex());
                            groupInfoLb.setText(null);
                        }
                    }
                }
            }
        });

        ActionListener actionListener = e -> {
            if (e.getSource() == settingBtnM) {
                settingWindow();
                mainFrame.dispose();
            }
            if (e.getSource() == logOutBtnM) {
                int answer = JOptionPane.showConfirmDialog(mainFrame,
                        "Are you sure to log out?",
                        "log out", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    clientWorker.logOut();
                    mainFrame.dispose();
                    JOptionPane.showMessageDialog(null,
                            "Log out successfully!",
                            "Log out", JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (e.getSource() == startBtnM) {
                String groupName = groupNameTfM.getText();
                String inviteString = inviteTfM.getText();
                Conversation conversation = clientWorker.createConversation(groupName, inviteString);
                UUID newConversation_uuid = conversation.uuid;
                conversationModel.addElement(newConversation_uuid); //add the conversation to list
                chatWindow(conversation);
                groupNameLbM.setText(null);
                inviteTfM.setText(null);
            }
        };

        logOutBtnM.addActionListener(actionListener);
        settingBtnM.addActionListener(actionListener);
        startBtnM.addActionListener(actionListener);

    }

    private Button backBtnSetting = new Button("Back");
    private Button deleteBtnSetting = new Button("DELETE ACCOUNT");
    private Button manageProfileBtnSetting = new Button("MANAGE PROFILE");
    //private Button exportBtnSetting = new Button("EXPORT CHAT HISTORY");
    private Button profileBtnSetting = new Button("MY PROFILE");

    public void settingWindow() {
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
        topP.add(backBtnSetting);

        //add to midP
        midP.add(profileBtnSetting);
        midP.add(manageProfileBtnSetting);
        midP.add(deleteBtnSetting);
        //midP.add(exportBtnSetting);

        //add to frame
        settingFrame.add(box);

        // ClientWorker clientWorker = new ClientWorker(this);
        ActionListener actionListener = e -> {
            if (e.getSource() == backBtnSetting) {
                mainWindow();
                settingFrame.dispose();
            }
            if (e.getSource() == manageProfileBtnSetting) {
                manageProfileWindow();
                settingFrame.dispose();
            }
            if (e.getSource() == deleteBtnSetting) {
                int answer = JOptionPane.showConfirmDialog(settingFrame,
                        "Are you sure to delete your account? " +
                                "All account information will be deleted" +
                                "which cannot be recovered.",
                        "Delete Account", JOptionPane.OK_CANCEL_OPTION);

                if (answer == JOptionPane.OK_OPTION) {
                    if (clientWorker.deleteAccount()) {
                        settingFrame.dispose();
                        JOptionPane.showMessageDialog(null,
                                "Successfully deleted!", "Delete Account",
                                JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
//            if (e.getSource() == exportBtnSetting) {
//
//            }

            if (e.getSource() == profileBtnSetting) {
                Profile profile = clientWorker.getMyProfile();
                String name = profile.name;
                int age = profile.age;
                String message = String.format("Name: %s\n" +
                        "Age: %d\n", name, age);
                JOptionPane.showMessageDialog(settingFrame, message, "Profile",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
        manageProfileBtnSetting.addActionListener(actionListener);
        backBtnSetting.addActionListener(actionListener);
        deleteBtnSetting.addActionListener(actionListener);
        //exportBtnSetting.addActionListener(actionListener);
        profileBtnSetting.addActionListener(actionListener);
    }

    private JLabel nameLbProfile = new JLabel("Name");
    private JLabel ageLbProfile = new JLabel("age");
    private TextField nameTfProfile = new TextField(20);
    private TextField ageTfProfile = new TextField(20);
    private Button okBtnProfile = new Button("OK");
    private Button cancelBtnProfile = new Button("Cancel");

    public void manageProfileWindow() {
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
        nameP.add(nameLbProfile);
        nameP.add(nameTfProfile);

        //add to age panel
        ageP.add(ageLbProfile);
        ageP.add(Box.createHorizontalStrut(5));
        ageP.add(ageTfProfile);

        //add to bottom panel
        bottomP.add(okBtnProfile);
        bottomP.add(cancelBtnProfile);

        //add to frame
        profileFrame.add(box);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == okBtnProfile) {
                    //TODO change profile request
                }
                if (e.getSource() == cancelBtnProfile) {
                    settingWindow();
                    profileFrame.dispose();
                }
            }
        };

        okBtnProfile.addActionListener(actionListener);
        cancelBtnProfile.addActionListener(actionListener);
    }

    private JTextArea displayChat = new JTextArea(15, 40);
    private TextField inputTfChat = new TextField(30);
    private Button sendBtnChat = new Button("SEND");
    private Button deleteBtnChat = new Button("Delete the group");
    private Button renameBtnChat = new Button("Rename the group");
    private String groupNameChat;
    private Button addUserToChatBtn = new Button("Invite");
    private Button exportBtnChat = new Button("Export");

    public void chatWindow(Conversation currentConversation) {
        //set frame
        JFrame chatFrame = new JFrame("Chat");
        chatFrame.setSize(400, 400);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        chatFrame.setVisible(true);

        //set display window
        displayChat.setLineWrap(true);
        displayChat.setEditable(false);
        JScrollPane jsp = new JScrollPane(displayChat);
        jsp.setBounds(displayChat.getX(), displayChat.getY(),
                displayChat.getWidth(), displayChat.getHeight());

        //set panels
        Box box = Box.createVerticalBox();
        Panel topP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel midP = new Panel();
        Panel bottomP = new Panel();
        box.add(topP);
        box.add(midP);
        box.add(bottomP);

        //add to panels
        topP.add(addUserToChatBtn);
        topP.add(renameBtnChat);
        topP.add(deleteBtnChat);
        topP.add(exportBtnChat);
        midP.add(jsp);
        bottomP.add(inputTfChat);
        bottomP.add(sendBtnChat);

        //add to frame
        chatFrame.add(box);
        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                clientWorker.saveMessageToMap();
            }
        };

        ActionListener actionListener = e -> {
            if (e.getSource() == addUserToChatBtn) {
                String invite_uuid_String = JOptionPane.showInputDialog(chatFrame, "Enter the uuid:",
                        "Invite", JOptionPane.INFORMATION_MESSAGE);
                UUID invited_uuid = UUID.fromString(invite_uuid_String);
                clientWorker.addUserToGroup(invited_uuid, clientWorker.getCurrentConversation_uuid());
            }
            if (e.getSource() == deleteBtnChat) {
                int answer = JOptionPane.showConfirmDialog(chatFrame, "Are you sure to delete" +
                        "the conversation?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    if (clientWorker.deleteConversation(clientWorker.getCurrentConversation_uuid())) {
                        chatFrame.dispose();
                    }
                }
            }
            if (e.getSource() == renameBtnChat) {
                groupNameChat = JOptionPane.showInputDialog(chatFrame, "Enter new group name:",
                        "Rename", JOptionPane.PLAIN_MESSAGE);
                if (clientWorker.renameConversation(groupNameChat)) {
                    chatFrame.setTitle(groupNameChat);
                }
            }
            if (e.getSource() == sendBtnChat) {
                Message message = new Message(clientWorker.getMy_uuid(),
                        new Date(), inputTfChat.getText());
                if (clientWorker.sendMessage(currentConversation, message)) {
                    displayChat.append(clientWorker.messageString(message));
                    inputTfChat.setText(null);
                }

            } if (e.getSource() == exportBtnChat) {
                clientWorker.export();
            }

        };
        deleteBtnChat.addActionListener(actionListener);
        renameBtnChat.addActionListener(actionListener);
        sendBtnChat.addActionListener(actionListener);
    }


    public String getSignInUsername() {
        return usernameTfSign.getText();
    }

    public String getSignInPassword() {
        return Arrays.toString(passwordTfSign.getPassword());
    }

    public String getRgUsername() {
        return rgUserTf.getText();
    }

    public String getRgPassword() {
        return Arrays.toString(rgPassTf.getPassword());
    }

    public String getRgName() {
        return rgNameTf.getText();
    }

    public String getRgAgeString() {
        return rgAgeTf.getText();
    }

}

class ClientWorker {
    private Window window;
    private static Socket socket;
    private User user;
    private Credential credential;
    private Profile profile;
    private UUID my_uuid;
    private UUID currentConversation_uuid;
    private Conversation currentConversation;
    private ArrayList<Message> currentMessageList = new ArrayList<>();
    private ArrayList<UUID> conversation_uuid_list = new ArrayList<>();
    private ArrayList<Conversation> conversation_list = new ArrayList<>();
    private HashMap<UUID, Message[]> messageMap = new HashMap<>();


    public ClientWorker() {

    }

    public ClientWorker(Window window) {
        this.window = window;
    }

    public UUID getCurrentConversation_uuid() {
        return currentConversation_uuid;
    }

    public ArrayList<UUID> getConversation_uuid_list() {
        return conversation_uuid_list;
    }

    public Profile getMyProfile() {
        return profile;
    }

    public UUID getMy_uuid() {
        return my_uuid;
    }

    /**
     * @param conversation_uuid the uuid of the conversation
     * @return return the name of the conversation
     */
    public String getGroupName(UUID conversation_uuid) {
        for (int i = 0; i < conversation_list.size(); i++) {
            if (conversation_list.get(i).uuid.equals(conversation_uuid)) {
                return conversation_list.get(i).name;
            }
        }
        return "No conversation";
    }

//    public ArrayList<String> getConversationString() {
//        ArrayList<String> conversationString = new ArrayList<>();
//        if (conversation_list != null) {
//            for (int i = 0; i < conversation_list.size(); i++) {
//                conversationString.add(conversation_list.get(i).name);
//            }
//            return conversationString;
//        } else {
//            conversationString.add("You haven't have any conversation yet");
//            return conversationString;
//        }
//    }

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
            if (response.exception != null) {
                throw response.exception;
            } else {
                throw new RequestFailedException();
            }
        }
    }


    /**
     * The method sends an authenticate request and receives
     * a response
     */
    public boolean signIn() {
        String username = window.getSignInUsername();
        String password = window.getSignInPassword();

        try {
            credential = new Credential(username, password);
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
            Response response = send(authenticateRequest, socket);
            my_uuid = response.uuid;
            //if success get the user's information
            getProfile();
            getAllConversationUid();
            getAllConversation();
            getExistMessageHistory();
            return true;
        } catch (UserNotFoundException | InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestParsingException e) {
            e.printStackTrace();

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * The method sends a register request to the server and receives a
     * registerResponse that contains the user's uuid.
     */
    public UUID register() {
        RegisterResponse response;
        String username = window.getRgUsername();
        String password = window.getRgPassword();
        String name = window.getRgName();
        try {
            Credential rgCredential = new Credential(username, password);

            Profile rgProfile = new Profile(name, Integer.parseInt(window.getRgAgeString()));

            RegisterRequest registerRequest = new RegisterRequest(rgCredential, rgProfile);

            response = (RegisterResponse) send(registerRequest, socket);
            //UUID my_uuid = response.uuid;
            JOptionPane.showMessageDialog(null, "Registered successfully",
                    "Register", JOptionPane.INFORMATION_MESSAGE);
            return response.uuid;

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

    /**
     * send a logout request
     */
    public void logOut() {
        LogOutRequest logOutRequest = new LogOutRequest();
        try {
            send(logOutRequest, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    /**
     * send a getUserRequest
     */
    public void getProfile() {
        Response response;
        GetUserRequest getUserRequest = new GetUserRequest(my_uuid);
        try {
            response = send(getUserRequest, socket);
            profile = ((GetUserResponse) response).user.profile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    /**
     * send a delete account request
     */
    public boolean deleteAccount() {
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(credential);
        try {
            send(deleteAccountRequest, socket);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * get all the conversation uid of a user
     */
    public void getAllConversationUid() {
        ListAllConversationsRequest listAllConversationsRequest = new ListAllConversationsRequest();
        ListAllConversationsResponse response;
        try {
            response = (ListAllConversationsResponse) send(listAllConversationsRequest, socket);
            if (response.conversation_uuids != null) {
                conversation_uuid_list.addAll(Arrays.asList(response.conversation_uuids));
            }
        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }

    /**
     * get all the conversation
     */
    public void getAllConversation() {
        if (conversation_uuid_list != null) {
            for (int i = 0; i < conversation_uuid_list.size(); i++) {
                GetConversationRequest getConversation = new GetConversationRequest(conversation_uuid_list.get(i));
                GetConversationResponse response;
                try {
                    response = (GetConversationResponse) send(getConversation, socket);
                    conversation_list.add(response.conversation);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RequestFailedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * get previously existed conversation history and
     * save them in the map
     */
    public void getExistMessageHistory() {
        if (conversation_uuid_list != null) {
            for (int i = 0; i < conversation_uuid_list.size(); i++) {
                GetMessageHistoryRequest getMessage = new GetMessageHistoryRequest(conversation_uuid_list.get(i));
                GetMessageHistoryResponse response;
                try {
                    response = (GetMessageHistoryResponse) send(getMessage, socket);
                    messageMap.put(conversation_list.get(i).uuid, response.messages);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (RequestFailedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * Delete a conversation
     * Sends a request abd delete both from
     * the UI and the data stored in client
     *
     * @param conversation_uuid the uuid of the conversation
     * @return return true if success
     */
    public boolean deleteConversation(UUID conversation_uuid) {
        DeleteConversationRequest dr = new DeleteConversationRequest(conversation_uuid);
        try {
            send(dr, socket);
            JOptionPane.showMessageDialog(null, "Successfully deleted",
                    "Delete Conversation", JOptionPane.INFORMATION_MESSAGE);
            conversation_uuid_list.remove(conversation_uuid);
            messageMap.remove(conversation_uuid);
            return true;

        } catch (NotLoggedInException | ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Sends an add user request and receives a response
     *
     * @param user_uuid         the user's uuid that you want to add
     * @param conversation_uuid the uuid of the conversation
     */
    public void addUserToGroup(UUID user_uuid, UUID conversation_uuid) {
        AddUser2ConversationRequest addRequest = new AddUser2ConversationRequest(user_uuid, conversation_uuid);
        try {
            send(addRequest, socket);
        } catch (NotLoggedInException | UserNotFoundException | ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    /**
     * Search for a user by the uuid
     * @param user_uuid the uuid of the user
     * @return return the user
     */
    public User searchUser(UUID user_uuid) {
        GetUserRequest getUser = new GetUserRequest(user_uuid);
        GetUserResponse response;
        try {
            response = (GetUserResponse) send(getUser, socket);
            return response.user;
        } catch (NotLoggedInException | UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Create a conversation
     * Sends a request and add the conversation both to
     * the UI and the data stored in client
     *
     * @param groupName the name of the conversation
     * @return the uuid of the group
     */
    public Conversation createConversation(String groupName, String inviteString) {
        String[] uuidsString;
        UUID[] uuids;
        try {
        uuidsString = inviteString.split(",");
        } catch (NumberFormatException | NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Please separate the uuids by commas!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
        uuids = new UUID[uuidsString.length];
        for (int i = 0; i <inviteString.length() ; i++) {
            UUID uuid = UUID.fromString(uuidsString[i]);
            uuids[i] = uuid;
        }
        CreateConversationRequest createRequest = new CreateConversationRequest(uuids, groupName);
        CreateConversationResponse response;

        try {
            response = (CreateConversationResponse) send(createRequest, socket);
            conversation_uuid_list.add(response.conversation_uuid);
            currentConversation_uuid = response.conversation_uuid;
            return getConversation(currentConversation_uuid);
        } catch (NotLoggedInException | InvalidConversationNameException | UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Conversation getConversation(UUID uuid) {
        GetConversationRequest getConversationRequest = new GetConversationRequest(uuid);
        GetConversationResponse response;
        try {
            response = (GetConversationResponse) send(getConversationRequest, socket);
            return response.conversation;
        } catch (ConversationNotFoundException | NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * The method sends a rename request and receives a response
     *
     * @param name the name of the conversation
     * @return return true if success
     */
    public boolean renameConversation(String name) {
        RenameConversationRequest renameRequest = new RenameConversationRequest(currentConversation_uuid, name);
        try {
            send(renameRequest, socket);
            return true;
        } catch (ConversationNotFoundException | NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The method sends a postMessageRequest and receives a response. If
     * the message was sent, save the message to currentMessageList
     *
     * @param conversation_uuid uuid of the conversation
     * @param message           the message that was send
     * @return return true if message was successfully send, false
     * if the message was not send
     */
    public boolean sendMessage(Conversation conversation_uuid, Message message) {
        PostMessageRequest postMessageRequest = new PostMessageRequest(conversation_uuid, message);
        PostMessageResponse response;
        try {
            send(postMessageRequest, socket);
            saveMessage(message);
            return true;
        } catch (NotLoggedInException | MessageNotFoundException | IllegalContentException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * After the user entered an previously existed conversation,
     * import the chat history for this conversation to currentMessageList
     */
    public void importCurrentHistory() {
        Message[] thisConversationHistory = messageMap.get(currentConversation_uuid);
        if (thisConversationHistory != null) {
            currentMessageList.addAll(Arrays.asList(thisConversationHistory));
        }
    }


    /**
     * If the user entered a previously existed conversation, use importCurrentHistory
     * before using saveMessage method.
     *
     * @param message when send button is clicked, add the message to currentMessageList
     */
    public void saveMessage(Message message) {
        currentMessageList.add(message);
    }


    /**
     * save currentMessageList to map
     */
    public void saveMessageToMap() {
        Message[] messageList = new Message[currentMessageList.size()];
        for (int i = 0; i < currentMessageList.size(); i++) {
            messageList[i] = currentMessageList.get(i);
        }
        if (messageMap.containsKey(currentConversation_uuid)) {
            messageMap.replace(currentConversation_uuid, messageList);
        } else {
            messageMap.put(currentConversation_uuid, messageList);
        }
    }

    /**
     * When the user exit current conversation, save the
     * messages, set currentConversation_uuid to null, and
     * clear the currentMessageList
     */
    public void setToNewConversation() {
        saveMessageToMap();
        currentConversation_uuid = null;
        currentMessageList.clear();
    }

    /**
     * Export the current conversation history
     */
    public void export() {
        try (PrintWriter pw = new PrintWriter("Conversation_History")) {
            pw.write("Message Sender,");
            pw.write("Timestamp,");
            pw.write("contents\n");
            if (currentMessageList != null) {
                for (int i = 0; i < currentMessageList.size(); i++) {
                    String sender = searchUser(currentMessageList.get(i).sender_uuid).profile.name;
                    pw.write(String.format("%s,", sender));
                    String time = currentMessageList.get(i).time.toString();
                    pw.write(String.format("%s,", time));
                    pw.write(String.format("%s,", currentMessageList.get(i).content));
                }
            }
            JOptionPane.showMessageDialog(null, "Export successfully!",
                    "export", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Export failed!",
                    "export", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * The message will be sent in the following format:
     * {Name at Time: <the content of the message>}
     * @param message the message
     * @return the message string
     */
    public String messageString(Message message) {
        String name = searchUser(message.sender_uuid).profile.name;
        String date = message.time.toString();
        String content = message.content;
        return String.format("{%s at %s: <%s>}\n", name, date, content);
    }

}


