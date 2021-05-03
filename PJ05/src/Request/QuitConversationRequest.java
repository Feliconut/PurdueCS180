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
    public final UUID conversationUUID;

    public QuitConversationRequest(UUID conversationUUID) {
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "QuitConversationRequest{" +
                "conversationUUID=" + conversationUUID +
                ", uuid=" + uuid +
                '}';
    }
}
