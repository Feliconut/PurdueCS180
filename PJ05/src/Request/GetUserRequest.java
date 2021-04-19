package Request;

import Field.Credential;

import java.util.UUID;

public class GetUserRequest extends Request{
    public final Credential credential;
    public final UUID uuid;

    public GetUserRequest(Credential credential, UUID uuid) {
        super();
        this.credential = credential;
        this.uuid = uuid;
    }
}
