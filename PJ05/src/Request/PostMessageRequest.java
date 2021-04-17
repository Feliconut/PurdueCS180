package Request;

import Field.Message;

public class PostMessageRequest extends Request
{
    public final Message message;

    public PostMessageRequest(Message message)
    {
        super();
        this.message = message;
    }
}
