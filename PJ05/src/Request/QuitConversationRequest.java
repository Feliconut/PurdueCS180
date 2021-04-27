package Request;

import java.util.UUID;

public class QuitConversationRequest extends Request {
    public final UUID conversation_uuid;

    public QuitConversationRequest(UUID conversation_uuid) {
        this.conversation_uuid = conversation_uuid;
    }

    @Override
    public String toString() {
        return "QuitConversationRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
