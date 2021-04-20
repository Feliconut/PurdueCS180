package Request;

import java.util.UUID;

public class GetConversationRequest extends Request {
    public final UUID uuid;


    public GetConversationRequest(UUID uuid) {
        this.uuid = uuid;
    }
}

