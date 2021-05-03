package Request;

/**
 * Project5-- UpdateMessageRequest
 * <p>
 * It is the request to update the message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class UpdateMessageRequest extends Request {
    public final String messageUID;
    public final String newContent;

    public UpdateMessageRequest(String messageUID, String newContent) {
        super();
        this.messageUID = messageUID;
        this.newContent = newContent;
    }

    @Override
    public String toString() {
        return "UpdateMessageRequest{" + "uuid=" + uuid + ", messageUID='" + messageUID + '\'' + ", newContent='" +
                newContent + '\'' + '}';
    }
}
