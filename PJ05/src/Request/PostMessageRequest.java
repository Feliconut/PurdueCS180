package Request;

import Field.Conversation;
import Field.Message;

public class PostMessageRequest extends Request {
    public final Conversation conversation_uuid;
    public final Message message;

    public PostMessageRequest(Conversation conversation_uuid, Message message) {
        super();
        this.conversation_uuid = conversation_uuid;
        this.message = message;
    }
}
