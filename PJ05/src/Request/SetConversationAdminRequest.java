package Request;

import java.util.UUID;

public class SetConversationAdminRequest extends Request {
    public final UUID user_uuid;
    public final UUID conversation_uuid;

    public SetConversationAdminRequest(UUID user_uuid, UUID conversation_uuid) {
        super();
        this.user_uuid = user_uuid;
        this.conversation_uuid = conversation_uuid;
    }
}
