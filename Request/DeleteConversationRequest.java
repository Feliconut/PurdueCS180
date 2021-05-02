package Request;

import java.util.UUID;

/**
 * Project5-- DeleteConversationRequest
 * <p>
 * It is the request to delete a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class DeleteConversationRequest extends Request {

    public final UUID conversation_uuid;

    public DeleteConversationRequest(UUID conversation_uuid) {
        super();
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "DeleteConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
