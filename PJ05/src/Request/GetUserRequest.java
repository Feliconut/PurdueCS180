package Request;


import java.util.UUID;

public class GetUserRequest extends Request {

    public final UUID user_uuid;

    public GetUserRequest(UUID user_uuid) {
        this.user_uuid = user_uuid;
    }
}
