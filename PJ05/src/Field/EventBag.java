package Field;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBag {
    private final CopyOnWriteArrayList<User> newUsers;
    private final CopyOnWriteArrayList<Conversation> newConversations;
    private final HashMap<UUID, CopyOnWriteArrayList<Message>> newMessages;
    private final CopyOnWriteArrayList<User> updatedUsers;
    private final CopyOnWriteArrayList<Conversation> updatedConversations;
    private final CopyOnWriteArrayList<Message> updatedMessages;
    private final CopyOnWriteArrayList<UUID> removedUsers;
    private final CopyOnWriteArrayList<UUID> removedConversations;
    private final CopyOnWriteArrayList<UUID> removedMessages;

    public EventBag() {
        this.newUsers = new CopyOnWriteArrayList<>();
        this.newConversations = new CopyOnWriteArrayList<>();
        this.newMessages = new HashMap<>();
        this.updatedUsers = new CopyOnWriteArrayList<>();
        this.updatedConversations = new CopyOnWriteArrayList<>();
        this.updatedMessages = new CopyOnWriteArrayList<>();
        this.removedUsers = new CopyOnWriteArrayList<>();
        this.removedConversations = new CopyOnWriteArrayList<>();
        this.removedMessages = new CopyOnWriteArrayList<>();
    }

    // adders

    public synchronized void putNewUser(User user) {
        newUsers.add(user);
    }

    public synchronized void putNewConversation(Conversation conversation) {
        newConversations.add(conversation);
    }

    public synchronized void putNewMessage(UUID conversationUUID, Message message) {
        newMessages.putIfAbsent(conversationUUID, new CopyOnWriteArrayList<>());
        newMessages.get(conversationUUID).add(message);
    }

    public synchronized void putUpdatedUser(User user) {
        putUpdatedObject(user, updatedUsers, User.class);
    }

    private synchronized static <T extends Storable> void putUpdatedObject(T obj, CopyOnWriteArrayList<T> db,
                                                                           Class<T> clazz) {
        for (Storable storable : db) {
            if (storable.uuid.equals(obj.uuid)) {
                db.remove(clazz.cast(storable));
            }
        }
        db.add(clazz.cast(obj));
    }

    public synchronized void putUpdatedConversation(Conversation conversation) {
        putUpdatedObject(conversation, updatedConversations, Conversation.class);
    }

    public synchronized void putUpdatedMessage(Message message) {
        putUpdatedObject(message, updatedMessages, Message.class);
    }

    public synchronized void putRemovedUser(UUID userUUID) {
        removedUsers.add(userUUID);
    }

    public synchronized void putRemovedConversation(UUID conversationUUID) {
        removedConversations.add(conversationUUID);
    }

    public synchronized void putRemovedMessage(UUID messageUUID) {
        removedMessages.add(messageUUID);
    }

    // getters of final values

    public User[] getNewUsers() {
        return newUsers.toArray(new User[0]);
    }

    public Conversation[] getNewConversations() {
        return newConversations.toArray(new Conversation[0]);
    }

    public HashMap<UUID, Message[]> getNewMessages() {
        HashMap<UUID, Message[]> res = new HashMap<>();
        for (UUID uuid : newMessages.keySet()) {
            res.put(uuid, newMessages.get(uuid).toArray(new Message[0]));
        }
        return res;
    }

    public User[] getUpdatedUsers() {
        return updatedUsers.toArray(new User[0]);
    }

    public Conversation[] getUpdatedConversations() {
        return updatedConversations.toArray(new Conversation[0]);
    }

    public Message[] getUpdatedMessages() {
        return updatedMessages.toArray(new Message[0]);
    }

    public UUID[] getRemovedUsers() {
        return removedUsers.toArray(new UUID[0]);
    }

    public UUID[] getRemovedConversations() {
        return removedConversations.toArray(new UUID[0]);
    }

    public UUID[] getRemovedMessages() {
        return removedMessages.toArray(new UUID[0]);
    }

}
