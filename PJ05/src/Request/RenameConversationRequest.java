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
    public final UUID conversation_uuid;
    public final String name;

    public RenameConversationRequest(UUID conversation_uuid, String name) {

        this.conversation_uuid = conversation_uuid;
        this.name = name;
    }

    @Override
    public String toString() {
        return "RenameConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
