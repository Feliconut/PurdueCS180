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

    public final UUID conversationUUID;

    public DeleteConversationRequest(UUID conversationUUID) {
        super();
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "DeleteConversationRequest{" + "conversationUUID=" + conversationUUID + ", uuid=" + uuid + '}';
    }
}
