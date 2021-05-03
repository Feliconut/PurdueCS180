package Request;

import java.util.Date;
import java.util.UUID;

/**
 * Project5-- PostMessageResponse
 * <p>
 * It is the response to post a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class PostMessageResponse extends Response {
    public final Date date;
    public final UUID messageUUID;

    public PostMessageResponse(boolean state, String msg, UUID requestUUID, Date date, UUID messageUUID) {
        super(state, msg, requestUUID);
        this.date = date;
        this.messageUUID = messageUUID;
    }

    @Override
    public String toString() {
        return "PostMessageResponse{" + "date=" + date + ", messageUUID=" + messageUUID + ", uuid=" + uuid +
                ", state=" + state + ", msg='" + msg + '\'' + ", requestUUID=" + requestUUID + ", exception=" +
                exception + '}';
    }
}
