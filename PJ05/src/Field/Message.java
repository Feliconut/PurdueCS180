package Field;

import java.util.Date;
import java.util.UUID;

public class Message extends Storable {
    public final UUID sender_uuid;
    public final Date time;
    public final String content;


    public Message(UUID uuid, UUID sender_uuid, Date time, String content) {
        super(uuid);
        this.sender_uuid = sender_uuid;
        this.time = time;
        this.content = content;
    }

    public Message(UUID sender_uuid, Date time, String content) {
        super(UUID.randomUUID());
        this.sender_uuid = sender_uuid;
        this.time = time;
        this.content = content;
    }
}
