package Request;

import java.util.UUID;

public class CreateConversationRequest extends Request {
    public final UUID[] user_uuids;
    public final String name;

    public CreateConversationRequest(UUID[] user_uuids, String name) {
        super();
        this.user_uuids = user_uuids;
        this.name = name;
    }
}
