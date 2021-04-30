import Exceptions.*;
import Field.*;

import java.util.*;

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

    public User signIn(Credential credential) throws LoggedInException, UserNotFoundException, InvalidPasswordException {
        User user = getUser(credential);
        eventBagHandler.signIn(user.uuid);
        return user;
    }

    public void signOut(UUID user_uuid) throws NotLoggedInException {
        eventBagHandler.signOut(user_uuid);
    }

    public EventBag getEventBag(UUID user_uuid) throws NotLoggedInException {
        return eventBagHandler.flush(user_uuid);
    }

    public User getUser(String name) throws UserNotFoundException {
        for (UUID uuid : userDatabase.uuids()) {
            User user = userDatabase.get(uuid);
            Credential userCredential = user.credential;
            if (userCredential.usrName.equals(name)) {
                return user;
            }
        }

        throw new UserNotFoundException();
    }

    public User getUser(UUID find_uuid) throws UserNotFoundException {
        User user;

        user = userDatabase.get(find_uuid);
        if (user == null) {
            throw new UserNotFoundException();

        } else {
            return user;
        }

    }

    public User getUser(Credential credential) throws UserNotFoundException, InvalidPasswordException {

        User user = getUser(credential.usrName);
        if (user.credential.passwd.equals(credential.passwd)) {
            return user;
        } else {
            throw new InvalidPasswordException();
        }
    }

    public Conversation getConversation(UUID conversation_uuid) throws ConversationNotFoundException {
        Conversation conversation;
        conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return conversation;
        }
    }

    public User addUser(Credential credential, Profile profile) throws UserExistsException {
        //make sure the user not exist
        try {
            getUser(credential.usrName);
            throw new UserExistsException();
        } catch (UserNotFoundException ignored) {

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
        eventBagHandler.remove(message, getConversation(message.conversation_uuid));
    }

    public Conversation deleteConversation(UUID uuid) throws ConversationNotFoundException {
        Conversation conversation;
        conversation = conversationDatabase.remove(uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {

            eventBagHandler.remove(conversation);
            return conversation;
        }
    }


    public Message addMessage(UUID conversation_uuid, UUID sender_uuid, String content) throws ConversationNotFoundException {
        // want to add the a message to it's conversation and return the message
        Date date = new Date();
        Message message = new Message(sender_uuid, date, content, conversation_uuid);
        messageDatabase.put(message.uuid, message);
        Conversation conversation = getConversation(conversation_uuid);
        ArrayList<UUID> modified_uuids = new ArrayList<>(Arrays.asList(conversation.message_uuids));
        modified_uuids.add(message.uuid);
        conversation.message_uuids = modified_uuids.toArray(new UUID[0]);
        conversationDatabase.put(conversation_uuid, conversation);
        eventBagHandler.add(conversation, message);
        eventBagHandler.update(conversation);

        return message;
    }

    public Conversation createConversation(String conversation_name, UUID[] user_uuids, UUID admin_uuid) throws InvalidConversationNameException, UserNotFoundException {
        // add admin to user_uuids
        ArrayList<UUID> user_uuid_array = new ArrayList<>(Arrays.asList(user_uuids));
        user_uuid_array.add(admin_uuid);
        // make distinct
        user_uuids = user_uuid_array.stream().distinct().toArray(UUID[]::new); // make user_uuids distinct
        // check user exists
        for (UUID user_uuid :
                user_uuids) {
            getUser(user_uuid);
        }
        // create conversation
        Conversation conversation = new Conversation(conversation_name, user_uuids, admin_uuid, new UUID[0]);
        conversationDatabase.put(conversation.uuid, conversation);
        eventBagHandler.add(conversation);
        return conversation;
    }

    public void setAdmin(UUID admin_uuid, UUID conversation_uuid) throws AuthorizationException, ConversationNotFoundException, UserNotFoundException {
        Conversation conversation = getConversation(conversation_uuid);

        if (Set.of(conversation.user_uuids).contains(admin_uuid)) {
            conversation.admin_uuid = admin_uuid;
            conversationDatabase.put(conversation_uuid, conversation);
            eventBagHandler.update(conversation);
        } else {
            throw new AuthorizationException();
        }

    }

    public void renameConversation(String conversation_name, UUID conversation_uuid) throws ConversationNotFoundException {
        //rename
        Conversation conversation = getConversation(conversation_uuid);
        conversation.name = conversation_name;
        conversationDatabase.put(conversation_uuid, conversation);
        eventBagHandler.update(conversation);
    }

    public void addUser2Conversation(UUID user_uuid, UUID conversation_uuid) throws UserNotFoundException,
            ConversationNotFoundException {
        //拉user_uuid进入conversation_uuid这个群聊
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        User user = getUser(user_uuid);
        UUID[] uuid = conversation.user_uuids;
        ArrayList<UUID> modified_uuids = new ArrayList<>(Arrays.asList(uuid));
        modified_uuids.add(user_uuid);
        conversation.user_uuids = modified_uuids.toArray(new UUID[0]);
        conversationDatabase.put(conversation_uuid, conversation);
        eventBagHandler.update(conversation);
    }


    public void quitConversation(UUID user_uuid, UUID conversation_uuid) throws ConversationNotFoundException, UserNotFoundException {
        //这个uuid主动离开这个conversation_uuid群聊
        Conversation conversation = getConversation(conversation_uuid);
        User user = getUser(user_uuid);

        ArrayList<UUID> user_uuids = new ArrayList<>(Arrays.asList(conversation.user_uuids));


        if (user_uuids.remove(user_uuid)) {
            conversation.user_uuids = user_uuids.toArray(new UUID[0]);
            conversationDatabase.put(conversation_uuid, conversation);
            eventBagHandler.update(conversation);
        } else {
            throw new UserNotFoundException();
        }

    }

    public UUID[] getAllUserUUIDs() {
        ArrayList<UUID> uuids = new ArrayList<>(userDatabase.uuids());
        return uuids.toArray(new UUID[0]);
    }

    public Message editMessage(String content, UUID message_uuid) throws MessageNotFoundException {
        //message为更改后的内容
        //生成当前时间
        Message message = getMessage(message_uuid);
        message.content = content;
        message.time = new Date();
        messageDatabase.put(message.uuid, message);
//        eventBagHandler.update(message); //TODO find the conversation of the message
        return message;
    }

    public UUID[] listAllUUID(UUID conversation_uuid) throws ConversationNotFoundException {
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return conversation.user_uuids;
        }
        //所有群聊人员的uuid
    }


    /**
     * Gets all message of the given conversation starting from given time.
     **/

    public Message[] getConversationMessages(UUID conversation_uuid) throws ConversationNotFoundException {
        //
        ArrayList<Message> messages = new ArrayList<>();
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID[] message_uuids = conversation.message_uuids;
            for (UUID message_uuid :
                    message_uuids) {
                try {
                    messages.add(getMessage(message_uuid));
                } catch (MessageNotFoundException e) {
                    e.printStackTrace();
                }
            }
            return messages.toArray(new Message[0]);
        }
    }

    public Message getMessage(UUID message_uuid) throws MessageNotFoundException {
        //一条信息
        Message message = messageDatabase.get(message_uuid);
        if (message == null) {
            throw new MessageNotFoundException();
        } else {
            return message;
        }

    }


    public UUID[] getUserConversations(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException();
        }
        UUID user_uuid = user.uuid;
        ArrayList<UUID> uuids = new ArrayList<>();
        for (UUID uuid : conversationDatabase.uuids()) {
            try {
                Conversation conversation = getConversation(uuid);
                UUID[] user_uuids = conversation.user_uuids;
                for (UUID conversation_user_uuid :
                        user_uuids) {
                    if (conversation_user_uuid == user_uuid) {
                        uuids.add(conversation.uuid);
                    }
                }
            } catch (ConversationNotFoundException e) {
                e.printStackTrace();
            }
        }


        return uuids.toArray(new UUID[0]);
    }

    public Profile editProfile(UUID user_uuid, Profile new_profile) throws UserNotFoundException {
        User user = getUser(user_uuid);

        user.profile = new_profile;
        userDatabase.put(user.uuid, user);
        eventBagHandler.update(user);

        return new_profile;
    }


    public User getUser(User user) throws UserNotFoundException {
        if (user == null) {
            throw new UserNotFoundException();
        } else {
            return getUser(user.uuid);
        }
    }
}

class EventBagHandler {
    private final HashMap<UUID, EventBag> currentBags;

    public EventBagHandler() {
        currentBags = new HashMap<>();
    }

    public synchronized boolean isLoggedIn(UUID user_uuid) {
        return currentBags.containsKey(user_uuid);
    }

    public synchronized void signIn(UUID user_uuid) throws LoggedInException {
        if (isLoggedIn(user_uuid)) {
            throw new LoggedInException();
        } else {
            currentBags.put(user_uuid, new EventBag());
        }
    }

    public synchronized EventBag signOut(UUID user_uuid) throws NotLoggedInException {
        EventBag bag = currentBags.remove(user_uuid);
        if ((bag) == null) { // if signed in, will remove and return non-null value.
            throw new NotLoggedInException();
        } else {
            return bag;
        }
    }

    public synchronized EventBag flush(UUID user_uuid) throws NotLoggedInException {
        EventBag bag = signOut(user_uuid);
        try {
            signIn(user_uuid);
        } catch (LoggedInException e) {
            e.printStackTrace();
        }
        return bag;
    }

    public synchronized void add(User new_user) {
        // everyone should know.
        for (EventBag bag :
                currentBags.values()) {
            bag.putNewUser(new_user);
        }
    }


    public synchronized void add(Conversation new_conversation) {
        // everyone in the conversation should know.
        Set<UUID> user_uuids = Set.of(new_conversation.user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putNewConversation(new_conversation);
            }
        }
    }

    public synchronized void add(UUID conversation_uuid, UUID[] conversation_user_uuids, Message new_message) {
        // everyone in the conversation should know.
        Set<UUID> user_uuids = Set.of(conversation_user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putNewMessage(conversation_uuid, new_message);
            }
        }
    }

    public synchronized void add(Conversation conversation, Message message) {
        add(conversation.uuid, conversation.user_uuids, message);
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
        Set<UUID> user_uuids = Set.of(conversation.user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putUpdatedConversation(conversation);
            }
        }
    }

    public synchronized void update(UUID[] conversation_user_uuids, Message message) {
        // everyone in the conversation should know.
        Set<UUID> user_uuids = Set.of(conversation_user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putUpdatedMessage(message);
            }
        }
    }

    public void addUpdate(Conversation conversation, Message message) {
        update(conversation.user_uuids, message);
    }

    public synchronized void removeUser(UUID user_uuid) {
        // everyone should know.
        for (EventBag bag :
                currentBags.values()) {
            bag.putRemovedUser(user_uuid);
        }
    }

    public void remove(User user) {
        removeUser(user.uuid);
    }

    public synchronized void removeConversation(UUID conversation_uuid, UUID[] conversation_user_uuids) {
        // everyone in the conversation should know.
        Set<UUID> user_uuids = Set.of(conversation_user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putRemovedConversation(conversation_uuid);
            }
        }
    }

    public void remove(Conversation conversation) {
        removeConversation(conversation.uuid, conversation.user_uuids);
    }

    public synchronized void removeMessage(UUID message_uuid, UUID[] conversation_user_uuids) {
        // everyone in the conversation should know.
        Set<UUID> user_uuids = Set.of(conversation_user_uuids);
        for (UUID uuid : currentBags.keySet()) {
            if (user_uuids.contains(uuid)) {
                currentBags.get(uuid).putRemovedMessage(message_uuid);
            }
        }
    }

    public void remove(Message message, Conversation conversation) {
        removeMessage(message.uuid, conversation.user_uuids);
    }

}