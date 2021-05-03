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
    public final UUID message_uuid;

    public GetMessageRequest(UUID uuid) {
        super();
        this.message_uuid = uuid;
    }

    @Override
    public String toString() {
        return "GetMessageRequest{" +
                "message_uuid=" + message_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
