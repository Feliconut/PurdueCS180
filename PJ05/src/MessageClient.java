import Exceptions.*;
import Field.*;
import Request.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.atomic.*;

/**
 * PJ5-MessageClient
 * This class enables connections between the client and
 * the server. It also displays the log-in user interface.
 *
 * @author Silvia Yang, lab sec OL3
 * @version April
 */

public class MessageClient {

    public static void main(String[] args) {
        new Window();
    }

}

class ListDisplay<T extends Storable> {

    final DefaultListModel<String> displayListModel;
    private final JList<String> displayList;
    private final ArrayList<UUID> uuids;
    private final JScrollPane jsp;
    private final ArrayList<Boolean> notifyStates;

    public ListDisplay() {
        this.displayListModel = new DefaultListModel<>();
        this.displayList = new JList<>(displayListModel);
        this.uuids = new ArrayList<>();
        this.notifyStates = new ArrayList<>();
        displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jsp = new JScrollPane(displayList);
        jsp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

    }

    public void notifyAll(Iterable<UUID> uuids) {
        for (UUID uuid : uuids) {
            notify(uuid);
        }
    }

    public void notify(UUID uuid) {
        setNotify(uuid, true);
    }

    private void setNotify(UUID uuid, boolean state) {
        int index = uuids.indexOf(uuid);
        setNotify(index, state);
    }

    private void setNotify(int index, boolean state) {
        if (index > -1 && index < notifyStates.size()) {
            notifyStates.set(index, state);
            displayListModel.set(index, toggleStringNotify(displayListModel.get(index), state));
        }

    }

    private String toggleStringNotify(String str, Boolean state) {
        if (str.startsWith("(*)")) {
            if (!state) {
                return str.substring(3);
            }
        } else {
            if (state) {
                return "(*)" + str;
            }
        }
        return str;
    }

    public void disNotify(UUID uuid) {
        setNotify(uuid, false);
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
        int index = displayList.getSelectedIndex();
        if (index == -1) {
            return null;
        } else {
            return uuids.get(index);
        }
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
        notifyStates.add(false);
    }

    private String makeDisplay(T obj) {
        String display;
        if (obj instanceof Conversation) {
            Conversation conversation = (Conversation) obj;
            display = String.format("%s (%d people)", conversation.name, conversation.userUUIDs.length);
        } else if (obj instanceof Message) {
            Message message = (Message) obj;
            display = String.format("%s (sent by <unknown> at %s)", message.content, message.time.toString());
        } else if (obj instanceof User) {
            User user = (User) obj;
            display = String
                    .format("%s (%s, %d years old)", user.credential.usrName, user.profile.name, user.profile.age);
        } else {
            display = obj.toString();
        }
        return display;

    }

    public void remove(UUID uuid) {
        int index = uuids.indexOf(uuid);
        if (index != -1) {
            uuids.remove(index);
            displayListModel.remove(index);
            notifyStates.remove(index);
        }
    }

    public void update(T obj) {
        update(obj, null);
    }

    public void update(T obj, String display) {
        int index = uuids.indexOf(obj.uuid);
        if (index == -1) {
            add(obj, display); // add the object if it does not exist
        } else {
            update(obj, display, index);
        }
    }

    private void update(T obj, String display, int index) {
        if (display != null) {
            displayListModel.set(index, display);
        } else {
            displayListModel.set(index, makeDisplay(obj));
        }
    }

}

class Window {
    private final ClientWorker clientWorker = new ClientWorker();

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
        titleLbSign = new JLabel("<html><center><font size='20'>Easy Chat</font></center></html>");
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
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == registerButtonSign) {
                    Window.this.registerWindow();
                    // frame.dispose();
                }
                if (e.getSource() == okButtonSign) {
                    String username = usernameTfSign.getText();
                    String password = Arrays.toString(passwordTfSign.getPassword());
                    UUID myUUID = clientWorker.signIn(username, password);
                    if (myUUID != null) {
                        Window.this.mainWindow();
                        frame.dispose();
                    } else {
                        usernameTfSign.setText(null);
                        passwordTfSign.setText(null);
                    }
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
        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
        final JLabel myUUID = new JLabel();
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
        myUUID.setText(String.format("%s (%s)",
                clientWorker.currentUser.profile.name,
                clientWorker.currentUser.credential.usrName));

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
        topP.add(myUUID);
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
        bigPanel.add(Box.createVerticalStrut(50));
        bigPanel.add(midPanel);
        bigPanel.add(Box.createVerticalStrut(50));

        //set up the conversation list
        for (Conversation conversation :
                clientWorker.getAllConversation()) {
            conversationListDisplay.add(conversation);
        }

        //add to chatroomP
        chatroomP.setLayout(new BoxLayout(chatroomP, BoxLayout.Y_AXIS));
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
        newChatP.setLayout(new BoxLayout(newChatP, BoxLayout.Y_AXIS));
        Panel newChatLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel labelP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addLbP = new Panel(new FlowLayout(FlowLayout.LEFT));
        Panel addP = new Panel();
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
        mainFrame.add(bigPanel);
        mainFrame.setVisible(true);
        mainFrame.pack();
        //if the list is clicked twice open up the selected conversation
        //if the list is clicked once show the conversation name in the label below
        //if the list is right-clicked pop up delete message
        conversationListDisplay.addListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    UUID selectedUUID = conversationListDisplay.getSelectedUUID();
                    clientWorker.resolve(selectedUUID);
                    if (e.getClickCount() == 2) {
                        Conversation conversation = clientWorker.getConversation(selectedUUID);
                        //clientWorker.setToNewConversation(list.getSelectedValue());
                        chatWindow(conversation);
                        mainFrame.dispose();
                    }

                    if (e.getClickCount() == 1) {
                        //display group name
                        String groupName = clientWorker.getConversation(selectedUUID).name;
                        groupInfoLb.setText(String.format("Group name: %s", groupName));

                        //display group members
                        UUID[] members = clientWorker.getConversation(selectedUUID).userUUIDs;
                        StringBuilder memberString = new StringBuilder();
                        memberString.append("Group members: ");

                        for (UUID member : members) {
                            memberString.append(clientWorker.getUser(member).credential.usrName);
                            memberString.append(" ");
                        }

                        groupMemberLb.setText(String.valueOf(memberString));

                    }

                    if (e.getButton() == MouseEvent.BUTTON3) {
                        int answer = JOptionPane.showConfirmDialog(null,
                                "Delete selected conversation from your list?",
                                "Delete", JOptionPane.YES_NO_OPTION);

                        if (answer == JOptionPane.YES_OPTION) {
                            if (clientWorker.deleteConversation(selectedUUID)) {
                                groupInfoLb.setText(null);
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        });

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                super.windowActivated(e);
                GetEventFeedResponse response = clientWorker.getEventFeed();
                for (Conversation conversation :
                        response.newConversations) {
                    conversationListDisplay.update(conversation);
                }
                for (Conversation conversation :
                        response.updatedConversations) {
                    conversationListDisplay.update(conversation);
                }
                for (UUID uuid :
                        response.removedConversations) {
                    conversationListDisplay.remove(uuid);
                }

                for (UUID uuid : response.newMessages.keySet()) {
                    conversationListDisplay.notify(uuid);
                }
                conversationListDisplay.notifyAll(clientWorker.getUnresolved());
            }
        };
        mainFrame.addWindowListener(windowListener);

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == settingBtnM) {
                    Window.this.settingWindow();
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
                    //create a new conversation
                    UUID[] userUUIDs = new UUID[invitedUsers.size()];
                    for (int i = 0; i < invitedUsers.size(); i++) {
                        userUUIDs[i] = invitedUsers.get(i);
                    }
                    Conversation conversation = clientWorker.createConversation(groupName, userUUIDs);
                    conversationListDisplay.add(conversation);

                    Window.this.chatWindow(conversation);
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
                        UUID userUUID = user.uuid;
                        showUsernameTa.append(String.format("%s ", username));
                        invitedUsers.add(userUUID);
                    }
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

        ActionListener actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == backBtnSetting) {
                    Window.this.mainWindow();
                    settingFrame.dispose();
                }
                if (e.getSource() == manageProfileBtnSetting) {
                    Window.this.manageProfileWindow();
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
                    Profile profile = clientWorker.currentUser.profile;

                    String name = profile.name;
                    int age = profile.age;

                    String message = String.format("Name: %s\n" +
                                                   "Age: %d\n", name, age);

                    JOptionPane.showMessageDialog(settingFrame, message, "Profile",
                            JOptionPane.INFORMATION_MESSAGE);
                }
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
                        Window.this.settingWindow();

                    }
                    nameTfProfile.setText(null);
                    ageTfProfile.setText(null);

                }
                if (e.getSource() == cancelBtnProfile) {
                    Window.this.settingWindow();
                    profileFrame.dispose();
                }
            }
        };

        okBtnProfile.addActionListener(actionListener);
        cancelBtnProfile.addActionListener(actionListener);
    }

    public void chatWindow(Conversation currentConversation) {

        final UUID currentConversationUUID = currentConversation.uuid;
        Message[] messages = clientWorker.getConversationMessages(currentConversationUUID);
        final TextField inputTfChat = new TextField(30);
        final Button sendBtnChat = new Button("SEND");
        //final Button deleteBtnChat = new Button("Delete the group");
        final Button leaveBtnChat = new Button("Leave the group");
        final Button renameBtnChat = new Button("Rename the group");
        AtomicReference<String> title = new AtomicReference<>(currentConversation.name);
        final Button addUserToChatBtn = new Button("Invite");
        final Button exportBtnChat = new Button("Export");
        final Button backBtn = new Button("Back");
        //JLabel groupMemberLb = new JLabel();

        //set frame
        JFrame chatFrame = new JFrame(title.get());
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
        topP.add(leaveBtnChat);
        topP.add(backBtn);
        midP.add(messageListDisplay.getJsp());
        midP.add(addUserToChatBtn);
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
                Message[] messages = response.newMessages.get(currentConversationUUID);
                if (messages != null) {
                    for (Message message :
                            messages) {
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }

                }
                for (Message message :
                        response.updatedMessages) {
                    if (Set.of(clientWorker.getConversation(currentConversationUUID).messageUUIDs)
                            .contains(message.uuid)) {
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }
                }
                for (UUID uuid :
                        response.removedMessages) {
                    messageListDisplay.remove(uuid);
                }
                clientWorker.resolve(currentConversationUUID);
                messageListDisplay.notifyAll(clientWorker.getUnresolved());

            }
        };
        chatFrame.addWindowListener(windowListener);

        //if left clicked, edit message.
        //if right clicked, delete message.
        messageListDisplay.addListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                UUID selectedUUID = messageListDisplay.getSelectedUUID();
                clientWorker.resolve(selectedUUID);
                messageListDisplay.disNotify(selectedUUID);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    int answer = JOptionPane.showConfirmDialog(chatFrame,
                            "Are you sure to delete the selected message?",
                            "Alert", JOptionPane.YES_NO_OPTION);
                    if (answer == JOptionPane.YES_OPTION) {
                        clientWorker.deleteMessage(selectedUUID, currentConversationUUID);
                    }
                }

                if (e.getClickCount() == 2) {
                    String newContent = JOptionPane.showInputDialog(chatFrame, "Edit the selected message:",
                            "Edit", JOptionPane.INFORMATION_MESSAGE);
                    Date date = clientWorker.editMessage(selectedUUID, newContent);
                    if (date != null) {
                        Message message = new Message(selectedUUID, clientWorker.currentUser.uuid,
                                date, newContent, currentConversationUUID);
                        messageListDisplay.update(message, clientWorker.messageString(message));
                    }
                }
            }
        });

        addUserToChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(chatFrame, "Enter the username:",
                        "Invite", JOptionPane.INFORMATION_MESSAGE);
                try {
                    clientWorker.addUserToGroup(clientWorker.getUser(username).uuid, currentConversationUUID);
                } catch (NullPointerException ignored) {
                }
            }
        });
        leaveBtnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = JOptionPane.showConfirmDialog(chatFrame, "Are you sure to leave" +
                        "the conversation?", "Leave", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    if (clientWorker.leaveConversation(currentConversationUUID)) {
                        chatFrame.dispose();
                        Window.this.mainWindow();
                    }
                }

            }
        });
        renameBtnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newName = JOptionPane.showInputDialog(chatFrame, "Enter new group name:",
                        "Rename", JOptionPane.PLAIN_MESSAGE);
                if (!newName.equals("")) {
                    if (clientWorker.renameConversation(newName, currentConversationUUID)) {
                        title.set(newName);
                        chatFrame.setTitle(title.get());
                    }
                }

            }
        });
        sendBtnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Message message = new Message(clientWorker.currentUser.uuid,
                        new Date(), inputTfChat.getText(), currentConversationUUID);
                message = clientWorker.postMessage(currentConversationUUID, message);
                if (message != null) { // post successful
                    String messageString = clientWorker.messageString(message);
                    messageListDisplay.add(message, messageString);
                    inputTfChat.setText(null);
                }

            }
        });
        exportBtnChat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (messages != null) {
                    clientWorker.export(currentConversationUUID);
                } else {
                    JOptionPane.showMessageDialog(chatFrame,
                            "No messages to export!", "Export",
                            JOptionPane.INFORMATION_MESSAGE);
                }

            }
        });
        backBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatFrame.dispose();
                Window.this.mainWindow();

            }
        });
    }


}

class ClientWorker {
    private final HashMap<UUID, Conversation> conversationHashMap = new HashMap<>();
    private final HashMap<UUID, User> userHashMap = new HashMap<>();
    private final HashMap<UUID, Message> messageHashMap = new HashMap<>();
    private final HashMap<UUID, ArrayList<UUID>> conversationMessageHashmap = new HashMap<>();
    private final HashSet<UUID> unresolved = new HashSet<>();
    protected User currentUser;
    private Socket socket;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;

    public ClientWorker() {
        connectToSocket();

    }

    /**
     * The method sends a register request to the server and receives a
     * registerResponse that contains the user's uuid.
     */
    public boolean register(String username, String password, String name, String ageString) {

        if (username.equals("") || password.equals("") || name.equals("") || ageString.equals("")) {
            JOptionPane.showMessageDialog(null, "Text fields cannot be blank! ",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }


        try {
            if (Integer.parseInt(ageString) < 0 || Integer.parseInt(ageString) > 120) {
                JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
            Credential rgCredential = new Credential(username, password);

            Profile rgProfile = new Profile(name, Integer.parseInt(ageString));

            RegisterRequest registerRequest = new RegisterRequest(rgCredential, rgProfile);

            send(registerRequest); // we don't need to response here

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


        }

        return false;
    }

    public HashSet<UUID> getUnresolved() {
        return unresolved;
    }

    public void resolve(UUID uuid) {
        unresolved.remove(uuid);
    }

    /**
     * The method sends an authenticate request and receives
     * a response
     */
    public UUID signIn(String username, String password) {
        AuthenticateResponse response;
        try {
            Credential credential = new Credential(username, password);
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
            response = (AuthenticateResponse) send(authenticateRequest);
            currentUser = getUser(response.userUUID);
            getAllConversation(); //get all the conversations
            getExistMessageHistory();
            return currentUser.uuid;

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

        }
    }

    /**
     * The method is used to send requests
     *
     * @param request some kind of request used
     * @throws RequestParsingException Error regarding parsing a request.
     * @throws RequestFailedException  Error because the request failed.
     */
    private Response send(Request request) throws RequestParsingException,
            RequestFailedException {
        try {
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
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "IO exception! Reconnecting now.", "Error",
                    JOptionPane.ERROR_MESSAGE);

            connectToSocket();
            throw new RequestParsingException();
        }
    }

    /**
     * Used to connect to the server
     */
    public void connectToSocket() {
        String hostname;
        String portString;
        int port;

        try {
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

                hostname = "localhost";
                portString = "9866";
                port = Integer.parseInt(portString);

                socket = new Socket(hostname, port);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Cannot Connect! Try reopen the application!", "Error",
                        JOptionPane.ERROR_MESSAGE);

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid input!", "Connecting...",
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
            currentUser = null;
        } catch (RequestFailedException e) {
            e.printStackTrace();
        }
    }


    /**
     * send a delete account request
     */
    public boolean deleteAccount() {
        DeleteAccountRequest deleteAccountRequest = new DeleteAccountRequest(currentUser.credential);
        try {
            send(deleteAccountRequest);
            return true;
        } catch (RequestFailedException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * get all the existed conversation uid of a user, if the user
     * has any existed conversations, add the conversations to
     * conversationUUID_list and return true. If the user does
     * not have any conversations, return false.
     */
    public UUID[] getAllConversationUid() {
        ListAllConversationsRequest listAllConversationsRequest = new ListAllConversationsRequest();
        ListAllConversationsResponse response;

        try {
            response = (ListAllConversationsResponse) send(listAllConversationsRequest);
            return response.conversationUUIDs;

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
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
                GetMessageHistoryResponse response = (GetMessageHistoryResponse) send(
                        new GetMessageHistoryRequest(conversation.uuid));
                conversationMessageHashmap.putIfAbsent(conversation.uuid, new ArrayList<>());
                ArrayList<UUID> messageUUIDs = conversationMessageHashmap.get(conversation.uuid);
                for (Message message :
                        response.messages) {
                    if (!messageUUIDs.contains(message.uuid)) {
                        messageUUIDs.add(message.uuid);
                    }
                    messageHashMap.put(message.uuid, message);
                }
            }

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
     * @param conversationUUID the uuid of the conversation
     * @return return true if success
     */
    public boolean deleteConversation(UUID conversationUUID) {
        DeleteConversationRequest dr = new DeleteConversationRequest(conversationUUID);
        try {
            send(dr);
            JOptionPane.showMessageDialog(null, "Successfully deleted",
                    "Delete Conversation", JOptionPane.INFORMATION_MESSAGE);
            conversationHashMap.remove(conversationUUID);
            conversationMessageHashmap.remove(conversationUUID);
            return true;

        } catch (NotLoggedInException e) {

            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);


        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public boolean leaveConversation(UUID conversationUUID) {
        QuitConversationRequest request = new QuitConversationRequest(conversationUUID);
        try {
            send(request);
            JOptionPane.showMessageDialog(null, "Successfully left",
                    "Leave Conversation", JOptionPane.INFORMATION_MESSAGE);
            conversationHashMap.remove(conversationUUID);
            conversationMessageHashmap.remove(conversationUUID);
            return true;

        } catch (NotLoggedInException e) {

            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
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
     * @param userUUID         the user's uuid that you want to add
     * @param conversationUUID the uuid of the conversation
     */
    public void addUserToGroup(UUID userUUID, UUID conversationUUID) {
        AddUser2ConversationRequest addRequest = new AddUser2ConversationRequest(userUUID, conversationUUID);
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


        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Create a conversation
     * Sends a request and add the conversation both to
     * the UI and the data stored in client
     *
     * @param groupName the name of the conversation
     * @return the uuid of the group
     */
    public Conversation createConversation(String groupName, UUID[] userUUIDs) {
        CreateConversationRequest createRequest = new CreateConversationRequest(userUUIDs, groupName);
        CreateConversationResponse response;

        try {
            response = (CreateConversationResponse) send(createRequest);
            Conversation conversation = getConversation(response.conversationUUID);
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


        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * @param conversationUUID the uuid of the conversation
     * @return return the conversation
     */
    public Conversation getConversation(UUID conversationUUID) {
        // try local
        Conversation conversation = conversationHashMap.get(conversationUUID);
        if (conversation != null) {
            return new Conversation(conversation);
        }


        // try fetch
        GetConversationRequest getConversationRequest = new GetConversationRequest(conversationUUID);
        try {
            GetConversationResponse response = (GetConversationResponse) send(getConversationRequest);
            conversation = response.conversation;
            conversationHashMap.put(conversationUUID, conversation);
            return conversation;

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
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
    public boolean renameConversation(String name, UUID conversationUUID) {
        RenameConversationRequest renameRequest = new RenameConversationRequest(conversationUUID, name);
        try {
            send(renameRequest);
            Conversation conversation = new Conversation(getConversation(conversationUUID));
            conversation.name = name;
            conversationHashMap.put(conversationUUID, conversation);
            return true;

        } catch (ConversationNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Conversation not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
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
     * @param conversationUUID uuid of the conversation
     * @param message           the message that was send
     * @return return true if message was successfully send, false
     * if the message was not send
     */
    public Message postMessage(UUID conversationUUID, Message message) {
        PostMessageRequest postMessageRequest = new PostMessageRequest(conversationUUID, message);
        PostMessageResponse response;

        try {
            response = (PostMessageResponse) send(postMessageRequest);
            //currentMessageList.add(message);
            Message newMessage = new Message(response.messageUUID, message.senderUUID,
                    response.date, message.content, message.conversationUUID);

            messageHashMap.put(newMessage.uuid, newMessage);
            conversationMessageHashmap.putIfAbsent(conversationUUID, new ArrayList<>());
            ArrayList<UUID> messageUUIDs = conversationMessageHashmap.get(conversationUUID);
            messageUUIDs.add(newMessage.uuid);
            conversationMessageHashmap.put(conversationUUID, messageUUIDs);
            return newMessage;

        } catch (IllegalContentException e) {
            JOptionPane.showMessageDialog(null, "Illegal content!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have logged out!",
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
    public Message[] getConversationMessages(UUID conversationUUID) {
        conversationMessageHashmap.putIfAbsent(conversationUUID, new ArrayList<>());
        ArrayList<UUID> messageUUIDs = conversationMessageHashmap.get(conversationUUID);
        ArrayList<Message> messages = new ArrayList<>();
        for (UUID uuid :
                messageUUIDs) {
            Message message;
            try {
                message = getMessage(uuid); // might throw
                messages.add(message);
            } catch (MessageNotFoundException e) {
                // sometimes the message is removed but its uuid still links to the conversation.
                messageUUIDs.remove(uuid);
            }
        }
        return messages.toArray(new Message[0]);
    }

    /**
     * Search for a user by the uuid.
     *
     * @param userUUID the uuid of the user
     * @return return the user
     */
    public User getUser(UUID userUUID) {
        // try local cache
        User user = userHashMap.get(userUUID);
        if (user != null) {
            return user;
        }

        // try remote fetch
        try {
            GetUserResponse response = (GetUserResponse) send(new GetUserRequest(userUUID));
            userHashMap.put(userUUID, response.user);
            return response.user;

        } catch (UserNotFoundException e) {
            JOptionPane.showMessageDialog(null, "User does not exist!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);


        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }

    /**
     * Export the current conversation history
     */
    public void export(UUID conversationUUID) {
        //get messages from map
        ArrayList<UUID> messagesUUIDs = conversationMessageHashmap.get(conversationUUID);
        Message[] message = new Message[messagesUUIDs.size()];
        for (int i = 0; i < messagesUUIDs.size(); i++) {
            message[i] = messageHashMap.get(messagesUUIDs.get(i));
        }

        try (PrintWriter pw = new PrintWriter("Conversation_History")) {
            pw.write("Message Sender,");
            pw.write("Timestamp,");
            pw.write("contents\n");

            for (Message value : message) {
                String sender = getUser(value.senderUUID).credential.usrName;
                pw.write(String.format("%s,", sender));
                String time = value.time.toString();
                pw.write(String.format("%s,", time));
                pw.write(String.format("%s", value.content));
                pw.println();
            }
            JOptionPane.showMessageDialog(null, "Export successfully!",
                    "export", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "IO exception!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        }
    }

    /**
     * The method sends a deleteMessage request and receives a response
     *
     * @param messageUUID      the uuid of the message
     * @param conversationUUID the uuid of the conversation
     */
    public void deleteMessage(java.util.UUID messageUUID, java.util.UUID conversationUUID) {
        DeleteMessageRequest deleteMessageRequest = new DeleteMessageRequest(messageUUID);

        try {
            send(deleteMessageRequest);
            messageHashMap.remove(messageUUID);
            ArrayList<UUID> messageUUIDs = conversationMessageHashmap.get(conversationUUID);
            messageUUIDs.remove(messageUUID);
            conversationMessageHashmap.put(conversationUUID, messageUUIDs);
        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);

        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);


        } catch (RequestFailedException e) {
            JOptionPane.showMessageDialog(null, "Request failed!",
                    "Error", JOptionPane.ERROR_MESSAGE);
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
        User user = getUser(message.senderUUID);
        return String.format("%s: %s (%s)", user.credential.usrName, message.content, message.time.toString());
    }

    public Date editMessage(UUID messageUUID, String newContent) {
        try {
            EditMessageResponse response = (EditMessageResponse) send(
                    new EditMessageRequest(messageUUID, newContent));
            Date date = response.dateEdited;

            Message message = getMessage(messageUUID);
            message.content = newContent;
            message.time = date;
            messageHashMap.put(messageUUID, message);
            return date;
        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);

        } catch (MessageNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Message not found!", "Error",
                    JOptionPane.ERROR_MESSAGE);

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

    private Message getMessage(UUID messageUUID) throws MessageNotFoundException {
        // try local
        Message message = messageHashMap.get(messageUUID);
        if (message != null) {
            return message;
        }

        // try remote
        GetMessageRequest request = new GetMessageRequest(messageUUID);
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

        try {
            if (Integer.parseInt(ageString) < 0 || Integer.parseInt(ageString) > 120) {
                JOptionPane.showMessageDialog(null, "Invalid age!", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return null;
            }
            Profile profile = new Profile(name, Integer.parseInt(ageString));
            send(new EditProfileRequest(profile));
            currentUser.profile = profile;
            return profile;

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null,
                    "Invalid age!", "Error",
                    JOptionPane.ERROR_MESSAGE);
            return null;

        } catch (IllegalContentException e) {
            e.printStackTrace();
            return null;

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
                    response.newUsers) {
                userHashMap.put(user.uuid, user);
            }
            for (Conversation conversation :
                    response.newConversations) {
                conversationHashMap.put(conversation.uuid, conversation);

                if (!conversation.adminUUID.equals(currentUser.uuid)) {
                    unresolved.add(conversation.uuid);
                }
            }
            for (UUID conversationUUID :
                    response.newMessages.keySet()) {
                for (Message message : response.newMessages.get(conversationUUID)) {
                    messageHashMap.put(message.uuid, message);
                    conversationMessageHashmap.putIfAbsent(conversationUUID, new ArrayList<>());
                    conversationMessageHashmap.get(conversationUUID).add(message.uuid);
                    if (!message.senderUUID.equals(currentUser.uuid)) {
                        unresolved.add(message.uuid);
                        unresolved.add(conversationUUID);
                    }
                }

            }
            for (User user :
                    response.updatedUsers) {
                userHashMap.put(user.uuid, user);
            }
            for (Conversation conversation :
                    response.updatedConversations) {
                conversationHashMap.put(conversation.uuid, conversation);
                unresolved.add(conversation.uuid);
            }
            for (Message message :
                    response.updatedMessages) {
                messageHashMap.put(message.uuid, message);
                unresolved.add(message.uuid);
                unresolved.add(message.conversationUUID);
            }

            for (UUID uuid :
                    response.removedUsers) {
                userHashMap.remove(uuid);
            }
            for (UUID uuid :
                    response.removedConversations) {
                conversationHashMap.remove(uuid);
            }
            for (UUID uuid :
                    response.removedMessages) {
                for (ArrayList<UUID> uuids : conversationMessageHashmap.values()) {
                    uuids.remove(uuid);
                }
            }
            return response;


        } catch (NotLoggedInException e) {
            JOptionPane.showMessageDialog(null, "You have been logged out!",
                    "Error", JOptionPane.ERROR_MESSAGE);


        } catch (RequestFailedException e) {
            System.out.println("Nothing needs to be updated.");
        }


        return null;
    }
}





