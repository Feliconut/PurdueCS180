import Field.Conversation;
import Field.Credential;
import Field.Message;
import Field.User;

import java.util.ArrayList;
import java.util.UUID;

public class MessageSystem
{
    private final Database<User> userDatabase;
    private final Database<Message> messageDatabase;
    private final Database<Conversation> conversationDatabase;


    public MessageSystem(String userFileName, String messageFileName, String conversationFileName)
    {
        userDatabase = new Database<User>(userFileName);
        conversationDatabase = new Database<Conversation>(conversationFileName);
        messageDatabase = new Database<Message>(messageFileName);

    }



    public User getUser(String name) throws UserNotFoundException
    {
        for (UUID uuid:userDatabase.uuids())
        {
            User user = userDatabase.get(uuid);
            Credential userCredential = user.getCredential();
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
        if (user.getCredential().passwd.equals(credential.passwd))
        {
            return user;
        } else
        {
            throw new InvalidPasswordException();
        }
    }

    /**
        Gets all message of the given conversation starting from given time.
     **/
    public ArrayList<Message> getMesssage(UUID uuid, String time)
    {
        //TODO
        return null;
    }

    public ArrayList<Message> getMessage(UUID uuid)
    {
        return null;
    }
    public ArrayList<Conversation> getConversation(UUID uuid, String time)
    {
        //TODO
        return null;
    }

    public ArrayList<Conversation> getConversation(UUID uuid)
    {
        return null;
    }




}

class UserNotFoundException extends Exception
{
}

class InvalidPasswordException extends Exception
{
}
