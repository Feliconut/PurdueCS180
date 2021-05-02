import Exceptions.*;
import Field.*;
import Request.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

/**
 * PJ5-MessageClient
 * This class enables connections between the client and
 * the server. It also displays the log-in user interface.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

public class MessageClient {
    private static final BufferedReader bfr = null;
    private static final PrintWriter pw = null;

    public static void main(String[] args) {
        new Window();
    }

}

class ListDisplay<T extends Storable> {

    final DefaultListModel<String> displayListModel;
    private final JList<String> displayList;
    private final ArrayList<UUID> uuids;
    private final JScrollPane jsp;

    public ListDisplay() {
        this.displayListModel = new DefaultListModel<>();
        this.displayList = new JList<>(displayListModel);
        this.uuids = new ArrayList<>();
        displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jsp = new JScrollPane(displayList);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    }

    public JScrollPane getJsp() {
        return jsp;
    }

    public void addListener(java.awt.event.MouseListener l) {
        displayList.addMouseListener(l);
    }

    public void setBounds(int x, int y, int width, int height) {
        jsp.setBounds(x, y, width, height);
    }

    public UUID getSelectedUUID() {

        return uuids.get(displayList.getSelectedIndex());

    }

    public UUID removeSelected() {
        int index = displayList.getSelectedIndex();
        UUID uuid = uuids.get(index);

        displayList.remove(index);
        uuids.remove(index);

        return uuid;
    }

    public void add(T obj) {
        add(obj, null);
    }


    public void add(T obj, String display) {
        if (display == null) {
            display = makeDisplay(obj);
        }
        try {
            remove(obj.uuid);
        } catch (IndexOutOfBoundsException ignored) {
        }
        displayListModel.addElement(display);
        uuids.add(obj.uuid);
    }

    private String makeDisplay(T obj) {
        String display;
        if (obj instanceof Conversation) {
            Conversation conversation = (Conversation) obj;
            display = String.format("%s (%d people)", conversation.name, conversation.user_uuids.length);
        } else if (obj instanceof Message) {
            Message message = (Message) obj;
            display = String.format("%s (sent by <unknown> at %s)", message.content, message.time.toString());
        } else if (obj instanceof User) {
            User user = (User) obj;
            display = String.format("%s (%s, %d years old)", user.credential.usrName, user.profile.name, user.profile.age);
        } else {
            display = obj.toString();
        }
        return display;

    }

    public void update(T obj, String display) {
        int index = uuids.indexOf(obj.uuid);
        if (index == -1) {
            add(obj, display); // add the object if it does not exist
        } else {
            update(obj, display, index);
        }
    }

    public void update(T obj) {
        update(obj, null);
    }

    private void update(T obj, String display, int index) {
        if (display != null) {
            displayListModel.set(index, display);
        } else {
            displayListModel.set(index, makeDisplay(obj));
        }
    }

    public void remove(UUID uuid) {
        int index = uuids.indexOf(uuid);
        uuids.remove(index);
        displayListModel.remove(index);
    }

}

class Window {
    private final ClientWorker clientWorker = new ClientWorker(this);

    public Window() {
        final Button registerButtonSign;
        final JLabel titleLbSign;
        final JLabel usernameLbSign;
        final JLabel passwordLbSign;
        final Button okButtonSign;
        final TextField usernameTfSign;
        final JPasswordField passwordTfSign;
        //set frame
        JFrame frame = new JFrame("sign-in");
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //set panels
        Box vertical = Box.createVerticalBox();
        Panel topPanel = new Panel(new FlowLayout(FlowLayout.RIGHT));
        Panel titlePanel = new Panel(new FlowLayout(FlowLayout.CENTER));
        Panel userPanel = new Panel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        //userPanel.setBounds(100,100,100,10);
        Panel passwordPanel = new Panel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        //passwordPanel.setBounds(100,140,100,10);
        Panel okPanel = new Panel();


        //set buttons and labels
        registerButtonSign = new Button("Register");
        okButtonSign = new Button("OK");
        titleLbSign = new JLabel("<html><center><font size='20'>PJ5 Messaging</font></center></html>");
        usernameLbSign = new JLabel("Username ");
        passwordLbSign = new JLabel("Password  ");
        usernameTfSign = new TextField(20);
        //usernameLbSign.setBounds(75,200, 450, 10);
        passwordTfSign = new JPasswordField(20);
        //usernameLbSign.setBounds(75,230, 450, 10);

        //passwordTfSign.setSize(usernameTfSign.getSize());
        //passwordTfSign.setEchoChar('*');

        //add to top panel
        topPanel.add(registerButtonSign);

        //add to title panel
        titlePanel.add(titleLbSign);

        //add to username panel
        userPanel.add(Box.createHorizontalStrut(100));
        userPanel.add(usernameLbSign);
        userPanel.add(usernameTfSign);
        userPanel.add(Box.createHorizontalStrut(100));

        //add to password panel
        passwordPanel.add(Box.createHorizontalStrut(100));
        passwordPanel.add(passwordLbSign);
        passwordPanel.add(passwordTfSign);
        passwordPanel.add(Box.createHorizontalStrut(100));

        //add to ok panel
        okPanel.add(okButtonSign);

        // add to frame
        vertical.add(topPanel);
        vertical.add(Box.createVerticalStrut(50));
        vertical.add(titlePanel);
        vertical.add(userPanel);
        vertical.add(Box.createVerticalStrut(10));
        vertical.add(passwordPanel);
        vertical.add(okPanel);
        vertical.add(Box.createVerticalStrut(100));
        frame.add(vertical);

        frame.setVisible(true);
        frame.pack();


        // clientWorker = new ClientWorker(this);
        //actionListener
        ActionListener actionListener = e -> {
            if (e.getSource() == registerButtonSign) {
                registerWindow();
                // frame.dispose();
            }
            if (e.getSource() == okButtonSign) {
                String username = usernameTfSign.getText();
                String password = Arrays.toString(passwordTfSign.getPassword());
                UUID my_uuid = clientWorker.signIn(username, password);
                if (my_uuid != null) {
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

    public void registerWindow() {
        final JLabel rgNameLb = new JLabel("Name  ");
        final JLabel rgAgeLb = new JLabel("Age  ");
        final JLabel rgUserLb = new JLabel("Username");
        final JLabel rgPassLb = new JLabel("Password ");
        final TextField rgNameTf = new TextField(28);
        final TextField rgAgeTf = new TextField(28);
        final TextField rgUserTf = new TextField(28);
        final JPasswordField rgPassTf = new JPasswordField(20);
        final Button rgOkBtn = new Button("OK");
        final Button rgCancelBtn = new Button("Cancel");

        //set frame
        JFrame registerFrame = new JFrame("Register");
        registerFrame.setSize(600, 400);
        registerFrame.setLocationRelativeTo(null);
        registerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //set panel
        Panel panel = new Panel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        //Box box = Box.createVerticalBox();
        Panel nameP = new Panel();
        nameP.setLayout(new BoxLayout(nameP, BoxLayout.X_AXIS));
        Panel ageP = new Panel();
        ageP.setLayout(new BoxLayout(ageP, BoxLayout.X_AXIS));
        Panel userP = new Panel();
        userP.setLayout(new BoxLayout(userP, BoxLayout.X_AXIS));
        Panel passP = new Panel();
        passP.setLayout(new BoxLayout(passP, BoxLayout.X_AXIS));
        Panel bottomP = new Panel();
        panel.add(Box.createVerticalStrut(70));
        panel.add(nameP);
        panel.add(Box.createVerticalStrut(10));
        panel.add(ageP);
        panel.add(Box.createVerticalStrut(10));
        panel.add(userP);
        panel.add(Box.createVerticalStrut(10));
        panel.add(passP);
        panel.add(Box.createVerticalStrut(10));
        panel.add(bottomP);
        panel.add(Box.createVerticalStrut(70));

        //add to nameP
        nameP.add(Box.createHorizontalStrut(50));
        nameP.add(rgNameLb);
        nameP.add(Box.createHorizontalStrut(20));
        nameP.add(rgNameTf);
        nameP.add(Box.createHorizontalStrut(50));

        //add to ageP
        ageP.add(Box.createHorizontalStrut(50));
        ageP.add(rgAgeLb);
        ageP.add(Box.createHorizontalStrut(30));
        ageP.add(rgAgeTf);
        ageP.add(Box.createHorizontalStrut(50));

        //add to userP
        userP.add(Box.createHorizontalStrut(50));
        userP.add(rgUserLb);
        userP.add(rgUserTf);
        userP.add(Box.createHorizontalStrut(50));

        //add to passP
        passP.add(Box.createHorizontalStrut(50));
        passP.add(rgPassLb);
        passP.add(rgPassTf);
        passP.add(Box.createHorizontalStrut(50));

        //add to bottomP
        bottomP.add(rgOkBtn);
        bottomP.add(rgCancelBtn);

        //add to frame
        registerFrame.add(panel);

        registerFrame.setVisible(true);
        registerFrame.pack();

        //ClientWorker clientWorker = new ClientWorker(this);
        ActionListener actionListener = e -> {
            if (e.getSource() == rgCancelBtn) {
                registerFrame.dispose();
            }
            if (e.getSource() == rgOkBtn) {
                String name = rgNameTf.getText();
                String ageString = rgAgeTf.getText();
                //System.out.println(age);
                String username = rgUserTf.getText();
                String password = Arrays.toString(rgPassTf.getPassword());
                boolean register = clientWorker.register(username, password, name, ageString);

                if (register) {
                    registerFrame.dispose();
                }

                rgAgeTf.setText(null);
                rgNameTf.setText(null);
                rgUserTf.setText(null);
                rgPassTf.setText(null);

            }
        };

        rgCancelBtn.addActionListener(actionListener);
        rgOkBtn.addActionListener(actionListener);

    }


    public void mainWindow() {
        final ListDisplay<Conversation> conversationListDisplay = new ListDisplay<>();
        JTextArea showUsernameTa = new JTextArea("Invited: ");
        showUsernameTa.setLineWrap(true);
        //private StringBuilder usernameString = new StringBuilder();
        ArrayList<UUID> invitedUsers = new ArrayList<>();

        final Button logOutBtnM = new Button("LOG OUT");
        final Button settingBtnM = new Button("SETTING");
        final JLabel my_uuid = new JLabel();
        final JLabel chatroomLbM = new JLabel("Chat Rooms");
        chatroomLbM.setFont(new Font("Serif", Font.BOLD, 15));
        final JLabel newChatLbM = new JLabel("New Chat");
        newChatLbM.setFont(new Font("Serif", Font.BOLD, 15));
        final JLabel groupNameLbM = new JLabel("Enter a group name");
        final TextField groupNameTfM = new TextField(10);
        final JLabel inviteLbM = new JLabel("Invite by usernames:");
        final JTextField inviteTfM = new JTextField(20);
        Button addBtnM = new Button("ADD");
        final Button startBtnM = new Button("CREATE");
        final JLabel groupInfoLb = new JLabel();
        final JLabel groupMemberLb = new JLabel();
        // UUID name
        // display: <index> name (X users)
        // index --> UUID
        my_uuid.setText(String.format("my uuid: %s", clientWorker.current_user.uuid));

        //set up frame
        JFrame mainFrame = new JFrame("Main");
        Panel bigPanel = new Panel();
        bigPanel.setLayout(new BoxLayout(bigPanel, BoxLayout.Y_AXIS));
        mainFrame.setSize(600, 400);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);

        //Box vBoxOut = Box.createVerticalBox();

        //set up top panel
        Panel topP = new Panel();
        topP.setLayout(new FlowLayout(FlowLayout.LEFT));
        topP.add(logOutBtnM);
        topP.add(settingBtnM);
        topP.add(my_uuid);
        bigPanel.add(topP);

        //set up middle panel
        Panel midPanel = new Panel();
        midPanel.setLayout(new BoxLayout(midPanel, BoxLayout.X_AXIS));
        //Box hBox = Box.createHorizontalBox();
        Panel chatroomP = new Panel();
        Panel newChatP = new Panel();
        midPanel.add(chatroomP);
        midPanel.add(Box.createHorizontalStrut(20));
        midPanel.add(newChatP);
//        hBox.add(chatroomP);
//        hBox.add(newChatP);
        bigPanel.add(Box.createVerticalStrut(50));
        bigPanel.add(midPanel);
        bigPanel.add(Box.createVerticalStrut(50));

        //set up the conversation list
//        my_conversation_uuids = clientWorker.getConversation_uuid_list();
//        for (UUID my_conversation_uuid : my_conversation_uuids) {
//            conversationListModel.addElement(my_conversation_uuid);
//        }
        for (Conversation conversation :
                clientWorker.getAllConversation()) {
            conversationListDisplay.add(conversation);
        }
        //        conversationList = new JList<>(conversationListModel);
//        conversationList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        //add to chatroomP
        // Box vBoxInLeft = Box.createVerticalBox();
        chatroomP.setLayout(new BoxLayout(chatroomP, BoxLayout.Y_AXIS));
        //chatroomP.add(vBoxInLeft);
        Panel chatroomLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        chatroomP.add(chatroomLbP);
        chatroomLbP.add(chatroomLbM);

        chatroomP.add(Box.createVerticalStrut(5));
        conversationListDisplay.setBounds(5, 20, chatroomP.getWidth() - 10, chatroomP.getHeight() - 50);
        chatroomP.add(conversationListDisplay.getJsp());

        Panel groupInfoP = new Panel();
        groupInfoP.add(groupInfoLb);

        Panel groupMemberP = new Panel();
        groupMemberP.add(groupMemberLb);

        chatroomP.add(groupInfoP);
        chatroomP.add(groupMemberP);

        //set up new chat panel
        //Box vBoxInRight = Box.createVerticalBox();
//        newChatP.add(vBoxInRight);
        newChatP.setLayout(new BoxLayout(newChatP, BoxLayout.Y_AXIS));
        Panel newChatLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel labelP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addP = new Panel();
        //Panel showP = new Panel();
        Panel groupNameP = new Panel();
        Panel startP = new Panel();

        //add panels to box
        newChatP.add(newChatLbP);
        newChatP.add(Box.createVerticalStrut(5));
        newChatP.add(labelP);
        newChatP.add(groupNameP);
        //show username panel
        showUsernameTa.setEditable(false);
        showUsernameTa.setColumns(10);
        JScrollPane showJsp = new JScrollPane(showUsernameTa);

        newChatP.add(showJsp);

        newChatP.add(addLbP);

        newChatP.add(addP);
        newChatP.add(startP);

        newChatLbP.add(newChatLbM);
        labelP.add(groupNameLbM);
        labelP.add(groupNameTfM);
        addLbP.add(inviteLbM);
        addLbP.add(inviteTfM);
        addLbP.add(addBtnM);
        startP.add(startBtnM);

        //add panels to frame
        //bigPanel.add(vBoxOut);
        mainFrame.add(bigPanel);
        mainFrame.setVisible(true);
        mainFrame.pack();
        //if the list is clicked twice open up the selected conversation
        //if the list is clicked once show the conversation name in the label below
        //if the list is right-clicked pop up delete message
        conversationListDisplay.addListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Conversation conversation = clientWorker.getConversation(conversationListDisplay.getSelectedUUID());
                    //clientWorker.setToNewConversation(list.getSelectedValue());
                    chatWindow(conversation);
                    mainFrame.dispose();
                }

                if (e.getClickCount() == 1) {
                    //display group name
                    String groupName = clientWorker.getConversation(conversationListDisplay.getSelectedUUID()).name;
                    groupInfoLb.setText(String.format("Group name: %s", groupName));

                    //display group members
                    UUID[] members = clientWorker.getConversation(conversationListDisplay.getSelectedUUID()).user_uuids;
                    StringBuilder memberString = new StringBuilder();
                    memberString.append("Group members: ");

                    for (int i = 0; i < members.length; i++) {
                        memberString.append(clientWorker.getUser(members[i]).credential.usrName);
                        memberString.append(" ");
                    }

                    groupMemberLb.setText(String.valueOf(memberString));

                }

                if (e.getButton() == MouseEvent.BUTTON3) {
                    int answer = JOptionPane.showConfirmDialog(null,
                            "Delete selected conversation from your list?",
                            "Delete", JOptionPane.YES_NO_OPTION);

                    if (answer == JOptionPane.YES_OPTION) {
                        if (clientWorker.deleteConversation(conversationListDisplay.getSelectedUUID())) {
                            groupInfoLb.setText(null);
                        }
                    }
                }
            }
        });

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                //TODO update the model list that displays the conversation list
                GetEventFeedResponse response = clientWorker.getEventFeed();
                for (Conversation conversation :
                        response.new_conversations) {
                    conversationListDisplay.update(conversation);
                }
                for (Conversation conversation :
                        response.updated_conversations) {
                    conversationListDisplay.update(conversation);
                }
                for (UUID uuid :
                        response.removed_conversations) {
                    conversationListDisplay.remove(uuid);
                }

            }
        };
        mainFrame.addWindowListener(windowListener);

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
                    // return to login window
                    new Window();
                }
            }
            if (e.getSource() == startBtnM) {
                //get the group name and usernames
                String groupName = groupNameTfM.getText();
                // String inviteString = inviteTfM.getText();
                //create a new conversation
                UUID[] user_uuids = new UUID[invitedUsers.size()];
                for (int i = 0; i < invitedUsers.size(); i++) {
                    user_uuids[i] = invitedUsers.get(i);
                }
                Conversation conversation = clientWorker.createConversation(groupName, user_uuids);
                conversationListDisplay.add(conversation);

                chatWindow(conversation);
                groupNameTfM.setText(null);
                inviteTfM.setText(null);
                showUsernameTa.setText("Invited: ");

                invitedUsers.clear();
                mainFrame.dispose();

            }
            if (e.getSource() == addBtnM) {
                String username = inviteTfM.getText();
                User user = clientWorker.getUser(username);
                if (user != null) {
                    UUID user_uuid = user.uuid;
                    showUsernameTa.append(String.format("%s ", username));
//                    usernameString.append();
//                    showUsernameTa.setText(String.valueOf(usernameString));
                    invitedUsers.add(user_uuid);
                    inviteTfM.setText(null);
                } else {
                    inviteTfM.setText(null);
                }
            }
        };

        logOutBtnM.addActionListener(actionListener);
        settingBtnM.addActionListener(actionListener);
        startBtnM.addActionListener(actionListener);
        addBtnM.addActionListener(actionListener);

    }

    public void settingWindow() {
        final Button backBtnSetting = new Button("Back");
        final Button deleteBtnSetting = new Button("DELETE ACCOUNT");
        final Button manageProfileBtnSetting = new Button("MANAGE PROFILE");
        //private Button exportBtnSetting = new Button("EXPORT CHAT HISTORY");
        final Button profileBtnSetting = new Button("MY PROFILE");
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

            if (e.getSource() == profileBtnSetting) {
                Profile profile = clientWorker.current_user.profile;

                String name = profile.name;
                int age = profile.age;

                String message = String.format("""
                        Name: %s
                        Age: %d
                        """, name, age);

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

    public void manageProfileWindow() {
        final JLabel nameLbProfile = new JLabel("Name");
        final JLabel ageLbProfile = new JLabel("age");
        final TextField nameTfProfile = new TextField(20);
        final TextField ageTfProfile = new TextField(20);
        final Button okBtnProfile = new Button("OK");
        final Button cancelBtnProfile = new Button("Cancel");

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
                    String name = nameTfProfile.getText();
                    String age = ageTfProfile.getText();
                    if (clientWorker.setProfile(name, age) != null) {
                        JOptionPane.showMessageDialog(profileFrame,
                                "Profile changed successfully!",
                                "Manage Profile", JOptionPane.INFORMATION_MESSAGE);
                        profileFrame.dispose();
                        settingWindow();
                        nameTfProfile.setText(null);
                        ageTfProfile.setText(null);

                    } else {
                        nameTfProfile.setText(null);
                        ageTfProfile.setText(null);
                    }

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

    public void chatWindow(Conversation currentConversation) {

        final UUID current_conversation_uuid = currentConversation.uuid;
        Message[] messages = clientWorker.getConversationMessages(current_conversation_uuid);
        final TextField inputTfChat = new TextField(30);
        final Button sendBtnChat = new Button("SEND");
        //final Button deleteBtnChat = new Button("Delete the group");
        final Button renameBtnChat = new Button("Rename the group");
        AtomicReference<String> groupNameChat = new AtomicReference<>("Chat");
        final Button addUserToChatBtn = new Button("Invite");
        final Button exportBtnChat = new Button("Export");
        final Button backBtn = new Button("Back");
        //JLabel groupMemberLb = new JLabel();

        //set frame
        JFrame chatFrame = new JFrame("Chat");
        chatFrame.setSize(600, 400);
        chatFrame.setLocationRelativeTo(null);
        chatFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        final ListDisplay<Message> messageListDisplay = new ListDisplay<>();
        //set display window
        //if there was exist messages, add them to model
        if (messages != null) {
            for (Message message : messages) {
                messageListDisplay.add(message, clientWorker.messageString(message));
            }
        }

        messageListDisplay.setBounds(0, 50, 400, 250);

        //set panels
        Box box = Box.createVerticalBox();
        Panel topP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel midP = new Panel();
        Panel bottomP = new Panel();
        box.add(topP);
        box.add(midP);
        box.add(bottomP);

        //add to panels
        //topP.add(addUserToChatBtn);
        topP.add(renameBtnChat);
        //topP.add(deleteBtnChat);
        topP.add(exportBtnChat);
        topP.add(backBtn);
        midP.add(messageListDisplay.getJsp());
        bottomP.add(inputTfChat);
        bottomP.add(sendBtnChat);

        //add to frame
        chatFrame.add(box);

        chatFrame.pack();
        chatFrame.setVisible(true);

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                GetEventFeedResponse response = clientWorker.getEventFeed();
                Message[] messages = response.new_messages.get(current_conversation_uuid);
                if (messages != null) {
                    for (Message message :
                            messages) {
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }

                }
                for (Message message :
                        response.updated_messages) {
                    if (Set.of(clientWorker.getConversation(current_conversation_uuid).message_uuids).contains(message.uuid)) {
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }
                }
                for (UUID uuid :
                        response.removed_messages) {
                    messageListDisplay.remove(uuid);
                }

            }
        };
        chatFrame.addWindowListener(windowListener);

        //if left clicked, edit message.
        //if right clicked, delete message.
        messageListDisplay.addListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int answer = JOptionPane.showConfirmDialog(chatFrame,
                            "Are you sure to delete the selected message?",
                            "Alert", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        UUID message_uuid = messageListDisplay.getSelectedUUID();
                        clientWorker.deleteMessage(message_uuid, current_conversation_uuid);

                    }
                }

                if (e.getClickCount() == 2) {
                    String new_content = JOptionPane.showInputDialog(chatFrame, "Edit the selected message:",
                            "Edit", JOptionPane.INFORMATION_MESSAGE);
                    UUID message_uuid = messageListDisplay.getSelectedUUID();
                    Date date = clientWorker.editMessage(message_uuid, new_content);
                    if (date != null) {
                        Message message = new Message(message_uuid, clientWorker.current_user.uuid,
                                date, new_content, current_conversation_uuid);
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }
                }
            }
        });

        ActionListener actionListener = e -> {
//            if (e.getSource() == addUserToChatBtn) {
//                String invite_uuid_String = JOptionPane.showInputDialog(chatFrame, "Enter the uuid:",
//                        "Invite", JOptionPane.INFORMATION_MESSAGE);
//                UUID invited_uuid = UUID.fromString(invite_uuid_String);
//                clientWorker.addUserToGroup(invited_uuid, clientWorker.getCurrentConversation_uuid());
//            }
//            if (e.getSource() == deleteBtnChat) {
//                int answer = JOptionPane.showConfirmDialog(chatFrame, "Are you sure to delete" +
//                        "the conversation?", "Delete", JOptionPane.OK_CANCEL_OPTION);
//                if (answer == JOptionPane.OK_OPTION) {
//                    if (clientWorker.deleteConversation(clientWorker.getCurrentConversation_uuid())) {
//                        chatFrame.dispose();
//                    }
//                }
//            }
            if (e.getSource() == renameBtnChat) {
                String new_name = JOptionPane.showInputDialog(chatFrame, "Enter new group name:",
                        "Rename", JOptionPane.PLAIN_MESSAGE);
                if (!new_name.equals("")) {
                    if (clientWorker.renameConversation(new_name, current_conversation_uuid)) {
                        groupNameChat.set(new_name);
                        chatFrame.setTitle(groupNameChat.get());
                    }
                }
            }
            if (e.getSource() == sendBtnChat) {
                Message message = new Message(clientWorker.current_user.uuid,
                        new Date(), inputTfChat.getText(), current_conversation_uuid);
                message = clientWorker.postMessage(current_conversation_uuid, message);
                if (message != null) { // post successful
                    String messageString = clientWorker.messageString(message);
                    messageListDisplay.add(message, messageString);
                    inputTfChat.setText(null);
                }

            }
            if (e.getSource() == exportBtnChat) {
                if (messages != null) {
                    clientWorker.export(current_conversation_uuid);
                } else {
                    JOptionPane.showMessageDialog(chatFrame,
                            "No messages to export!", "Export",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            if (e.getSource() == backBtn) {
                chatFrame.dispose();
                mainWindow();
            }

        };
        //deleteBtnChat.addActionListener(actionListener);
        renameBtnChat.addActionListener(actionListener);
        sendBtnChat.addActionListener(actionListener);
        exportBtnChat.addActionListener(actionListener);
        backBtn.addActionListener(actionListener);
    }


}

class ClientWorker {
    private final HashMap<UUID, Conversation> conversationHashMap = new HashMap<>();
    private final HashMap<UUID, User> userHashMap = new HashMap<>();
    private final HashMap<UUID, Message> messageHashMap = new HashMap<>();
    private final HashMap<UUID, ArrayList<UUID>> conversationMessageHashmap = new HashMap<>();
    protected User current_user;
    private Socket socket;
    private Window window;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;


    public ClientWorker() {
        connectToSocket();
    }

    public ClientWorker(Window window) {
        this();
        this.window = window;
    }


    public UUID[] getConversation_uuid_list() {
        return conversationHashMap.keySet().toArray(new UUID[0]);
    }

    /**
     * The method is used to send requests
     *
     * @param request some kind of request used
     * @return a response
     * @throws IOException
     * @throws RequestParsingException
     * @throws RequestFailedException
     */
    private Response send(Request request) throws IOException, RequestParsingException,
            RequestFailedException {
        if (objectOutputStream == null) {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        }
        objectOutputStream.writeObject(request);
        if (objectInputStream == null) {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
        }
        Response response;
        try {
            response = (Response) objectInputStream.readObject();
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
    public UUID

    signIn(String username, String password) {
        AuthenticateResponse response;
        try {
            Credential credential = new Credential(username, password);
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
            response = (AuthenticateResponse) send(authenticateRequest);
            current_user = getUser(response.user_uuid);
            getAllConversation(); //get all the conversations
            getExistMessageHistory();
            return current_user.uuid;

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User does not exist!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, "Invalid password!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (RequestParsingException e) {
            e.printStackTrace();
            return null;

        } catch (LoggedInException e) {
            JOptionPane.showMessageDialog(null,
                    "You have already logged in!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "IO exception!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }


    /**
     * The method sends a register request to the server and receives a
     * registerResponse that contains the user's uuid.
     */
    public boolean register(String username, String password, String name, String ageString) {
        RegisterResponse response;

        if (username.equals("") || password.equals("") || name.equals("") || ageString.equals("")) {
            JOptionPane.showMessageDialog(null, "Text fields cannot be blank! ",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (Integer.parseInt(ageString) < 0 || Integer.parseInt(ageString) > 120) {
            JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            Credential rgCredential = new Credential(username, password);

            Profile rgProfile = new Profile(name, Integer.parseInt(ageString));

            RegisterRequest registerRequest = new RegisterRequest(rgCredential, rgProfile);

            send(registerRequest);

            JOptionPane.showMessageDialog(null, "Registered successfully",
                    "Register", JOptionPane.INFORMATION_MESSAGE);
            return true;


        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (RequestParsingException e) {
            JOptionPane.showMessageDialog(null, "Error", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (UserExistsException e) {
            JOptionPane.showMessageDialog(null, "The username has been taken!",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (InvalidUsernameException e) {
            JOptionPane.showMessageDialog(null, "Invalid username!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (InvalidPasswordException e) {
            JOptionPane.showMessageDialog(null, "Invalid password!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO exception!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;

        }

        return false;
    }


    /**
     * Used to connect to the server
     */
    public void connectToSocket() {
        String hostname;
        String portString;
        int port;

        try {
//            hostname = JOptionPane.showInputDialog(null, "Please enter the hostname:",
//                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);
//            if (hostname == null) {
//                return;
//            }
//
//            portString = JOptionPane.showInputDialog(null, "Please enter the port number:",
//                    "Connecting...", JOptionPane.INFORMATION_MESSAGE);
//            if (portString == null) {
//                return;
//            }
            hostname = "localhost";
            portString = "9866";
            port = Integer.parseInt(portString);

            socket = new Socket(hostname, port);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Connection failed!", "Connecting...",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * send a logout request
     */
    public void logOut() {
        LogOutRequest logOutRequest = new LogOutRequest();
        try {
            send(logOutRequest);
            current_user = null;
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
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(current_user.credential);
        try {
            send(deleteAccountRequest);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (RequestFailedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get all the existed conversation uid of a user, if the user
     * has any existed conversations, add the conversations to
     * conversation_uuid_list and return true. If the user does
     * not have any conversations, return false.
     */
    public UUID[] getAllConversationUid() {
        ListAllConversationsRequest listAllConversationsRequest = new ListAllConversationsRequest();
        ListAllConversationsResponse response;

        try {
            response = (ListAllConversationsResponse) send(listAllConversationsRequest);
            return response.conversation_uuids;

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Get all the conversation existed and add to the conversation_list.
     */
    public Conversation[] getAllConversation() {
        try {
            for (UUID uuid : getAllConversationUid()) {
                GetConversationRequest getConversation = new GetConversationRequest(uuid);
                GetConversationResponse response;
                try {
                    response = (GetConversationResponse) send(getConversation);
                    Conversation conversation = response.conversation;
                    conversationHashMap.put(conversation.uuid, conversation);
                } catch (ConversationNotFoundException e) {
                    e.printStackTrace(); // shouldn't happen
                }
            }
            return conversationHashMap.values().toArray(new Conversation[0]);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

    /**
     * get previously existed conversation history and
     * save them in the map
     */
    public void getExistMessageHistory() {
        try {
            for (Conversation conversation : conversationHashMap.values()) {
                GetMessageHistoryResponse response = (GetMessageHistoryResponse) send(new GetMessageHistoryRequest(conversation.uuid));
                conversationMessageHashmap.putIfAbsent(conversation.uuid, new ArrayList<>());
                ArrayList<UUID> message_uuids = conversationMessageHashmap.get(conversation.uuid);
                for (Message message :
                        response.messages) {
                    if (!message_uuids.contains(message.uuid)) {
                        message_uuids.add(message.uuid);
                    }
                    messageHashMap.put(message.uuid, message);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
            send(dr);
            JOptionPane.showMessageDialog(null, "Successfully deleted",
                    "Delete Conversation", JOptionPane.INFORMATION_MESSAGE);
            conversationHashMap.remove(conversation_uuid);
            conversationMessageHashmap.remove(conversation_uuid);
            return true;

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
            send(addRequest);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    /**
     * Search for a user by the uuid.
     *
     * @param user_uuid the uuid of the user
     * @return return the user
     */
    public User getUser(UUID user_uuid) {
        // try local cache
        User user = userHashMap.get(user_uuid);
        if (user != null) {
            return user;
        }

        // try remote fetch
        try {
            GetUserResponse response = (GetUserResponse) send(new GetUserRequest(user_uuid));
            userHashMap.put(user_uuid, user);
            return response.user;

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User does not exist!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
    public Conversation createConversation(String groupName, UUID[] user_uuids) {
        CreateConversationRequest createRequest = new CreateConversationRequest(user_uuids, groupName);
        CreateConversationResponse response;

        try {
            response = (CreateConversationResponse) send(createRequest);
            Conversation conversation = getConversation(response.conversation_uuid);
            conversationHashMap.put(conversation.uuid, conversation);
            return conversation;

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (InvalidConversationNameException e) {
            JOptionPane.showMessageDialog(null, "Invalid conversation name!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User does not exist!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * @param conversation_uuid the uuid of the conversation
     * @return return the conversation
     */
    public Conversation getConversation(UUID conversation_uuid) {
        // try local
        Conversation conversation = conversationHashMap.get(conversation_uuid);
        if (conversation != null) {
            return new Conversation(conversation);
        }


        // try fetch
        GetConversationRequest getConversationRequest = new GetConversationRequest(conversation_uuid);
        try {
            GetConversationResponse response = (GetConversationResponse) send(getConversationRequest);
            conversation = response.conversation;
            conversationHashMap.put(conversation_uuid, conversation);
            return conversation;

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


    /**
     * The method sends a rename request and receives a response
     *
     * @param name the name of the conversation
     * @return return true if success
     */
    public boolean renameConversation(String name, UUID conversation_uuid) {
        RenameConversationRequest renameRequest = new RenameConversationRequest(conversation_uuid, name);
        try {
            send(renameRequest);
            Conversation conversation = new Conversation(getConversation(conversation_uuid));
            conversation.name = name;
            conversationHashMap.put(conversation_uuid, conversation);
            return true;

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The method sends a postMessageRequest and receives a response.
     *
     * @param conversation_uuid uuid of the conversation
     * @param message           the message that was send
     * @return return true if message was successfully send, false
     * if the message was not send
     */
    public Message postMessage(UUID conversation_uuid, Message message) {
        PostMessageRequest postMessageRequest = new PostMessageRequest(conversation_uuid, message);
        PostMessageResponse response;

        try {
            response = (PostMessageResponse) send(postMessageRequest);
            //currentMessageList.add(message);
            Message new_message = new Message(response.message_uuid, message.sender_uuid,
                    response.date, message.content, message.conversation_uuid);

            messageHashMap.put(new_message.uuid, new_message);
            conversationMessageHashmap.putIfAbsent(conversation_uuid, new ArrayList<>());
            ArrayList<UUID> message_uuids = conversationMessageHashmap.get(conversation_uuid);
            message_uuids.add(new_message.uuid);
            conversationMessageHashmap.put(conversation_uuid, message_uuids);
            return new_message;

        } catch (IllegalContentException e) {
            JOptionPane.showMessageDialog(null, "Illegal content!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }


    /**
     * After the user entered an previously existed conversation,
     * import the chat history for this conversation
     *
     * @return return the chat history
     */
    public Message[] getConversationMessages(UUID conversation_uuid) {
        conversationMessageHashmap.putIfAbsent(conversation_uuid, new ArrayList<>());
        ArrayList<UUID> message_uuids = conversationMessageHashmap.get(conversation_uuid);
        ArrayList<Message> messages = new ArrayList<>();
        for (UUID uuid :
                message_uuids) {
            Message message;
            try {
                message = getMessage(uuid); // might throw
                messages.add(message);
            } catch (MessageNotFoundException e) {
                // sometimes the message is removed but its uuid still links to the conversation.
                message_uuids.remove(uuid);
            }
        }
        return messages.toArray(new Message[0]);
    }


    /**
     * Export the current conversation history
     */
    public void export(UUID conversation_uuid) {
        //get messages from map
        ArrayList<UUID> messages_uuids = conversationMessageHashmap.get(conversation_uuid);
        Message[] message = new Message[messages_uuids.size()];
        for (int i = 0; i < messages_uuids.size(); i++) {
            message[i] = messageHashMap.get(messages_uuids.get(i));
        }

        try (PrintWriter pw = new PrintWriter("Conversation_History")) {
            pw.write("Message Sender,");
            pw.write("Timestamp,");
            pw.write("contents\n");

            for (Message value : message) {
                String sender = getUser(value.sender_uuid).credential.usrName;
                pw.write(String.format("%s,", sender));
                String time = value.time.toString();
                pw.write(String.format("%s,", time));
                pw.write(String.format("%s", value.content));
                pw.println();
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
     *
     * @param message the message
     * @return the message string
     */
    public String messageString(Message message) {
        User user = getUser(message.sender_uuid);
        return String.format("%s (sent by %s at %s)", message.content, user.credential.usrName, message.time.toString());
    }

    /**
     * The method sends a deleteMessage request and receives a response
     *
     * @param message_uuid      the uuid of the message
     * @param conversation_uuid the uuid of the conversation
     * @return true if the message was successfully deleted, false if
     * an error occurred
     */
    public boolean deleteMessage(UUID message_uuid, UUID conversation_uuid) {
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(message_uuid);

        try {
            send(deleteMessageRequest);
            messageHashMap.remove(message_uuid);
            ArrayList<UUID> message_uuids = conversationMessageHashmap.get(conversation_uuid);
            message_uuids.remove(message_uuid);
            conversationMessageHashmap.put(conversation_uuid, message_uuids);
            return true;
        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public Date editMessage(UUID message_uuid, String new_content) {
        try {
            EditMessageResponse response = (EditMessageResponse) send(new EditMessageRequest(message_uuid, new_content));
            Date date = response.dateEdited;

            Message message = getMessage(message_uuid);
            message.content = new_content;
            message.time = date;
            messageHashMap.put(message_uuid, message);
            return date;
        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalContentException e) {
            JOptionPane.showMessageDialog(null, "Illegal Content " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return null;
    }

    private Message getMessage(UUID message_uuid) throws MessageNotFoundException {
        // try local
        Message message = messageHashMap.get(message_uuid);
        if (message != null) {
            return message;
        }

        // try remote
        GetMessageRequest request = new GetMessageRequest(message_uuid);
        try {
            GetMessageResponse response = (GetMessageResponse) send(request);
            message = response.message;
            if (message == null) {
                throw new MessageNotFoundException();
            } else {
                messageHashMap.put(message.uuid, message);
                return message;
            }

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (MessageNotFoundException e) {
            throw e;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException ignored) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        throw new MessageNotFoundException();
    }


    /**
     * pass a username and return the uuid of the user
     *
     * @param username the username of the user
     * @return return the uuid of the user
     */
    public User getUser(String username) {
        GetUserByNameRequest getUserByNameRequest = new GetUserByNameRequest(username);
        GetUserByNameResponse response;
        try {
            response = (GetUserByNameResponse) send(getUserByNameRequest);
            User user = response.user;
            userHashMap.put(user.uuid, user);
            return user;
        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User does not exist!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


    /**
     * @param name      the name of the user
     * @param ageString the age of the user with a string type
     * @return return true if no error
     */
    public Profile setProfile(String name, String ageString) {
        if (name.equals("") || ageString.equals("")) {
            JOptionPane.showMessageDialog(null,
                    "Text Fields cannot be blank!");
            return null;
        }
        if (Integer.parseInt(ageString) < 0 || Integer.parseInt(ageString) > 120) {
            JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;
        }

        try {
            Profile profile = new Profile(name, Integer.parseInt(ageString));
            send(new EditProfileRequest(profile));
            current_user.profile = profile;
            return profile;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (IllegalContentException e) {
            e.printStackTrace();
            return null;

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }


    public GetEventFeedResponse getEventFeed() {
        GetEventFeedRequest request = new GetEventFeedRequest();
        GetEventFeedResponse response;

        try {
            response = (GetEventFeedResponse) send(request);

            for (User user :
                    response.new_users) {
                userHashMap.put(user.uuid, user);
            }
            for (Conversation conversation :
                    response.new_conversations) {
                conversationHashMap.put(conversation.uuid, conversation);
            }
            for (UUID conversation_uuid :
                    response.new_messages.keySet()) {
                for (Message message : response.new_messages.get(conversation_uuid)) {
                    messageHashMap.put(message.uuid, message);
                    conversationMessageHashmap.putIfAbsent(conversation_uuid, new ArrayList<>());
                    conversationMessageHashmap.get(conversation_uuid).add(message.uuid);

                }
            }
            for (User user :
                    response.updated_users) {
                userHashMap.put(user.uuid, user);
            }
            for (Conversation conversation :
                    response.updated_conversations) {
                conversationHashMap.put(conversation.uuid, conversation);

            }
            for (Message message :
                    response.updated_messages) {
                messageHashMap.put(message.uuid, message);
            }

            for (UUID uuid :
                    response.removed_users) {
                userHashMap.remove(uuid);
            }
            for (UUID uuid :
                    response.removed_conversations) {
                conversationHashMap.remove(uuid);
            }
            for (UUID uuid :
                    response.removed_messages) {
                for (ArrayList<UUID> uuids : conversationMessageHashmap.values()) {
                    uuids.remove(uuid);
                }
            }
            return response;


        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "IO Exception",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (RequestFailedException e) {
//                JOptionPane.showMessageDialog(null, "Request failed!",
//                        "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Nothing needs to be updated.");
        }


        return null;
    }
}





