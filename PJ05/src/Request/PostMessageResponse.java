package Request;

import java.util.Date;
import java.util.UUID;

public class PostMessageResponse extends Response{
    public final Date date;


    public PostMessageResponse(boolean state, String msg, UUID request_uuid, Date date) {
        super(state, msg, request_uuid);
        this.date = date;
    }
}
