package Request;

import java.util.UUID;

public class GetUserNameResponse extends Response {
    public final UUID user_uuid;

    public GetUserNameResponse(boolean state, String msg, UUID request_uuid, UUID user_uuid) {
        super(state, msg, request_uuid);
        this.user_uuid = user_uuid;
    }
}
