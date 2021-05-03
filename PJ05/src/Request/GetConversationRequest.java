package Request;

import java.util.UUID;

/**
 * Project5-- GetConversationRequest
 * <p>
 * It is the request to find a conversation by their uuid
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetConversationRequest extends Request {
    public final UUID conversation_uuid;

    public GetConversationRequest(UUID conversation_uuid) {
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "GetConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}

