package Request;

import java.util.UUID;

/**
 * Project5-- SetConversationAdminRequest
 * <p>
 * It is the request to delete a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class DeleteMessageRequest extends Request {

    public final UUID message_uuid;

    public DeleteMessageRequest(UUID message_uuid) {
        super();
        this.message_uuid = message_uuid;
    }

    @Override
    public String toString() {
        return "DeleteMessageRequest{" +
                "message_uuid=" + message_uuid +
                ", uuid=" + uuid +
                '}';
    }


}
