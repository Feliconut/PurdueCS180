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
    public final UUID userUUID;

    public RegisterResponse(boolean state, String msg, UUID requestUUID, UUID userUUID) {
        super(state, msg, requestUUID);
        this.userUUID = userUUID;
    }

    @Override
    public String toString() {
        return "RegisterResponse{" +
                "userUUID=" + userUUID +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID +
                ", exception=" + exception +
                '}';
    }
}
