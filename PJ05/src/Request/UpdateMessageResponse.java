package Request;

import Field.Message;

import java.util.UUID;

public class UpdateMessageResponse extends Response {

    public final Message message;
    public final UUID uuid;

    public UpdateMessageResponse(boolean state, String msg, UUID request_uuid, Message message, UUID uuid) {
        super(state, msg, request_uuid);
        this.message = message;
        this.uuid = uuid;
    }
}
