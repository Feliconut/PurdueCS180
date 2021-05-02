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

    public final UUID user_uuid;
    public final UUID conversation_uuid;

    public AddUser2ConversationRequest(UUID user_uuid, UUID conversation_uuid) {
        super();
        this.user_uuid = user_uuid;
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "AddUser2ConversationRequest{" +
                "user_uuid=" + user_uuid +
                ", conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
