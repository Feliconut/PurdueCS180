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

    public final UUID[] userUUIDs;

    public ListAllUsersResponse(boolean state, String msg, UUID requestUUID, UUID[] userUUIDs) {
        super(state, msg, requestUUID);
        this.userUUIDs = userUUIDs;
    }

    @Override
    public String toString() {
        return "ListAllUsersResponse{" +
                "userUUIDs=" + Arrays.toString(userUUIDs) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID +
                ", exception=" + exception +
                '}';
    }
}
