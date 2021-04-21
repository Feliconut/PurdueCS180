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
    private TextField passwordTfSign;
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
        passwordTfSign = new TextField(20);

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
        private final TextField rgPassTf = new TextField(20);
        private final Button rgOkBtn = new Button("OK");
        private final Button rgCancelBtn = new Button("Cancel");


        public void registerWindow() {
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
        private JLabel newChatLbM = new JLabel("Start a new chat");
        private JLabel invitePromptLbM = new JLabel("Invite people to chat:");
        private JLabel invitedLbM = new JLabel("No one has been invited yet!"); //need to be updated
        private TextField searchTfM = new TextField(20);
        private Button addBtnM = new Button("Add");
        private Button startBtnM = new Button("Start Chatting!");
        //ClientWorker clientWorker = new ClientWorker(this);

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

            //add to chatroomP
            Box vBoxInLeft = Box.createVerticalBox();
            chatroomP.add(vBoxInLeft);
            Panel chatroomLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
            chatroomLbM.setBorder(new LineBorder(Color.gray));
            chatroomLbP.add(chatroomLbM);
            vBoxInLeft.add(chatroomLbP);
            Panel conversationP = new Panel();
            vBoxInLeft.add(conversationP);

            //add to newChatP
            Box vBoxInRight = Box.createVerticalBox();
            newChatP.add(vBoxInRight);
            Panel newChatLbP = new Panel(new FlowLayout(FlowLayout.CENTER));
            newChatLbP.add(newChatLbM);
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
            labelP.add(invitePromptLbM);
            updateP.add(invitedLbM);
            addP.add(searchTfM);
            addP.add(addBtnM);
            startP.add(startBtnM);

            //add panels to frame
            mainFrame.add(vBoxOut);

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
                if (e.getSource() == addBtnM) {
                    //TODO add people to chat request
                    //TODO update the invitedLb if the person is successfully added
                    JOptionPane.showMessageDialog(mainFrame, "Successfully added!",
                            "Invitation", JOptionPane.INFORMATION_MESSAGE);
                    //TODO if the person does not exist throw exception
                }
                if (e.getSource() == startBtnM) {
                    chatWindow();
                }
            };

            logOutBtnM.addActionListener(actionListener);
            settingBtnM.addActionListener(actionListener);
            addBtnM.addActionListener(actionListener);
            startBtnM.addActionListener(actionListener);

        }

    private Button backBtnSetting = new Button("Back");
    private Button deleteBtnSetting = new Button("DELETE ACCOUNT");
    private Button manageProfileBtnSetting = new Button("MANAGE PROFILE");
    private Button exportBtnSetting = new Button("EXPORT CHAT HISTORY");
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
        midP.add(exportBtnSetting);

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
                    clientWorker.deleteAccount();
                    settingFrame.dispose();
                    JOptionPane.showMessageDialog(null,
                            "Successfully deleted!", "Delete Account",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (e.getSource() == exportBtnSetting) {
                //TODO export request
            }

            if (e.getSource() == profileBtnSetting) {
                Profile profile = clientWorker.getProfile();
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
        exportBtnSetting.addActionListener(actionListener);
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

        public void chatWindow() {
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
            topP.add(renameBtnChat);
            topP.add(deleteBtnChat);
            midP.add(jsp);
            bottomP.add(inputTfChat);
            bottomP.add(sendBtnChat);

            //add to frame
            chatFrame.add(box);

            ActionListener actionListener = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == deleteBtnChat) {
                        //TODO send verification request
                        int answer = JOptionPane.showConfirmDialog(chatFrame, "Are you sure to delete" +
                                "the conversation?", "Delete", JOptionPane.OK_CANCEL_OPTION);
                        if (answer == JOptionPane.OK_OPTION) {
                            //TODO send delete request
                        }
                    }
                    if (e.getSource() == renameBtnChat) {
                        groupNameChat = JOptionPane.showInputDialog(chatFrame, "Enter new group name:",
                                "Rename", JOptionPane.PLAIN_MESSAGE);
                        chatFrame.setTitle(groupNameChat);
                    }
                    if (e.getSource() == sendBtnChat) {
                        //TODO send message request
                    }
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
        return passwordTfSign.getText();
    }

    public String getRgUsername() {
        return rgUserTf.getText();
    }

    public String getRgPassword() {
        return rgPassTf.getText();
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
    private UUID uuid;

    public ClientWorker() {

    }

    public ClientWorker(Window window) {
        this.window = window;
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
     *
     * @return true if the user passed the authentication, false
     * if the user is not registered or entered wrong password
     */
    public boolean signIn() {
        String username = window.getSignInUsername();
        String password = window.getSignInPassword();

        try {
            credential = new Credential(username, password);
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
            Response response = send(authenticateRequest, socket);
            uuid = response.uuid;
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
     *
     * @return return UUID if the success, else return null.
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
     * @return send a getUserRequest and return the user's profile
     * return null if exceptions occur
     */
    public Profile getProfile() {
        Response response;
        GetUserRequest getUserRequest = new GetUserRequest(uuid);
        try {
            response = send(getUserRequest,socket);
            profile =((GetUserResponse)response).user.profile;
            return profile;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * send a delete account request
     */
    public void deleteAccount() {
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(credential);
        try {
            send(deleteAccountRequest, socket);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }
}


