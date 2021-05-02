package Field;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class EventBag {
    private final CopyOnWriteArrayList<User> new_users;
    private final CopyOnWriteArrayList<Conversation> new_conversations;
    private final HashMap<UUID, CopyOnWriteArrayList<Message>> new_messages;
    private final CopyOnWriteArrayList<User> updated_users;
    private final CopyOnWriteArrayList<Conversation> updated_conversations;
    private final CopyOnWriteArrayList<Message> updated_messages;
    private final CopyOnWriteArrayList<UUID> removed_users;
    private final CopyOnWriteArrayList<UUID> removed_conversations;
    private final CopyOnWriteArrayList<UUID> removed_messages;

    public EventBag() {
        this.new_users = new CopyOnWriteArrayList<>();
        this.new_conversations = new CopyOnWriteArrayList<>();
        this.new_messages = new HashMap<>();
        this.updated_users = new CopyOnWriteArrayList<>();
        this.updated_conversations = new CopyOnWriteArrayList<>();
        this.updated_messages = new CopyOnWriteArrayList<>();
        this.removed_users = new CopyOnWriteArrayList<>();
        this.removed_conversations = new CopyOnWriteArrayList<>();
        this.removed_messages = new CopyOnWriteArrayList<>();
    }

    // adders

    private synchronized static <T extends Storable> void putUpdatedObject(T obj, CopyOnWriteArrayList<T> db, Class<T> clazz) {
        for (Storable storable :
                db) {
            if (storable.uuid.equals(obj.uuid)) {
                db.remove(clazz.cast(storable));
            }
        }
        db.add(clazz.cast(obj));
    }

    public synchronized void putNewUser(User user) {
        new_users.add(user);
    }

    public synchronized void putNewConversation(Conversation conversation) {
        new_conversations.add(conversation);
    }

    public synchronized void putNewMessage(UUID conversation_uuid, Message message) {
        new_messages.putIfAbsent(conversation_uuid, new CopyOnWriteArrayList<>());
        new_messages.get(conversation_uuid).add(message);
    }

    public synchronized void putUpdatedUser(User user) {
        putUpdatedObject(user, updated_users, User.class);
    }

    public synchronized void putUpdatedConversation(Conversation conversation) {
        putUpdatedObject(conversation, updated_conversations, Conversation.class);
    }

    public synchronized void putUpdatedMessage(Message message) {
        putUpdatedObject(message, updated_messages, Message.class);
    }

    public synchronized void putRemovedUser(UUID user_uuid) {
        removed_users.add(user_uuid);
    }

    public synchronized void putRemovedConversation(UUID conversation_uuid) {
        removed_conversations.add(conversation_uuid);
    }

    public synchronized void putRemovedMessage(UUID message_uuid) {
        removed_messages.add(message_uuid);
    }

    // getters of final values

    public User[] getNewUsers() {
        return new_users.toArray(new User[0]);
    }

    public Conversation[] getNewConversations() {
        return new_conversations.toArray(new Conversation[0]);
    }

    public HashMap<UUID, Message[]> getNewMessages() {
        HashMap<UUID, Message[]> res = new HashMap<>();
        for (UUID uuid :
                new_messages.keySet()) {
            res.put(uuid, new_messages.get(uuid).toArray(new Message[0]));
        }
        return res;
    }

    public User[] getUpdatedUsers() {
        return updated_users.toArray(new User[0]);
    }

    public Conversation[] getUpdatedConversations() {
        return updated_conversations.toArray(new Conversation[0]);
    }

    public Message[] getUpdatedMessages() {
        return updated_messages.toArray(new Message[0]);
    }

    public UUID[] getRemovedUsers() {
        return removed_users.toArray(new UUID[0]);
    }

    public UUID[] getRemovedConversations() {
        return removed_conversations.toArray(new UUID[0]);
    }

    public UUID[] getRemovedMessages() {
        return removed_messages.toArray(new UUID[0]);
    }

}
