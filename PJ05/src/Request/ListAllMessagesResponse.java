package Request;

import java.util.Arrays;
import java.util.UUID;

public class ListAllMessagesResponse extends Response {
    public final UUID[] user_messages;

    public ListAllMessagesResponse(boolean state, String msg, UUID request_uuid, UUID[] user_messages) {
        super(state, msg, request_uuid);
        this.user_messages = user_messages;
    }

    @Override
    public String toString() {
        return "ListAllMessagesResponse{" +
                "user_messages=" + Arrays.toString(user_messages) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
