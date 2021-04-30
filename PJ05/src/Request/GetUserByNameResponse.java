package Request;

import Field.User;

import java.util.UUID;

public class GetUserByNameResponse extends Response {
    public final User user;

    public GetUserByNameResponse(boolean state, String msg, UUID request_uuid, User user) {
        super(state, msg, request_uuid);
        this.user = user;
    }
}
