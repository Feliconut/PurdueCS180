package Request;


import java.util.Arrays;
import java.util.UUID;

public class ListAllConversationsResponse extends Response {
    public final UUID[] conversation_uuids;

    public ListAllConversationsResponse(boolean state, String msg, UUID request_uuid, UUID[] conversation_uuids) {
        super(state, msg, request_uuid);
        this.conversation_uuids = conversation_uuids;
    }

    @Override
    public String toString() {
        return "ListAllConversationsResponse{" +
                "conversation_uuids=" + Arrays.toString(conversation_uuids) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }

}
