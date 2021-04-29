package Request;

import Exceptions.RequestFailedException;

import java.util.UUID;

public class AuthenticateResponse extends Response {
    public final UUID user_uuid;

    public AuthenticateResponse(boolean state, String msg, UUID request_uuid, UUID user_uuid) {
        super(state, msg, request_uuid);
        this.user_uuid = user_uuid;
    }

    public AuthenticateResponse(boolean state, String msg, UUID request_uuid, RequestFailedException exception, UUID user_uuid) {
        super(state, msg, request_uuid, exception);
        this.user_uuid = user_uuid;
    }

    @Override
    public String toString() {
        return "AuthenticateResponse{" +
                "user_uuid=" + user_uuid +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", request_uuid=" + request_uuid +
                ", exception=" + exception +
                '}';
    }
}
