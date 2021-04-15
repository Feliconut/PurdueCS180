package Request;

public class UpdateMessageRequest extends BaseClientRequest
{
    public final String messageUID;
    public final String newContent;

    public UpdateMessageRequest(String messageUID, String newContent)
    {
        this.messageUID = messageUID;
        this.newContent = newContent;
    }

    public static UpdateMessageRequest parseRequest(String requestStr) throws RequestParsingException
    {
        //TODO
        return null;
    }

    @Override
    public String toString()
    {
        return "UpdateMessageRequest{" +
                "messageUID='" + messageUID + '\'' +
                ", newContent='" + newContent + '\'' +
                '}';
    }
}
