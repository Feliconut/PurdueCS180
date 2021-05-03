package Request;

import Field.Message;

import java.util.UUID;

/**
 * Project5-- GetMessageResponse
 * <p>
 * It is the response to find a message
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetMessageResponse extends Response {
    public final Message message;

    public GetMessageResponse(boolean state, String msg, UUID requestUUID, Message message) {
        super(state, msg, requestUUID);
        this.message = message;
    }

    @Override
    public String toString() {
        return "GetMessageResponse{" + "message=" + message + ", uuid=" + uuid + ", state=" + state + ", msg='" + msg +
                '\'' + ", requestUUID=" + requestUUID + ", exception=" + exception + '}';
    }
}
