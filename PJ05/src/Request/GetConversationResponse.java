package Request;

import Field.Conversation;

import java.util.UUID;

public class GetConversationResponse extends Response {
    public final Conversation conversation;

    public GetConversationResponse(boolean state, String msg, UUID request_uuid, Conversation conversation) {
        super(state, msg, request_uuid);
        this.conversation = conversation;
    }
}
