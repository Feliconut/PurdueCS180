package Request;

import Field.Credential;

import java.util.UUID;

public class GetConversationRequest extends Request{
    public final Credential credential;
    public final UUID uuid;


    public GetConversationRequest(Credential credential, UUID uuid) {
        this.credential = credential;
        this.uuid = uuid;
    }
}

