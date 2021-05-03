package Request;

/**
 * Project5-- GetEventFeedRequest
 * <p>
 * It is the request to find all the message event during a time period
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetEventFeedRequest extends Request {

    public GetEventFeedRequest() {
        super();
    }

    @Override
    public String toString() {
        return "GetEventFeedRequest{" + ", uuid=" + uuid + '}';
    }
}
