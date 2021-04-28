package Request;

import java.util.Arrays;
import java.util.UUID;

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
