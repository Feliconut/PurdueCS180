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
    public final UUID user_uuid;
    public final UUID conversation_uuid;

    public RemoveUserFromConversationRequest(UUID user_uuid, UUID conversation_uuid) {
        this.user_uuid = user_uuid;
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "RemoveUserFromConversationRequest{" +
                "user_uuid=" + user_uuid +
                ", conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
