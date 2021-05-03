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
    public final UUID conversationUUID;
    public final Message message;

    public PostMessageRequest(UUID conversationUUID, Message message) {
        super();
        this.conversationUUID = conversationUUID;
        this.message = message;
    }

    @Override
    public String toString() {
        return "PostMessageRequest{" +
                "conversationUUID=" + conversationUUID +
                ", message=" + message +
                ", uuid=" + uuid +
                '}';
    }
}
