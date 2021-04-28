package Request;

import java.util.UUID;

public class GetMessageHistoryRequest extends Request {

    public final UUID conversation_uuId;

    public GetMessageHistoryRequest(UUID conversattionId) {
        super();
        this.conversation_uuId = conversattionId;

    }

    @Override
    public String toString() {
        return "GetMessageHistoryRequest{" +
                "conversation_uuId=" + conversation_uuId +
                ", uuid=" + uuid +
                '}';
    }


}
