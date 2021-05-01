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

    public final UUID user_uuid;

    public GetUserRequest(UUID user_uuid) {
        this.user_uuid = user_uuid;
    }

    @Override
    public String toString() {
        return "GetUserRequest{" +
                "user_uuid=" + user_uuid +
                ", uuid=" + uuid +
                '}';
    }
}
