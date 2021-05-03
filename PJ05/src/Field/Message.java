package Field;

import java.util.Date;
import java.util.UUID;

public class Message extends Storable {
    public UUID senderUUID;
    public Date time;
    public String content;
    public UUID conversationUUID;

    public Message(UUID uuid, UUID senderUUID, Date time, String content, UUID conversationUUID) {
        super(uuid);
        this.senderUUID = senderUUID;
        this.time = time;
        this.content = content;
        this.conversationUUID = conversationUUID;
    }

    public Message(UUID senderUUID, Date time, String content, UUID conversationUUID) {
        super(UUID.randomUUID());
        this.senderUUID = senderUUID;
        this.time = time;
        this.content = content;
        this.conversationUUID = conversationUUID;
    }

    public Message(UUID senderUUID, Date time, String content) {
        this(senderUUID, time, content, null);
    }


    public Message(Message message) {
        this(
                message.uuid,
                message.senderUUID,
                message.time,
                message.content,
                message.conversationUUID);
    }
}
