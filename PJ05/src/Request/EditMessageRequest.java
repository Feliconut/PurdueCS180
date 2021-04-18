package Request;

import java.util.UUID;

public class EditMessageRequest extends Request {

    public final UUID uuid;
    public final String content;


    public EditMessageRequest(UUID uuid, String content) {
        super();
        this.uuid = uuid;
        this.content = content;
    }
}
