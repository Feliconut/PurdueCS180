package Request;

import java.util.Date;
import java.util.UUID;

public class EditMessageResponse extends Response {
    public final Date dateEdited;

    public EditMessageResponse(boolean state, String msg, UUID request_uuid, Date date) {
        super(state, msg, request_uuid);
        this.dateEdited = date;
    }

    @Override
    public String toString() {
        return "EditMessageResponse{" +
                "dateEdited=" + dateEdited +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
