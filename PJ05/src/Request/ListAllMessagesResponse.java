package Request;

import java.util.Arrays;
import java.util.UUID;

/**
 * Project5-- ListAllMessagesResponse
 * <p>
 * It is the response to list all message in a conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class ListAllMessagesResponse extends Response {
    public final UUID[] userMessages;

    public ListAllMessagesResponse(boolean state, String msg, UUID requestUUID, UUID[] userMessages) {
        super(state, msg, requestUUID);
        this.userMessages = userMessages;
    }

    @Override
    public String toString() {
        return "ListAllMessagesResponse{" + "userMessages=" + Arrays.toString(userMessages) + ", uuid=" + uuid +
                ", state=" + state + ", msg='" + msg + '\'' + ", requestUUID=" + requestUUID + ", exception=" +
                exception + '}';
    }
}
