package Request;

import java.util.UUID;

/**
 * Project5-- GetUserNameResponse
 * <p>
 * It is the request to get a user name
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetUserNameResponse extends Response {
    public final UUID user_uuid;

    public GetUserNameResponse(boolean state, String msg, UUID request_uuid, UUID user_uuid) {
        super(state, msg, request_uuid);
        this.user_uuid = user_uuid;
    }
}
