package Request;

import Field.Credential;

import java.util.UUID;

public class ListAllConversationsRequest extends Request{
    public final UUID uuid;
    public final Credential credential;

    public ListAllConversationsRequest(UUID uuid, Credential credential) {
        super();
        this.uuid = uuid;
        this.credential = credential;
    }
}
