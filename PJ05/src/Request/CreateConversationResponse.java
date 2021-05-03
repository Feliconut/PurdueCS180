package Request;

import java.util.UUID;

/**
 * Project5-- CreateConversationResponse
 * <p>
 * It is the response to create a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class CreateConversationResponse extends Response {
    public final UUID conversationUUID;

    public CreateConversationResponse(boolean state, String msg, UUID requestUUID, UUID conversationUUID) {
        super(state, msg, requestUUID);
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "CreateConversationResponse{" + "conversationUUID=" + conversationUUID + ", uuid=" + uuid + ", state=" +
                state + ", msg='" + msg + '\'' + ", requestUUID=" + requestUUID + ", exception=" + exception + '}';
    }
}
