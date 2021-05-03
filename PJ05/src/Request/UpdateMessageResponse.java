package Request;

import Field.Message;

import java.util.UUID;

/**
 * Project5-- UpdateMessageResponse
 * <p>
 * It is the response to update the message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class UpdateMessageResponse extends Response {
    public final Message message;
    public final UUID uuid;

    public UpdateMessageResponse(boolean state, String msg, UUID requestUUID, Message message, UUID uuid) {
        super(state, msg, requestUUID);
        this.message = message;
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "UpdateMessageResponse{" + "uuid=" + uuid + ", state=" + state + ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID + ", exception=" + exception + ", message=" + message + ", uuid=" +
                uuid + '}';
    }
}
