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
            if (storable.uuid == obj.uuid) {
                db.remove(clazz.cast(storable));
            }
        }
    }

    public void putNewUser(User user) {
        new_users.add(user);
    }

    public void putNewConversation(Conversation conversation) {
        new_conversations.add(conversation);
    }

    public void putNewMessage(UUID conversation_uuid, Message message) {
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
        return (User[]) new_users.toArray();
    }

    public Conversation[] getNewConversations() {
        return (Conversation[]) new_conversations.toArray();
    }

    public HashMap<UUID, Message[]> getNewMessages() {
        HashMap<UUID, Message[]> res = new HashMap<>();
        for (UUID uuid :
                new_messages.keySet()) {
            res.put(uuid, (Message[]) new_messages.get(uuid).toArray());
        }
        return res;
    }

    public User[] getUpdatedUsers() {
        return (User[]) updated_users.toArray();
    }

    public Conversation[] getUpdatedConversations() {
        return (Conversation[]) updated_conversations.toArray();
    }

    public Message[] getUpdatedMessages() {
        return (Message[]) updated_messages.toArray();
    }

    public UUID[] getRemovedUsers() {
        return (UUID[]) removed_users.toArray();
    }

    public UUID[] getRemovedConversations() {
        return (UUID[]) removed_conversations.toArray();
    }

    public UUID[] getRemovedMessages() {
        return (UUID[]) removed_messages.toArray();
    }

}
