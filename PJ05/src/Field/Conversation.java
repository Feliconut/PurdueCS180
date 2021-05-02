package Field;

import java.util.Arrays;
import java.util.UUID;

public class Conversation extends Storable {
    public String name;
    public UUID[] user_uuids;
    public UUID admin_uuid;
    public UUID[] message_uuids;

    public Conversation(UUID uuid, String name, UUID[] user_uuids, UUID admin_uuid, UUID[] message_uuids) {
        super(uuid);
        this.name = name;
        this.user_uuids = user_uuids;
        this.admin_uuid = admin_uuid;
        this.message_uuids = message_uuids;
        assert Arrays.asList(user_uuids).contains(admin_uuid);
    }

    public Conversation(String name, UUID[] user_uuids, UUID admin_uuid, UUID[] message_uuids) {
        super(UUID.randomUUID());
        this.name = name;
        this.user_uuids = user_uuids;
        this.admin_uuid = admin_uuid;
        this.message_uuids = message_uuids;
        assert Arrays.asList(user_uuids).contains(admin_uuid);
    }

    public Conversation(Conversation conversation) {
        this(
                conversation.uuid,
                conversation.name,
                conversation.user_uuids,
                conversation.admin_uuid,
                conversation.message_uuids);
    }


}
