import Field.User;

public class MessageClient {

    private User user;
    private Credential credential;
    private Profile profile;
    private UUID uuid;

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
     *
     * @return true if the user passed the authentication, false
     * if the user is not registered or entered wrong password
     */
    public boolean signIn() {
        String username = signInWindow.getUsername();
        String password = signInWindow.getPassword();

        try {
            credential = new Credential(username, password);
            AuthenticateRequest authenticateRequest = new AuthenticateRequest(credential);
            Response response = send(authenticateRequest, socket);
            uuid = response.uuid;
            return true;

        //TODO use calculator client code, establish connection

        //TODO display main UI, popup sign-in window.

        //TODO query for new events every few seconds.

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

    public Profile getProfile() {
        GetUserRequest getUserRequest = new GetUserRequest(credential, uuid);
//        try {
//            user = (GetUserResponse)send(getUserRequest,socket);
//            profile = user.profile;
//            return profile;
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (RequestFailedException e) {
//            e.printStackTrace();
//        }
        return null;
    }

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


