package Request;

import java.util.UUID;

public class DeleteConversationRequest extends Request {

    public final UUID conversation_uuid;

    public DeleteConversationRequest(UUID conversation_uuid) {
        super();
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "DeleteConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
