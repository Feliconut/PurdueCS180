package Request;

import java.util.Date;
import java.util.UUID;

public class EditMessageRequest extends Request {

    public final UUID messsage_uuid;
    public final String content;
    public final Date date;


    public EditMessageRequest(UUID messsage_uuid, String content, Date date) {
        super();
        this.messsage_uuid = messsage_uuid;
        this.content = content;
        this.date = date;
    }
}
