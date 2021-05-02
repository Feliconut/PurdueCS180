package Request;

import java.util.UUID;

/**
 * Project5-- QuitConversationRequest
 * <p>
 * It is the request for user to quit the conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class QuitConversationRequest extends Request {
    public final UUID conversation_uuid;

    public QuitConversationRequest(UUID conversation_uuid) {
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "QuitConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
