package Request;

import Field.Message;

import java.util.Arrays;
import java.util.UUID;

/**
 * Project5-- GetMessageHistoryResponse
 * <p>
 * It is the response to get all the message of a uuid
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetMessageHistoryResponse extends Response {
    public final Message[] messages;

    public GetMessageHistoryResponse(boolean state, String msg, UUID requestUUID, Message[] messages) {
        super(state, msg, requestUUID);
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "GetMessageHistoryResponse{" + "messages=" + Arrays.toString(messages) + ", uuid=" + uuid + ", state=" +
                state + ", msg='" + msg + '\'' + ", requestUUID=" + requestUUID + ", exception=" + exception + '}';
    }
}
