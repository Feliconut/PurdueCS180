package Request;

import java.util.UUID;

public class AddUser2ConversationRequest extends Request {

    public final UUID user_uuid;
    public final UUID conversation_uuid;

    public AddUser2ConversationRequest(UUID user_uuid, UUID conversation_uuid) {
        super();
        this.user_uuid = user_uuid;
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "AddUser2ConversationRequest{" +
                "user_uuid=" + user_uuid +
                ", conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
