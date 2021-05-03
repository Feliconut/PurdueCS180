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

    public final UUID conversationUUID;

    public GetMessageHistoryRequest(UUID conversattionId) {
        super();
        this.conversationUUID = conversattionId;

    }

    @Override
    public String toString() {
        return "GetMessageHistoryRequest{" +
                "conversationUUID=" + conversationUUID +
                ", uuid=" + uuid +
                '}';
    }


}
