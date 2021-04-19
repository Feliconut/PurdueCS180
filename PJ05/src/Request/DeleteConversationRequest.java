package Request;

import Field.Conversation;

import java.util.UUID;

// definition incomplete
public class DeleteConversationRequest extends Request{
    public final Conversation conversation;
    public final UUID uuid;


    public DeleteConversationRequest(Conversation conversation, UUID uuid) {
        super();
        this.conversation = conversation;
        this.uuid = uuid;
    }
}
