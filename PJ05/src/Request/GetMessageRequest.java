package Request;

import java.util.UUID;

/**
 * Project5-- GetMessageRequest
 * <p>
 * It is the request to find a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetMessageRequest extends Request {
    public final UUID messageUUID;

    public GetMessageRequest(UUID uuid) {
        super();
        this.messageUUID = uuid;
    }

    @Override
    public String toString() {
        return "GetMessageRequest{" +
                "messageUUID=" + messageUUID +
                ", uuid=" + uuid +
                '}';
    }
}
