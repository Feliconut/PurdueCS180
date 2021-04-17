package Request;

public class UpdateMessageRequest extends Request
{
    public final String messageUID;
    public final String newContent;

    public UpdateMessageRequest(String messageUID, String newContent)
    {
        super();
        this.messageUID = messageUID;
        this.newContent = newContent;
    }
}
