import Field.Conversation;
import Field.Credential;
import Field.Message;
import Field.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class MessageSystem
{
    private final Database<User> userDatabase;
    private final Database<Message> messageDatabase;
    private final Database<Conversation> conversationDatabase;


    public MessageSystem(String userFileName, String messageFileName, String conversationFileName)
    {
        userDatabase = new Database<>(userFileName, User.class);
        conversationDatabase = new Database<>(conversationFileName,Conversation.class);
        messageDatabase = new Database<>(messageFileName, Message.class);

    }


    public User getUser(String name) throws UserNotFoundException
    {
        for (UUID uuid : userDatabase.uuids())
        {
            User user = userDatabase.get(uuid);
            Credential userCredential = user.credential;
            if (userCredential.usrName.equals(name))
            {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    public User getUser(Credential credential) throws UserNotFoundException, InvalidPasswordException
    {

        User user = getUser(credential.usrName);
        if (user.credential.passwd.equals(credential.passwd))
        {
            return user;
        } else
        {
            throw new InvalidPasswordException();
        }
    }

    /**
     * Gets all message of the given conversation starting from given time.
     **/
    public Message[] getMessage(UUID uuid, String time)
    {
        List<Message> list = new ArrayList<Message>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date date = format.parse(time);
            Conversation conversation = conversationDatabase.get(uuid);
            UUID[] message_uuids = conversation.message_uuids;
            for (UUID item:
                 message_uuids) {
                Message message = messageDatabase.get(item);
                long time1 = message.time.getTime();
                long time2 = date.getTime();
                if(time1 < time2){
                    continue;
                }
                list.add(message);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //TODO
        return list.toArray(new Message[list.size()]);
    }

    public Message[] getMessage(UUID uuid)
    {
        List<Message> list = new ArrayList<Message>();
        Conversation conversation = conversationDatabase.get(uuid);
        UUID[] message_uuids = conversation.message_uuids;
        for (UUID item:
                message_uuids) {
            Message message = messageDatabase.get(item);
            list.add(message);
        }

        return list.toArray(new Message[list.size()]);
    }

    public Conversation[] getConversation(UUID uuid, String time)
    {
        List<Conversation> list = new ArrayList<Conversation>();
        UUID[] userUUIDs = {uuid};
        for (UUID itemUUID:
                conversationDatabase.uuids()) {
           Conversation conversation =  conversationDatabase.get(itemUUID);
            for (UUID userID:
                 conversation.user_uuids) {
                if (!userID.equals(uuid)){
                    continue;
                }
                Message[] messages = getMessage(conversation.uuid,time);
                UUID[] msgUUIDs = new UUID[messages.length];
                for(int i = 0; i < messages.length; i++){
                    msgUUIDs[i] = messages[i].uuid;
                }
                Conversation item = new Conversation(conversation.uuid,conversation.name,conversation.user_uuids,msgUUIDs);
                list.add(item);
                break;
            }
        }

        return list.toArray(new Conversation[list.size()]);
    }

    public Conversation[] getConversation(UUID uuid)
    {
        Conversation item = conversationDatabase.get(uuid);
        List<Conversation> list = new ArrayList<Conversation>();
        list.add(item);


        return list.toArray(new Conversation[list.size()]);
    }


}

class UserNotFoundException extends Exception
{
}

class InvalidPasswordException extends Exception
{
}
