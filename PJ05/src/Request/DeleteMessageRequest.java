package Request;

import java.util.UUID;

public class DeleteMessageRequest extends Request {

    public final UUID message_uuid;

    public DeleteMessageRequest(UUID message_uuid) {
        super();
        this.message_uuid = message_uuid;
    }

    @Override
    public String toString() {
        return "DeleteMessageRequest{" +
                "message_uuid=" + message_uuid +
                ", uuid=" + uuid +
                '}';
    }


}
