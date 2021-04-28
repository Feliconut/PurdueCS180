package Request;

import java.util.Date;

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
