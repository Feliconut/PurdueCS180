import Exceptions.*;
import Field.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class MessageSystem {
    private final Database<User> userDatabase;
    private final Database<Message> messageDatabase;
    private final Database<Conversation> conversationDatabase;


    public MessageSystem(String userFileName, String messageFileName, String conversationFileName) {
        userDatabase = new Database<>(userFileName, User.class);
        conversationDatabase = new Database<>(conversationFileName, Conversation.class);
        messageDatabase = new Database<>(messageFileName, Message.class);

    }


    public User getUser(String name) throws UserNotFoundException {
        for (UUID uuid : userDatabase.uuids()) {
            User user = userDatabase.get(uuid);
            Credential userCredential = user.credential;
            if (userCredential.usrName.equals(name)) {
                return user;
            }
        }
        throw new UserNotFoundException();
    }

    public User getUser(Credential credential) throws UserNotFoundException, InvalidPasswordException {

        User user = getUser(credential.usrName);
        if (user.credential.passwd.equals(credential.passwd)) {
            return user;
        } else {
            throw new InvalidPasswordException();
        }
    }

    public User addUser(Credential credential, Profile profile) throws UserExistsException {
        //make sure the user not exist
        try {
            User user = getUser(credential.usrName);
            throw new UserExistsException();
        } catch (UserNotFoundException ignored) {

        }
        User user = new User(credential, profile);
        userDatabase.put(user.uuid, user);
        return user;

    }

    public Profile deleteUser(UUID uuid) throws UserNotFoundException {
//        userDatabase.remove(uuid);
        return null;
    }

    public Message deleteMessage(UUID uuid) throws MessageNotFoundException {
//        messageDatabase.remove(uuid);
        return null;
    }

    public Conversation deleteConversation(Conversation conversation, UUID uuid) throws ConversationNotFoundException {
//        conversationDatabase.remove(conversation.message_uuids);
        return null;
    }

    public <T extends Storable> void delete(UUID uuid) {

    }


    public void postMessage(Message message, UUID uuid) throws MessageNotFoundException {

    }

    /**
     * Gets all message of the given conversation starting from given time.
     **/
    public Message[] getMessages(UUID conversatioin_uuid, Date time) throws MessageNotFoundException {
        //TODO
        Conversation conversation = getConversation(conversatioin_uuid);
        Message message = messageDatabase.get(conversatioin_uuid);


        return null;
    }

    public Message[] getConversationMessages(UUID conversation_uuid) {
        return null;
    }

    public Message getMessage(UUID uuid) throws MessageNotFoundException {

        return null;
    }

    public Conversation[] getUserConversations(UUID user_uuid) {
        ArrayList<Conversation> conversations = new ArrayList<>();
        for (UUID uuid : conversationDatabase.uuids()) {
            Conversation conversation = getConversation(uuid);
            if (conversation.uuid == user_uuid) {
                conversations.add(conversation);
            }
        }
        return (Conversation[]) conversations.toArray();
    }

    public Conversation getConversation(UUID uuid) {
        return null;
    }


}

