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

    public final UUID conversationUUID;
    public final Date start;
    public final Date end;

    public ListAllMessagesRequest(UUID conversationId) {
        this(conversationId, null);
    }


    public ListAllMessagesRequest(UUID conversationId, Date start) {
        this(conversationId, start, null);
    }

    public ListAllMessagesRequest(UUID conversationId, Date start, Date end) {
        this.conversationUUID = conversationId;
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "ListAllMessagesRequest{" + "conversationUUID=" + conversationUUID + ", start=" + start + ", end=" +
                end + ", uuid=" + uuid + '}';
    }
}
