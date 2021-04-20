package Request;

import Field.Message;

import java.util.UUID;

public class ListAllMessageResponse extends Response {
    public final Message[] user_messages;

    public ListAllMessageResponse(boolean state, String msg, UUID request_uuid, Message[] user_messages) {
        super(state, msg, request_uuid);
        this.user_messages = user_messages;
    }
}
