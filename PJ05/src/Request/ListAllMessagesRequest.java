package Request;

import java.util.Date;
import java.util.UUID;

/**
 * Project5-- ListAllMessagesRequest
 * <p>
 * It is the request to list all messages of a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class ListAllMessagesRequest extends Request {

    public final UUID conversation_uuid;
    public final Date start;
    public final Date end;

    public ListAllMessagesRequest(UUID conversation_Id) {
        this(conversation_Id, null);
    }


    public ListAllMessagesRequest(UUID conversation_Id, Date start) {
        this(conversation_Id, start, null);
    }

    public ListAllMessagesRequest(UUID conversation_Id, Date start, Date end) {
        this.conversation_uuid = conversation_Id;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "ListAllMessagesRequest{" +
                "conversation_uuid=" + conversation_uuid +
                ", start=" + start +
                ", end=" + end +
                ", uuid=" + uuid +
                '}';
    }
}
