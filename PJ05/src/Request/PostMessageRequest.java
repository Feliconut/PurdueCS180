package Request;

import Field.Message;

import java.util.UUID;

/**
 * Project5-- PostMessageRequest
 * <p>
 * It is the request to post a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class PostMessageRequest extends Request {
    public final UUID conversation_uuid;
    public final Message message;

    public PostMessageRequest(UUID conversation_uuid, Message message) {
        super();
        this.conversation_uuid = conversation_uuid;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostMessageRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", message=" + message +
                ", uuid=" + uuid +
                '}';
    }
}
