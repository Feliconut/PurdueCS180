package Request;

import java.util.UUID;

public class GetMessageRequest extends Request {
    public final UUID message_uuid;

    public GetMessageRequest(UUID uuid) {
        super();
        this.message_uuid = uuid;
    }

    @Override
    public String toString() {
        return "GetMessageRequest{" +
                "message_uuid=" + message_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
