package Request;

import java.util.UUID;

public class GetConversationRequest extends Request {
    public final UUID conversation_uuid;


    public GetConversationRequest(UUID conversation_uuid) {
        this.conversation_uuid = conversation_uuid;
    }
}

