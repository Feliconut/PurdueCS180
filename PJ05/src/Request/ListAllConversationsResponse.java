package Request;


import java.util.UUID;

public class ListAllConversationsResponse extends Response {
    public final UUID[] conversation_uuids;

    public ListAllConversationsResponse(boolean state, String msg, UUID request_uuid, UUID[] conversation_uuids) {
        super(state, msg, request_uuid);
        this.conversation_uuids = conversation_uuids;
    }

}
