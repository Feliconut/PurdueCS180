package Field;

import java.util.UUID;

public class Conversation extends Storable
{
    public String name;
    public UUID[] user_uuids;
    public UUID[] message_uuids;

    public Conversation(UUID uuid, String name, UUID[] user_uuids, UUID[] message_uuids)
    {
        super(uuid);
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }

    public Conversation(String name, UUID[] user_uuids, UUID[] message_uuids)
    {
        super(UUID.randomUUID());
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }


}
