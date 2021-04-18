package Request;

import java.util.UUID;

public class GetMessageRequest extends Request{
    public final UUID uuid;

    public GetMessageRequest(UUID uuid) {
        this.uuid = uuid;
    }
}
