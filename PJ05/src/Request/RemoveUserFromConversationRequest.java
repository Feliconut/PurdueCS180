package Request;

import java.util.UUID;

public class RemoveUserFromConversationRequest extends Request {
    public final UUID user_uuid;
    public final UUID conversation_uuid;

    public RemoveUserFromConversationRequest(UUID user_uuid, UUID conversation_uuid) {
        this.user_uuid = user_uuid;
        this.conversation_uuid = conversation_uuid;
    }
}
