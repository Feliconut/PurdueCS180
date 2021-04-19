package Request;

import java.util.UUID;

public class ListAllUsersRequest extends Request{
    public final UUID uuid;


    public ListAllUsersRequest(UUID uuid) {
        super();
        this.uuid = uuid;
    }
}
