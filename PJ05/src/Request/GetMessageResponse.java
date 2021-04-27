package Request;

import Field.Message;

import java.util.UUID;

public class GetMessageResponse extends Response {
    public final Message message;

    public GetMessageResponse(boolean state, String msg, UUID request_uuid, Message message) {
        super(state, msg, request_uuid);
        this.message = message;
    }
}
