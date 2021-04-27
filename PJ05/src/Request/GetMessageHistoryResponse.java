package Request;

import Field.Message;

import java.util.Arrays;
import java.util.UUID;

public class GetMessageHistoryResponse extends Response {
    public final Message[] messages;

    public GetMessageHistoryResponse(boolean state, String msg, UUID request_uuid, Message[] messages) {
        super(state, msg, request_uuid);
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "GetMessageHistoryResponse{" +
                "messages=" + Arrays.toString(messages) +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
