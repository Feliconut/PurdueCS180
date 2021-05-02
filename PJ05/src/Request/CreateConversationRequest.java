package Request;

import java.util.Arrays;
import java.util.UUID;

/**
 * Project5-- CreateConversationRequest
 * <p>
 * It is the request to create a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class CreateConversationRequest extends Request {
    public UUID[] user_uuids;
    public String name;

    public CreateConversationRequest(UUID[] user_uuids, String name) {
        super();
        this.user_uuids = user_uuids;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateConversationRequest{" +
                "user_uuids=" + Arrays.toString(user_uuids) +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
