import Exceptions.InvalidPasswordException;
import Exceptions.UserExistsException;
import Exceptions.UserNotFoundException;
import Field.*;

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

    public User addUser(Credential credential, Profile profile) throws UserNotFoundException, InvalidPasswordException{
        //make sure the user not exist
        try {
            User user = getUser(credential.usrName);
            throw new UserExistsException();
        }
        catch(UserNotFoundException | UserExistsException ignored)
        {

        }

        User user = new User(credential, profile);
        userDatabase.put(user.uuid,user);
        return user;


    }

    /**
     * Gets all message of the given conversation starting from given time.
     **/
    public Message[] getMessage(UUID uuid, String time)
    {
        //TODO
        return null;
    }

    public Message[] getMessage(UUID uuid)
    {
        return null;
    }

    public Conversation[] getConversation(UUID uuid, String time)
    {
        //TODO
        return null;
    }

    public Conversation[] getConversation(UUID uuid)
    {
        return null;
    }


}

