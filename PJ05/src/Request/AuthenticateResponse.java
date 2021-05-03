package Request;

import Exceptions.*;

import java.util.*;

public class AuthenticateResponse extends Response {
    public final UUID userUUID;

    public AuthenticateResponse(boolean state, String msg, UUID requestUUID, UUID userUUID) {
        super(state, msg, requestUUID);
        this.userUUID = userUUID;
    }

    public AuthenticateResponse(boolean state, String msg, UUID requestUUID, RequestFailedException exception,
                                UUID userUUID) {
        super(state, msg, requestUUID, exception);
        this.userUUID = userUUID;
    }

    @Override
    public String toString() {
        return "AuthenticateResponse{" +
                "userUUID=" + userUUID +
                ", uuid=" + uuid +
                ", state=" + state +
                ", msg='" + msg + '\'' +
                ", requestUUID=" + requestUUID +
                ", exception=" + exception +
                '}';
    }
}
