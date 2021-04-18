package Request;

import java.util.UUID;

public class RegisterResponse extends Response{
    public final UUID uuid;

    public RegisterResponse(boolean state, String msg, UUID request_uuid, UUID uuid) {
        super(state, msg, request_uuid);
        this.uuid = uuid;
    }
}
