package Request;

import Exceptions.RequestFailedException;

import java.util.UUID;

public class CreateConversationResponse extends Response {
    UUID conversation_uuid;

    public CreateConversationResponse(boolean state, String msg, UUID request_uuid) {
        super(state, msg, request_uuid);
    }

    public CreateConversationResponse(boolean state, String msg, UUID request_uuid, RequestFailedException exception) {
        super(state, msg, request_uuid, exception);
    }
}
