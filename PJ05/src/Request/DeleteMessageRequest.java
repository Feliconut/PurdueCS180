package Request;

import java.util.UUID;

public class DeleteMessageRequest extends Request {

    public final UUID message_uuid;

    public DeleteMessageRequest(UUID message_uuid) {
        super();
        this.message_uuid = message_uuid;
    }


}
