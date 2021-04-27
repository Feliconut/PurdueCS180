package Request;

import Field.User;

import java.util.UUID;

public class GetUserResponse extends Response {
    public final User user;

    public GetUserResponse(boolean state, String msg, UUID request_uuid, User user) {
        super(state, msg, request_uuid);
        this.user = user;
    }
}
