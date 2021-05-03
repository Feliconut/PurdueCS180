package Request;

import java.util.UUID;

/**
 * Project5-- AddUser2ConversationRequest
 * <p>
 * It is the request to add user to a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class AddUser2ConversationRequest extends Request {

    public final UUID userUUID;
    public final UUID conversationUUID;

    public AddUser2ConversationRequest(UUID userUUID, UUID conversationUUID) {
        super();
        this.userUUID = userUUID;
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "AddUser2ConversationRequest{" +
                "userUUID=" + userUUID +
                ", conversationUUID=" + conversationUUID +
                ", uuid=" + uuid +
                '}';
    }
}
