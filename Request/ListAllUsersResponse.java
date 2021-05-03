package Request;

import java.util.Arrays;
import java.util.UUID;

/**
 * Project5-- ListAllUsersResponse
 * <p>
 * It is the response to list all user of a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class ListAllUsersResponse extends Response {

    public final UUID[] user_uuids;

    public ListAllUsersResponse(boolean state, String msg, UUID request_uuid, UUID[] user_uuids) {
        super(state, msg, request_uuid);
        this.user_uuids = user_uuids;
    }

    @Override
    public String toString() {
        return "ListAllUsersResponse{" +
                "user_uuids=" + Arrays.toString(user_uuids) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
