package Request;

import java.util.UUID;

public class GetUserNameRequest extends Request {
    public final UUID user_uuid;

    public GetUserNameRequest(UUID user_uuid) {
        this.user_uuid = user_uuid;

    }
}
