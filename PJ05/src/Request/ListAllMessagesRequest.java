package Request;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.UUID;

public class ListAllMessagesRequest extends Request {
    public final Array[] arrays;
    public final UUID conversattionId;


    public ListAllMessagesRequest(Array[] arrays, UUID conversattionId) {
        this.arrays = arrays;
        this.conversattionId = conversattionId;

    }



}
