package Request;


import java.util.UUID;

/**
 * Project5-- GetUserRequest
 * <p>
 * It is the request to find a user
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetUserRequest extends Request {

    public final UUID userUUID;

    public GetUserRequest(UUID userUUID) {
        this.userUUID = userUUID;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" + "userUUID=" + userUUID + ", uuid=" + uuid + '}';
    }
}
