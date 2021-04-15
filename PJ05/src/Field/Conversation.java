package Field;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class Conversation extends Storable
{
    public final UUID uuid;
    public String name;
    public Set<UUID> user_uuids;
    public ArrayList<UUID> message_uuids;

    public Conversation(UUID uuid, String name, Set<UUID> user_uuids, ArrayList<UUID> message_uuids)
    {
        this.uuid = uuid;
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }

    public Conversation(String name, Set<UUID> user_uuids, ArrayList<UUID> message_uuids)
    {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.user_uuids = user_uuids;
        this.message_uuids = message_uuids;
    }

    public Conversation(String conversationStr){
        Conversation conversation = parse(conversationStr);
        this.uuid = conversation.uuid;
        this.name = conversation.name;
        this.user_uuids = conversation.user_uuids;
        this.message_uuids = conversation.message_uuids;
    }

    public static Conversation parse(String conversationStr)
    {
        //TODO
        return null;
    }

//    public Storable parse(String conversationStr){
//        return parse(conversationStr);
//    }

    @Override
    public String toString()
    {
        return "Conversation{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                ", user_uuids=" + user_uuids +
                ", message_uuids=" + message_uuids +
                '}';
    }


}
