package Request;

import Field.Message;

import java.util.UUID;

public class ListAllMessagesResponse extends Response {
    public final UUID[] user_messages;

    public ListAllMessagesResponse(boolean state, String msg, UUID request_uuid, UUID[] user_messages) {
        super(state, msg, request_uuid);
        this.user_messages = user_messages;
    }
}
