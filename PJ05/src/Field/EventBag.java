package Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class EventBag {
    private final ArrayList<User> new_users;
    private final ArrayList<Conversation> new_conversations;
    private final HashMap<UUID, ArrayList<Message>> new_messages;
    private final ArrayList<User> updated_users;
    private final ArrayList<Conversation> updated_conversations;
    private final ArrayList<Message> updated_messages;
    private final ArrayList<UUID> removed_users;
    private final ArrayList<UUID> removed_conversations;
    private final ArrayList<UUID> removed_messages;

    public EventBag() {
        this.new_users = new ArrayList<>();
        this.new_conversations = new ArrayList<>();
        this.new_messages = new HashMap<>();
        this.updated_users = new ArrayList<>();
        this.updated_conversations = new ArrayList<>();
        this.updated_messages = new ArrayList<>();
        this.removed_users = new ArrayList<>();
        this.removed_conversations = new ArrayList<>();
        this.removed_messages = new ArrayList<>();
    }

    public EventBag(ArrayList<User> new_users,
                    ArrayList<Conversation> new_conversations,
                    HashMap<UUID, ArrayList<Message>> new_messages,
                    ArrayList<User> updated_users,
                    ArrayList<Conversation> updated_conversations,
                    ArrayList<Message> updated_messages,
                    ArrayList<UUID> removed_users,
                    ArrayList<UUID> removed_conversations,
                    ArrayList<UUID> removed_messages) {
        this.new_users = new_users;
        this.new_conversations = new_conversations;
        this.new_messages = new_messages;
        this.updated_users = updated_users;
        this.updated_conversations = updated_conversations;
        this.updated_messages = updated_messages;
        this.removed_users = removed_users;
        this.removed_conversations = removed_conversations;
        this.removed_messages = removed_messages;
    }

    // adders

    private synchronized static <T extends Storable> void putUpdatedObject(T obj, ArrayList<T> db, Class<T> clazz) {
        for (Storable storable :
                db) {
            if (storable.uuid.equals(obj.uuid)) {
                db.remove(clazz.cast(storable));
            }
        }
        db.add(clazz.cast(obj));
    }

    public void putNewUser(User user) {
        new_users.add(user);
    }

    public void putNewConversation(Conversation conversation) {
        new_conversations.add(conversation);
    }

    public void putNewMessage(UUID conversation_uuid, Message message) {
        new_messages.putIfAbsent(conversation_uuid, new ArrayList<>());
        new_messages.get(conversation_uuid).add(message);
    }

    public void putUpdatedUser(User user) {
        putUpdatedObject(user, updated_users, User.class);
    }

    public void putUpdatedConversation(Conversation conversation) {
        putUpdatedObject(conversation, updated_conversations, Conversation.class);
    }

    public void putUpdatedMessage(Message message) {
        putUpdatedObject(message, updated_messages, Message.class);
    }

    public void putRemovedUser(UUID user_uuid) {
        removed_users.add(user_uuid);
    }

    public void putRemovedConversation(UUID conversation_uuid) {
        removed_conversations.add(conversation_uuid);
    }

    public void putRemovedMessage(UUID message_uuid) {
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
