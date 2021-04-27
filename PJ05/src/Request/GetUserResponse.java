package Request;

import Field.User;

import java.util.UUID;

public class GetUserResponse extends Response {
    public final User user;

    public GetUserResponse(boolean state, String msg, UUID request_uuid, User user) {
        super(state, msg, request_uuid);
        this.user = user;
    }

    @Override
    public String toString() {
        return "GetUserResponse{" +
                "user=" + user +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
