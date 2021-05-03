package Request;

import java.util.UUID;

/**
 * Project5-- RenameConversationRequest
 * <p>
 * It is the request to rename the conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class RenameConversationRequest extends Request {
    public final UUID conversationUUID;
    public final String name;

    public RenameConversationRequest(UUID conversationUUID, String name) {

        this.conversationUUID = conversationUUID;
        this.name = name;
    }

    @Override
    public String toString() {
        return "RenameConversationRequest{" +
                "conversationUUID=" + conversationUUID +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
