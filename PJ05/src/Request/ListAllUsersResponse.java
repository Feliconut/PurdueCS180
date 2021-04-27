package Request;

import java.util.UUID;

public class ListAllUsersResponse extends Response {

    public final UUID[] user_uuids;

    public ListAllUsersResponse(boolean state, String msg, UUID request_uuid, UUID[] user_uuids) {
        super(state, msg, request_uuid);
        this.user_uuids = user_uuids;
    }
}
