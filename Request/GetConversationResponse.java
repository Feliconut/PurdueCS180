package Request;

import Field.Conversation;

import java.util.UUID;

/**
 * Project5-- GetConversationResponse
 * <p>
 * It is the response to find a conversation by their uuid
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetConversationResponse extends Response {
    public final Conversation conversation;

    public GetConversationResponse(boolean state, String msg, UUID request_uuid, Conversation conversation) {
        super(state, msg, request_uuid);
        this.conversation = conversation;
    }

    @Override
    public String toString() {
        return "GetConversationResponse{" +
                "conversation=" + conversation +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
