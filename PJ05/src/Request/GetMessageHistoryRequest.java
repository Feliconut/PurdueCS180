package Request;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

public class GetMessageHistoryRequest extends Request {
    public final Array[] arrays;
    public final UUID conversattionId;


    public GetMessageHistoryRequest(Array[] arrays, UUID conversattionId) {
        super();
        this.arrays = arrays;
        this.conversattionId = conversattionId;

    }




}
