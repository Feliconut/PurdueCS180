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
    public UUID[] userUUIDs;
    public String name;

    public CreateConversationRequest(UUID[] userUUIDs, String name) {
        super();
        this.userUUIDs = userUUIDs;
        this.name = name;
    }

    @Override
    public String toString() {
        return "CreateConversationRequest{" +
                "userUUIDs=" + Arrays.toString(userUUIDs) +
                ", name='" + name + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
