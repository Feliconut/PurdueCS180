package Request;

import java.util.UUID;

public class SetConversationAdminRequest extends Request {
    public final UUID admin_uuid;
    public final UUID conversation_uuid;

    public SetConversationAdminRequest(UUID admin_uuid, UUID conversation_uuid) {
        super();
        this.admin_uuid = admin_uuid;
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "SetConversationAdminRequest{" +
                "uuid=" + uuid +
                ", admin_uuid=" + admin_uuid +
                ", conversation_uuid=" + conversation_uuid +
                '}';
    }
}
