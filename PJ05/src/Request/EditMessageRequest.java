package Request;

import java.util.UUID;

public class EditMessageRequest extends Request {

    public final UUID messsage_uuid;
    public final String content;


    public EditMessageRequest(UUID messsage_uuid, String content) {
        super();
        this.messsage_uuid = messsage_uuid;
        this.content = content;
    }
}
