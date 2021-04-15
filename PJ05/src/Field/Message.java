package Field;

import java.util.UUID;

public class Message extends Storable
{
    public UUID uuid;
    public UUID senderUID;
    public UUID receiverUID;
    public String time;
    public String content;

    public Message(UUID senderUID, String time, String content)
    {
        this.uuid = UUID.randomUUID();
        this.senderUID = senderUID;
        this.time = time;
        this.content = content;
    }

    public Message(UUID uuid, UUID senderUID, String time, String content)
    {
        this.uuid = uuid;
        this.senderUID = senderUID;
        this.time = time;
        this.content = content;
    }

    @Override
    public String toString()
    {
        return "Message{" +
                "uuid=" + uuid +
                ", senderUID=" + senderUID +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    public static Message parse(String messageStr)
    {
        //TODO
        return null;
    }

    public Message(String messageStr)
    {
        Message message = parse(messageStr);
        assert message != null;
        this.uuid = message.uuid;
        this.senderUID = message.senderUID;
        this.time = message.time;
        this.content = message.content;
    }

}
