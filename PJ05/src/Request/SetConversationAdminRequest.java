package Request;

import java.util.UUID;

/**
 * Project5-- SetConversationAdminRequest
 * <p>
 * It is the request to set the admin of conversation
 *
 * @author team 84
 * @version 04/30/2021
 */
public class SetConversationAdminRequest extends Request {
    public final UUID adminUUID;
    public final UUID conversationUUID;

    public SetConversationAdminRequest(UUID adminUUID, UUID conversationUUID) {
        super();
        this.adminUUID = adminUUID;
        this.conversationUUID = conversationUUID;
    }

    @Override
    public String toString() {
        return "SetConversationAdminRequest{" + "uuid=" + uuid + ", adminUUID=" + adminUUID + ", conversationUUID=" +
                conversationUUID + '}';
    }
}
