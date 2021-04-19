package Request;

import Field.Credential;
import Field.Profile;

import java.util.UUID;

public class AddUser2ConversationRequest extends Request{

    public final UUID uuid;
    public final Credential credential;

    public AddUser2ConversationRequest(UUID uuid, Credential credential) {
        super();
        this.uuid = uuid;
        this.credential = credential;
    }
}
