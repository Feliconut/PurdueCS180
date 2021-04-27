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

    @Override
    public String toString() {
        return "UpdateMessageResponse{" +
                "uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                ", message=" + message +
                ", uuid=" + uuid +
                '}';
    }
}
