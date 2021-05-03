package Request;

import Field.Conversation;
import Field.EventBag;
import Field.Message;
import Field.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

/**
 * Project5-- GetEventFeedResponse
 * <p>
 * It is the response to find all the message event during a time period
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetEventFeedResponse extends Response {
    public final User[] newUsers;
    public final Conversation[] newConversations;
    public final HashMap<UUID, Message[]> newMessages;
    public final User[] updatedUsers;
    public final Conversation[] updatedConversations;
    public final Message[] updatedMessages;
    public final UUID[] removedUsers;
    public final UUID[] removedConversations;
    public final UUID[] removedMessages;

    public GetEventFeedResponse(boolean state, String msg, UUID requestUUID, User[] newUsers,
                                Conversation[] newConversations, HashMap<UUID, Message[]> newMessages,
                                User[] updatedUsers, Conversation[] updatedConversations,
                                Message[] updatedMessages, UUID[] removedUsers,
                                UUID[] removedConversations, UUID[] removedMessages) {
        super(state, msg, requestUUID);
        this.newUsers = newUsers;
        this.newConversations = newConversations;
        this.newMessages = newMessages;
        this.updatedUsers = updatedUsers;
        this.updatedConversations = updatedConversations;
        this.updatedMessages = updatedMessages;
        this.removedUsers = removedUsers;
        this.removedConversations = removedConversations;
        this.removedMessages = removedMessages;
    }

    public GetEventFeedResponse(boolean state, String msg, UUID requestUUID, EventBag bag) {

        super(state, msg, requestUUID);
        this.newUsers = bag.getNewUsers();
        this.newConversations = bag.getNewConversations();
        this.newMessages = bag.getNewMessages();
        this.updatedUsers = bag.getUpdatedUsers();
        this.updatedConversations = bag.getUpdatedConversations();
        this.updatedMessages = bag.getUpdatedMessages();
        this.removedUsers = bag.getRemovedUsers();
        this.removedConversations = bag.getRemovedConversations();
        this.removedMessages = bag.getRemovedMessages();
    }


    @Override
    public String toString() {
        return "GetEventFeedResponse{" +
                "users=" + Arrays.toString(newUsers) +
                ", conversations=" + Arrays.toString(newConversations) +
                ", newMessages=" + newMessages +
                ", updatedUsers=" + Arrays.toString(updatedUsers) +
                ", updatedConversations=" + Arrays.toString(updatedConversations) +
                ", updatedMessages=" + Arrays.toString(updatedMessages) +
                ", removedUsers=" + Arrays.toString(removedUsers) +
                ", removedConversations=" + Arrays.toString(removedConversations) +
                ", removedMessages=" + Arrays.toString(removedMessages) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID +
                ", exception=" + exception +
                '}';
    }
}
