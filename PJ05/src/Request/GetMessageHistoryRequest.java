package Request;

import java.util.UUID;

/**
 * Project5-- GetMessageHistoryRequest
 * <p>
 * It is the request find all message of a uuid
 *
 * @author team 84
 * @version 04/30/2021
 */
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
