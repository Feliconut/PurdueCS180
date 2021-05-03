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
    public final UUID conversationUUID;

    public GetConversationRequest(UUID conversationUUID) {
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "GetConversationRequest{" + "conversationUUID=" + conversationUUID + ", uuid=" + uuid + '}';
    }
}

