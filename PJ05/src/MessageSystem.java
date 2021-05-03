import Exceptions.*;
import Field.*;

import java.util.*;

/**
 * Project5-- server worker's method
 * <p>
 * This class will do some specific work based on the command send form server worker
 *
 * @author team 84
 * @version 04/30/2021
 */

public class MessageSystem {
    private final Database<User> userDatabase;
    private final Database<Message> messageDatabase;
    private final Database<Conversation> conversationDatabase;
    private final EventBagHandler eventBagHandler;


    public MessageSystem(String userFileName, String messageFileName, String conversationFileName) {
        userDatabase = new Database<>(userFileName, User.class);
        conversationDatabase = new Database<>(conversationFileName, Conversation.class);
        messageDatabase = new Database<>(messageFileName, Message.class);
        eventBagHandler = new EventBagHandler();
    }

    public User signIn(Credential credential)
            throws LoggedInException, UserNotFoundException, InvalidPasswordException {
        User user = getUser(credential);
        eventBagHandler.signIn(user.uuid);
        return new User(user);
    }

    public User getUser(Credential credential) throws UserNotFoundException, InvalidPasswordException {

        User user = getUser(credential.usrName);
        if (user.credential.passwd.equals(credential.passwd)) {
            return new User(user);
        } else {
            throw new InvalidPasswordException();
        }
    }

    public User getUser(String name) throws UserNotFoundException {
        for (UUID uuid : userDatabase.uuids()) {
            User user = userDatabase.get(uuid);
            Credential userCredential = user.credential;
            if (userCredential.usrName.equals(name)) {
                return new User(user);
            }
        }

        throw new UserNotFoundException();
    }

    public void signOut(UUID userUUID) throws NotLoggedInException {
        eventBagHandler.signOut(userUUID);
    }

    public EventBag getEventBag(UUID userUUID) throws NotLoggedInException {
        return eventBagHandler.flush(userUUID);
    }

    public User addUser(Credential credential, Profile profile) throws UserExistsException, InvalidUsernameException {
        //make sure the user not exist
        try {
            getUser(credential.usrName);
            throw new UserExistsException();
        } catch (UserNotFoundException ignored) {

        }
        String name = credential.usrName;
        if (name == null) {
            throw new InvalidUsernameException();
        } else if (name.contains(" ")) {
            throw new InvalidUsernameException();
        } else if (name.length() > 20) {
            throw new InvalidUsernameException();
        }
        User user = new User(credential, profile);
        userDatabase.put(user.uuid, user);
        eventBagHandler.add(user);
        return user;

    }

    public void deleteUser(UUID uuid) throws UserNotFoundException {
        User user;
        user = userDatabase.remove(uuid);
        if (user == null) {
            throw new UserNotFoundException();
        }
        eventBagHandler.remove(user);
    }

    public void deleteMessage(UUID uuid) throws MessageNotFoundException, ConversationNotFoundException {
        Message message;
        message = messageDatabase.remove(uuid);
        if (message == null) {
            throw new MessageNotFoundException();
        }
        eventBagHandler.remove(message, getConversation(message.conversationUUID));
    }

    public Message addMessage(UUID conversationUUID, UUID senderUUID, String content)
            throws ConversationNotFoundException, IllegalContentException {
        // want to add the a message to it's conversation and return the message
        Date date = new Date();
        if (content == null) {
            throw new IllegalContentException("content cannot be empty or too long");
        }
        content = content.strip();

        if (content.equals("") || content.length() > 500) {
            throw new IllegalContentException("content cannot be empty or too long");
        }

        Message message = new Message(senderUUID, date, content, conversationUUID);
        messageDatabase.put(message.uuid, message);
        Conversation conversation = getConversation(conversationUUID);
        ArrayList<UUID> modifiedUUIDs = new ArrayList<>(Arrays.asList(conversation.messageUUIDs));
        modifiedUUIDs.add(message.uuid);
        conversation.messageUUIDs = modifiedUUIDs.toArray(new UUID[0]);
        conversationDatabase.put(conversationUUID, conversation);
        eventBagHandler.add(conversation, message);
        eventBagHandler.update(conversation);

        return message;
    }

    public Conversation getConversation(UUID conversationUUID) throws ConversationNotFoundException {
        Conversation conversation;
        conversation = conversationDatabase.get(conversationUUID);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return new Conversation(conversation);
        }
    }

    public Conversation createConversation(String conversationName, UUID[] userUUIDs, UUID adminUUID)
            throws InvalidConversationNameException, UserNotFoundException {
        // Make sure the name is valid
        if (conversationName == null) {
            throw new InvalidConversationNameException();
        } else if (conversationName.length() > 20) {
            throw new InvalidConversationNameException();
        }

        // add admin to userUUIDs
        ArrayList<UUID> userUUIDArray = new ArrayList<>(Arrays.asList(userUUIDs));
        userUUIDArray.add(adminUUID);
        // make distinct
        userUUIDs = userUUIDArray.stream().distinct().toArray(UUID[]::new); // make userUUIDs distinct
        // check user exists
        for (UUID userUUID :
                userUUIDs) {
            getUser(userUUID);
        }
        // create conversation
        Conversation conversation = new Conversation(conversationName, userUUIDs, adminUUID, new UUID[0]);
        conversationDatabase.put(conversation.uuid, conversation);
        eventBagHandler.add(conversation);
        return conversation;
    }

    public User getUser(UUID findUUID) throws UserNotFoundException {
        User user;

        user = userDatabase.get(findUUID);
        if (user == null) {
            throw new UserNotFoundException();

        } else {
            return new User(user);
        }

    }

    public void renameConversation(String conversationName, UUID conversationUUID)
            throws ConversationNotFoundException {
        //rename
        Conversation conversation = getConversation(conversationUUID);
        conversation.name = conversationName;
        conversationDatabase.put(conversationUUID, conversation);
        eventBagHandler.update(conversation);
    }

    public void addUser2Conversation(UUID userUUID, UUID conversationUUID) throws UserNotFoundException,
            ConversationNotFoundException {
        //拉userUUID进入conversationUUID这个群聊
        Conversation conversation = conversationDatabase.get(conversationUUID);
        getUser(userUUID);
        UUID[] uuids = conversation.userUUIDs;
        ArrayList<UUID> uuidArrayList = new ArrayList<>(Arrays.asList(uuids));
        uuidArrayList.add(userUUID);
        conversation.userUUIDs = uuidArrayList.stream().distinct().toArray(UUID[]::new);
        conversationDatabase.put(conversationUUID, conversation);
        eventBagHandler.update(conversation);
    }

    public void quitConversation(UUID userUUID, UUID conversationUUID)
            throws ConversationNotFoundException, UserNotFoundException {
        //这个uuid主动离开这个conversationUUID群聊
        Conversation conversation = getConversation(conversationUUID);
        getUser(userUUID);

        ArrayList<UUID> userUUIDs = new ArrayList<>(Arrays.asList(conversation.userUUIDs));

        if (userUUIDs.remove(userUUID)) {
            conversation.userUUIDs = userUUIDs.toArray(new UUID[0]);
            if (conversation.userUUIDs.length == 0) {
                deleteConversation(conversation.uuid);
            } else {
                if (userUUID.equals(conversation.adminUUID)) {
                    setAdmin(conversation.userUUIDs[0], conversationUUID);
                }
                conversationDatabase.put(conversationUUID, conversation);
                eventBagHandler.update(conversation);
            }
        } else {
            throw new UserNotFoundException();
        }

    }

    public Conversation deleteConversation(UUID uuid) throws ConversationNotFoundException {
        Conversation conversation;
        conversation = conversationDatabase.remove(uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {

            eventBagHandler.remove(conversation);
            return new Conversation(conversation);
        }
    }

    public void setAdmin(UUID adminUUID, UUID conversationUUID)
            throws ConversationNotFoundException, UserNotFoundException {
        Conversation conversation = getConversation(conversationUUID);

        if (!Set.of(conversation.userUUIDs).contains(adminUUID)) {
            throw new UserNotFoundException();
        } else {
            conversation.adminUUID = adminUUID;
            conversationDatabase.put(conversationUUID, conversation);
            eventBagHandler.update(conversation);

        }

    }

    public UUID[] getAllUserUUIDs() {
        ArrayList<UUID> uuids = new ArrayList<>(userDatabase.uuids());
        return uuids.toArray(new UUID[0]);
    }

    public Message editMessage(String content, UUID messageUUID) throws MessageNotFoundException {
        //message为更改后的内容
        //生成当前时间
        Message message = getMessage(messageUUID);
        message.content = content;
        message.time = new Date();
        messageDatabase.put(message.uuid, message);
        try {
            eventBagHandler.add(getConversation(message.conversationUUID), message);
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
        }
        return new Message(message);
    }

    public Message getMessage(UUID messageUUID) throws MessageNotFoundException {
        //一条信息
        Message message = messageDatabase.get(messageUUID);
        if (message == null) {
            throw new MessageNotFoundException();
        } else {
            return new Message(message);
        }

    }

    public UUID[] listAllUUID(UUID conversationUUID) throws ConversationNotFoundException {
        Conversation conversation = conversationDatabase.get(conversationUUID);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return conversation.userUUIDs;
        }
        //所有群聊人员的uuid
    }

    /**
     * Gets all message of the given conversation starting from given time.
     **/

    public Message[] getConversationMessages(UUID conversationUUID) throws ConversationNotFoundException {
        //
        ArrayList<Message> messages = new ArrayList<>();
        Conversation conversation = conversationDatabase.get(conversationUUID);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID[] messageUUIDs = conversation.messageUUIDs;
            for (UUID messageUUID :
                    messageUUIDs) {
                try {
                    messages.add(getMessage(messageUUID));
                } catch (MessageNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return messages.toArray(new Message[0]);
        }
    }

    public UUID[] getUserConversations(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException();
        }
        UUID userUUID = user.uuid;
        ArrayList<UUID> uuids = new ArrayList<>();
        for (UUID uuid : conversationDatabase.uuids()) {
            try {
                Conversation conversation = getConversation(uuid);
                for (UUID conversationUserUUID :
                        conversation.userUUIDs) {
                    if (conversationUserUUID.equals(userUUID)) {
                        uuids.add(conversation.uuid);
                    }
                }
            } catch (ConversationNotFoundException e) {
                e.printStackTrace();
            }
        }


        return uuids.toArray(new UUID[0]);
    }

    public Profile editProfile(UUID userUUID, Profile newProfile) throws UserNotFoundException {
        User user = getUser(userUUID);

        user.profile = newProfile;
        userDatabase.put(user.uuid, user);
        eventBagHandler.update(user);

        return newProfile;
    }


}

class EventBagHandler {
    private final HashMap<UUID, EventBag> currentBags;

    public EventBagHandler() {
        currentBags = new HashMap<>();
    }

    public synchronized EventBag flush(UUID userUUID) throws NotLoggedInException {
        EventBag bag = signOut(userUUID);
        try {
            signIn(userUUID);
        } catch (LoggedInException e) {
            e.printStackTrace();
        }
        return bag;
    }

    public synchronized EventBag signOut(UUID userUUID) throws NotLoggedInException {
        EventBag bag = currentBags.remove(userUUID);
        if ((bag) == null) { // if signed in, will remove and return non-null value.
            throw new NotLoggedInException();
        } else {
            return bag;
        }
    }

    public synchronized void signIn(UUID userUUID) throws LoggedInException {
        if (isLoggedIn(userUUID)) {
            throw new LoggedInException();
        } else {
            currentBags.put(userUUID, new EventBag());
        }
    }

    public synchronized boolean isLoggedIn(UUID userUUID) {
        return currentBags.containsKey(userUUID);
    }

    public synchronized void add(User newUser) {
        // everyone should know.
        for (EventBag bag :
                currentBags.values()) {
            bag.putNewUser(newUser);
        }
    }


    public synchronized void add(Conversation newConversation) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(newConversation.userUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putNewConversation(newConversation);
            }
        }
    }

    public synchronized void add(Conversation conversation, Message message) {
        add(conversation.uuid, conversation.userUUIDs, message);
    }

    public synchronized void add(UUID conversationUUID, UUID[] conversationUserUUIDs, Message newMessage) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(conversationUserUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putNewMessage(conversationUUID, newMessage);
            }
        }
    }

    public synchronized void update(User user) {
        // everyone should know.
        for (EventBag bag :
                currentBags.values()) {
            bag.putUpdatedUser(user);
        }

    }

    public synchronized void update(Conversation conversation) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(conversation.userUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putUpdatedConversation(conversation);
            }
        }
    }

    public void addUpdate(Conversation conversation, Message message) {
        update(conversation.userUUIDs, message);
    }

    public synchronized void update(UUID[] conversationUserUUIDs, Message message) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(conversationUserUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putUpdatedMessage(message);
            }
        }
    }

    public void remove(User user) {
        removeUser(user.uuid);
    }

    public synchronized void removeUser(UUID userUUID) {
        // everyone should know.
        for (EventBag bag :
                currentBags.values()) {
            bag.putRemovedUser(userUUID);
        }
    }

    public void remove(Conversation conversation) {
        removeConversation(conversation.uuid, conversation.userUUIDs);
    }

    public synchronized void removeConversation(UUID conversationUUID, UUID[] conversationUserUUIDs) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(conversationUserUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putRemovedConversation(conversationUUID);
            }
        }
    }

    public void remove(Message message, Conversation conversation) {
        removeMessage(message.uuid, conversation.userUUIDs);
    }

    public synchronized void removeMessage(UUID messageUUID, UUID[] conversationUserUUIDs) {
        // everyone in the conversation should know.
        Set<UUID> userUUIDs = Set.of(conversationUserUUIDs);
        for (UUID uuid : currentBags.keySet()) {
            if (userUUIDs.contains(uuid)) {
                currentBags.get(uuid).putRemovedMessage(messageUUID);
            }
        }
    }

}