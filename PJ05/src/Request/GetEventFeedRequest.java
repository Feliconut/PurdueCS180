package Request;

import java.util.Date;

/**
 * Project5-- GetEventFeedRequest
 * <p>
 * It is the request to find all the message event during a time period
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetEventFeedRequest extends Request {
    public final Date dateForm;

    public GetEventFeedRequest(Date dateForm) {
        this.dateForm = dateForm;
    }

    @Override
    public String toString() {
        return "GetEventFeedRequest{" +
                "dateForm=" + dateForm +
                ", uuid=" + uuid +
                '}';
    }
}
