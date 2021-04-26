package Request;

import Field.Conversation;
import Field.EventBag;
import Field.Message;
import Field.User;

import java.util.HashMap;
import java.util.UUID;

public class GetEventFeedResponse extends Response {
    public final User[] new_users;
    public final Conversation[] new_conversations;
    public final HashMap<UUID, Message[]> new_messages;
    public final User[] updated_users;
    public final Conversation[] updated_conversations;
    public final Message[] updated_messages;
    public final UUID[] removed_users;
    public final UUID[] removed_conversations;
    public final UUID[] removed_messages;

    public GetEventFeedResponse(boolean state, String msg, UUID request_uuid, User[] new_users,
                                Conversation[] new_conversations, HashMap<UUID, Message[]> new_messages,
                                User[] updated_users, Conversation[] updated_conversations,
                                Message[] updated_messages, UUID[] removed_users,
                                UUID[] removed_conversations, UUID[] removed_messages) {
        super(state, msg, request_uuid);
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

    public GetEventFeedResponse(boolean state, String msg, UUID request_uuid, EventBag bag) {

        super(state, msg, request_uuid);
        this.new_users = bag.getNewUsers();
        this.new_conversations = bag.getNewConversations();
        this.new_messages = bag.getNewMessages();
        this.updated_users = bag.getUpdatedUsers();
        this.updated_conversations = bag.getUpdatedConversations();
        this.updated_messages = bag.getUpdatedMessages();
        this.removed_users = bag.getRemovedUsers();
        this.removed_conversations = bag.getRemovedConversations();
        this.removed_messages = bag.getRemovedMessages();
    }

}
