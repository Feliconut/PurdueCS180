package Request;

import Field.Conversation;
import Field.Message;

public class PostMessageRequest extends Request
{
    public final Conversation conversation;
    public final Message message;

    public PostMessageRequest(Conversation conversation, Message message) {
        super();
        this.conversation = conversation;
        this.message = message;
    }
}
