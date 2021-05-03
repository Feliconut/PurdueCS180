package Field;

import java.util.Arrays;
import java.util.UUID;

public class Conversation extends Storable {
    public String name;
    public UUID[] userUUIDs;
    public UUID adminUUID;
    public UUID[] messageUUIDs;

    public Conversation(UUID uuid, String name, UUID[] userUUIDs, UUID adminUUID, UUID[] messageUUIDs) {
        super(uuid);
        this.name = name;
        this.userUUIDs = userUUIDs;
        this.adminUUID = adminUUID;
        this.messageUUIDs = messageUUIDs;
        assert Arrays.asList(userUUIDs).contains(adminUUID);
    }

    public Conversation(String name, UUID[] userUUIDs, UUID adminUUID, UUID[] messageUUIDs) {
        super(UUID.randomUUID());
        this.name = name;
        this.userUUIDs = userUUIDs;
        this.adminUUID = adminUUID;
        this.messageUUIDs = messageUUIDs;
        assert Arrays.asList(userUUIDs).contains(adminUUID);
    }

    public Conversation(Conversation conversation) {
        this(
                conversation.uuid,
                conversation.name,
                conversation.userUUIDs,
                conversation.adminUUID,
                conversation.messageUUIDs);
    }


}
