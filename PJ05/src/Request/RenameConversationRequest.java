package Request;

import java.util.UUID;

public class RenameConversationRequest extends Request {
    public final UUID conversation_uuid;
    public final String name;


    public RenameConversationRequest(UUID conversation_uuid, String name) {

        this.conversation_uuid = conversation_uuid;
        this.name = name;
    }
}
