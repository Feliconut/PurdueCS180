package Field;

import java.util.Date;
import java.util.Objects;
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
        super();
        this.sender_uuid = sender_uuid;
        this.time = time;
        this.content = content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return sender_uuid.equals(message.sender_uuid) && time.equals(message.time) && content.equals(message.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sender_uuid, time, content);
    }
}
