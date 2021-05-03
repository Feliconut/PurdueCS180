package Request;

import java.util.UUID;

/**
 * Project5-- RegisterResponse
 * <p>
 * It is the response of when register
 *
 * @author team 84
 * @version 04/30/2021
 */
public class RegisterResponse extends Response {
    public final UUID user_uuid;

    public RegisterResponse(boolean state, String msg, UUID request_uuid, UUID user_uuid) {
        super(state, msg, request_uuid);
        this.user_uuid = user_uuid;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "user_uuid=" + user_uuid +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
