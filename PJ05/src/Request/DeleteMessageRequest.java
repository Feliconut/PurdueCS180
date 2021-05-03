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

    public final UUID messageUUID;

    public DeleteMessageRequest(UUID messageUUID) {
        super();
        this.messageUUID = messageUUID;
    }

    @Override
    public String toString() {
        return "DeleteMessageRequest{" +
                "messageUUID=" + messageUUID +
                ", uuid=" + uuid +
                '}';
    }


}
