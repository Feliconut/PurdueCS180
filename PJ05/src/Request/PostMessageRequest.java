package Request;

import Field.Message;

public class PostMessageRequest extends BaseClientRequest
{
    public final Message message;

    public PostMessageRequest(Message message)
    {
        super();
        this.message = message;
    }

    public static PostMessageRequest parseRequest(String requestStr) throws RequestParsingException
    {
        //TODO
        return null;
    }

    @Override
    public String toString()
    {
        return null;
    }
}
