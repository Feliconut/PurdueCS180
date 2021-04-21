package Request;

import Field.Conversation;
import Field.Message;
import Field.User;

import java.util.HashMap;
import java.util.UUID;

public class GetEventFeedResponse extends Response {
    public final User[] users;
    public final Conversation[] conversations;
    public final HashMap<UUID, Message> new_messages;
    public final User[] updated_users;
    public final Conversation[] updated_conversations;
    public final Message[] updated_messages;
    public final UUID[] removed_users;
    public final UUID[] removed_conversations;
    public final UUID[] removed_messages;

    public GetEventFeedResponse(boolean state, String msg, UUID request_uuid, User[] users,
                                Conversation[] conversations, HashMap<UUID, Message> new_messages,
                                User[] updated_users, Conversation[] updated_conversations,
                                Message[] updated_messages, UUID[] removed_users,
                                UUID[] removed_conversations, UUID[] removed_messages) {
        super(state, msg, request_uuid);
        this.users = users;
        this.conversations = conversations;
        this.new_messages = new_messages;
        this.updated_users = updated_users;
        this.updated_conversations = updated_conversations;
        this.updated_messages = updated_messages;
        this.removed_users = removed_users;
        this.removed_conversations = removed_conversations;
        this.removed_messages = removed_messages;
    }
}
