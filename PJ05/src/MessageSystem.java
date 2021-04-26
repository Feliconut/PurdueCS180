import Field.*;

import java.util.ArrayList;
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

    public User getUser(UUID find_uuid) throws UserNotFoundException {


        User user = userDatabase.get(find_uuid);
        return user;

    }

    public User getUser(Credential credential) throws UserNotFoundException, InvalidPasswordException {

        User user = getUser(credential.usrName);
        if (user.credential.passwd.equals(credential.passwd)) {
            return user;
        } else {
            throw new InvalidPasswordException();
        }
    }

    public Conversation getConversation(UUID conversation_uuid) throws ConversationNotFoundException {

        Conversation conversation = conversationDatabase.get(conversation_uuid);
        return conversation;

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

    public Message deleteMessage(UUID message_uuid, UUID conversation_uuid) throws MessageNotFoundException {
//        messageDatabase.remove(uuid);
        return null;
    }

    public Conversation deleteConversation(Conversation conversation, UUID uuid) throws ConversationNotFoundException {
//        conversationDatabase.remove(conversation.message_uuids);
        return null;
    }


    public Message addMessage(UUID message_uuid, UUID conversation_uuid) throws MessageNotFoundException {
        // want to add the a message to it's conversation and return the message
        return null;
    }

    public Conversation createConversation(String name, UUID uuid) throws InvalidConversationNameException, UserNotFoundException {
        //create one conversation(默认uuids>0)
        return null;
    }

    public Conversation setAdmin(UUID user_uuid, UUID conversation_uuid) throws ConversationNotFoundException {
        return null;

    }

    //    public Conversation createConversation(String name,UUID uuid) throws InvalidConversationNameException,UserNotFoundException{
//        // 变成单人聊天
//        return null;
//    }
    public Conversation renameConversation(String name, UUID conversation_uuid) {
        //rename
        return null;
    }

    public Conversation addUser2Conversation(UUID user_uuid, UUID conversation_uuid) {
        //拉user_uuid进入conversation_uuid这个群聊
        return null;
    }

    public Conversation removeUserFormConversation(UUID user_uuid, UUID conversation_uuid) {
        //踢user_uuid进入conversation_uuid这个群聊（被动离开）
        return null;
    }

    public Conversation quitConversation(UUID user_uuid, UUID conversation_uuid) {
        //这个uuid主动离开这个conversation_uuid群聊
        return null;
    }

    public ArrayList<UUID> allUser() {
        ArrayList<UUID> uuids = new ArrayList<>(userDatabase.uuids());
        return uuids;
    }

    public Message editMessage(String message, UUID message_uuid) {
        //message为更改后的内容
        //生成当前时间
        return null;
    }

    public UUID[] listAllUUID(UUID conversation_uuid) {
        //所有群聊人员的uuid
        return null;
    }


    /**
     * Gets all message of the given conversation starting from given time.
     **/
    public Message getMessages(UUID conversatioin_uuid) throws MessageNotFoundException, ConversationNotFoundException {
        //TODO
        Conversation conversation = getConversation(conversatioin_uuid);
        Message message = messageDatabase.get(conversatioin_uuid);


        return message;
    }

    public Message[] getConversationMessages(UUID conversation_uuid) {
        //一个conversation所有的人员的uuid
        return null;
    }

    public Message getMessage(UUID conversation_uuid) throws MessageNotFoundException {
        //一条信息
        return null;
    }

    public UUID[] getUserConversations(UUID user_uuid) throws ConversationNotFoundException {
        ArrayList<UUID> uuids = new ArrayList<>();
        for (UUID uuid : conversationDatabase.uuids()) {
            Conversation conversation = getConversation(uuid);
            if (conversation.uuid == user_uuid) {
                uuids.add(conversation.uuid);
            }
        }

        return (UUID[]) uuids.toArray();
    }


}

class UserNotFoundException extends Exception
{
}

class ConversationNotFoundException extends Exception {
}

class MessageNotFoundException extends Exception {
}

class InvalidPasswordException extends Exception
{
}

class InvalidConversationNameException extends Exception {

}

class UserExistsException extends Exception {

}
