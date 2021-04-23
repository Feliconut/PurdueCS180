package Field;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

public class Conversation extends Storable {
    public String name;
    public UUID[] user_uuids;
    public UUID[] message_uuids;

    public Conversation(UUID uuid, String name, UUID[] user_uuids, UUID[] message_uuids) {
        super(uuid);
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }

    public Conversation(String name, UUID[] user_uuids, UUID[] message_uuids) {
        super();
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Conversation)) return false;
        Conversation that = (Conversation) o;
        return name.equals(that.name) && Arrays.equals(user_uuids, that.user_uuids) && Arrays.equals(message_uuids, that.message_uuids);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(user_uuids);
        result = 31 * result + Arrays.hashCode(message_uuids);
        return result;
    }
}
