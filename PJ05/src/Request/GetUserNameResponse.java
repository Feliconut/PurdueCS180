package Request;

import java.util.UUID;

public class GetUserNameResponse extends Response {
    public final String name;

    public GetUserNameResponse(boolean state, String msg, UUID request_uuid, String name) {
        super(state, msg, request_uuid);
        this.name = name;
    }
}
