package Request;


import java.util.Arrays;
import java.util.UUID;

/**
 * Project5-- ListAllConversationsResponse
 * <p>
 * It is the response to list all conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class ListAllConversationsResponse extends Response {
    public final UUID[] conversationUUIDs;

    public ListAllConversationsResponse(boolean state, String msg, UUID requestUUID, UUID[] conversationUUIDs) {
        super(state, msg, requestUUID);
        this.conversationUUIDs = conversationUUIDs;
    }

    @Override
    public String toString() {
        return "ListAllConversationsResponse{" +
                "conversationUUIDs=" + Arrays.toString(conversationUUIDs) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID +
                ", exception=" + exception +
                '}';
    }

}
