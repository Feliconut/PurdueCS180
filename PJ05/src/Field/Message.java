package Field;

import java.util.Date;
import java.util.UUID;

public class Message extends Storable {
    public UUID sender_uuid;
    public Date time;
    public String content;
    public UUID conversation_uuid;

    public Message(UUID uuid, UUID sender_uuid, Date time, String content, UUID conversation_uuid) {
        super(uuid);
        this.sender_uuid = sender_uuid;
        this.time = time;
        this.content = content;
        this.conversation_uuid = conversation_uuid;
    }

    public Message(UUID sender_uuid, Date time, String content, UUID conversation_uuid) {
        super(UUID.randomUUID());
        this.sender_uuid = sender_uuid;
        this.time = time;
        this.content = content;
        this.conversation_uuid = conversation_uuid;
    }

    public Message(Message message) {
        this(
                message.uuid,
                message.sender_uuid,
                message.time,
                message.content,
                message.conversation_uuid);
    }
}
