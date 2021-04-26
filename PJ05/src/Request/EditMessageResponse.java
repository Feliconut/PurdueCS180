package Request;

import java.util.Date;
import java.util.UUID;

public class EditMessageResponse extends Response {
    public final Date dateEdited;

    public EditMessageResponse(boolean state, String msg, UUID request_uuid, Date date) {
        super(state, msg, request_uuid);
        this.dateEdited = date;
    }
}