package Request;

import Field.Message;

import java.util.UUID;

public class GetMessageHistoryResponse extends Response {
    public final Message[] messages;

    public GetMessageHistoryResponse(boolean state, String msg, UUID request_uuid, Message[] messages) {
        super(state, msg, request_uuid);
        this.messages = messages;
    }
}
