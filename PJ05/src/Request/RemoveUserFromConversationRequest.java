package Request;

import java.util.UUID;

/**
 * Project5-- RemoveUserFromConversationRequest
 * <p>
 * It is the request to remove user from conversation(passive)
 *
 * @author team 84
 * @version 04/30/2021
 */
public class RemoveUserFromConversationRequest extends Request {
    public final UUID userUUID;
    public final UUID conversationUUID;

    public RemoveUserFromConversationRequest(UUID userUUID, UUID conversationUUID) {
        this.userUUID = userUUID;
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "RemoveUserFromConversationRequest{" +
                "userUUID=" + userUUID +
                ", conversationUUID=" + conversationUUID +
                ", uuid=" + uuid +
                '}';
    }
}
