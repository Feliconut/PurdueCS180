package Request;

import java.util.UUID;

public class CreateConversationResponse extends Response {
    public final UUID conversation_uuid;

    public CreateConversationResponse(boolean state, String msg, UUID request_uuid, UUID conversation_uuid) {
        super(state, msg, request_uuid);
        this.conversation_uuid = conversation_uuid;
    }
}
