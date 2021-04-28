package Request;

import java.util.Date;
import java.util.UUID;

public class PostMessageResponse extends Response {
    public final Date date;
    public final UUID message_uuid;

    public PostMessageResponse(boolean state, String msg, UUID request_uuid, Date date, UUID message_uuid) {
        super(state, msg, request_uuid);
        this.date = date;
        this.message_uuid = message_uuid;
    }

    @Override
    public String toString() {
        return "PostMessageResponse{" +
                "date=" + date +
                ", message_uuid=" + message_uuid +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
