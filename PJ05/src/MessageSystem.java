import Exceptions.*;
import Field.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    public User getUser(UUID find_uuid) throws UserNotFoundException {
        User user;

        user = userDatabase.get(find_uuid);
        if (user == null) {
            throw new UserNotFoundException();

        } else {
            return user;
        }

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
        Conversation conversation;
        conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return conversation;
        }
    }

    public User addUser(Credential credential, Profile profile) throws UserExistsException {
        //make sure the user not exist
        try {
            getUser(credential.usrName);
            throw new UserExistsException();
        } catch (UserNotFoundException ignored) {

        }
        User user = new User(credential, profile);
        userDatabase.put(user.uuid, user);
        return user;

    }

    public void deleteUser(UUID uuid) throws UserNotFoundException {
        User user;
        user = userDatabase.remove(uuid);
        if (user == null) {
            throw new UserNotFoundException();
        }
    }

    public void deleteMessage(UUID uuid) throws MessageNotFoundException {
        Message message;
        message = messageDatabase.remove(uuid);
        if (message == null) {
            throw new MessageNotFoundException();
        }
    }

    public Conversation deleteConversation(UUID uuid) throws ConversationNotFoundException {
        Conversation conversation;
        conversation = conversationDatabase.remove(uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        }
        return conversation;
    }


    public Message addMessage(UUID conversation_uuid, UUID sender_uuid, String content) throws MessageNotFoundException, ConversationNotFoundException {
        // want to add the a message to it's conversation and return the message
        Date date = new Date();
        Message message = new Message(sender_uuid, date, content);
        messageDatabase.put(message.uuid, message);
        Conversation conversation = getConversation(conversation_uuid);
        ArrayList<UUID> modified_uuids = new ArrayList<UUID>(Arrays.asList(conversation.message_uuids));
        modified_uuids.add(message.uuid);
        conversation.message_uuids = (UUID[]) modified_uuids.toArray();
        conversationDatabase.put(conversation_uuid, conversation);
        return message;
    }

    public Conversation createConversation(String conversation_name, UUID[] user_uuids) throws InvalidConversationNameException, UserNotFoundException {
        //create one conversation(默认uuids>0)
        return null;
    }

    public Conversation setAdmin(UUID user_uuid, UUID conversation_uuid) throws ConversationNotFoundException {
        return null;

    }

    public void renameConversation(String conversation_name, UUID conversation_uuid) throws ConversationNotFoundException {
        //rename
        String original_name = conversationDatabase.get(conversation_uuid).name;
        if (original_name == null) {
            throw new ConversationNotFoundException();
        } else {
            conversation_name = conversationDatabase.get(conversation_uuid).name;
        }
    }

    public void addUser2Conversation(UUID user_uuid, UUID conversation_uuid) throws UserNotFoundException,
            ConversationNotFoundException {
        //拉user_uuid进入conversation_uuid这个群聊
        ArrayList<UUID> modified_uuids = new ArrayList<>();
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        User user = userDatabase.get(user_uuid);
        if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID[] uuid = conversation.user_uuids;
            modified_uuids.addAll(Arrays.asList(uuid));
            modified_uuids.add(user_uuid);
            conversation.user_uuids = (UUID[]) modified_uuids.toArray();
        }
    }

    public Conversation removeUserFromConversation(UUID user_uuid, UUID conversation_uuid) throws UserNotFoundException, ConversationNotFoundException {
        //踢user_uuid进入conversation_uuid这个群聊（被动离开）

        return null;
    }

    public void quitConversation(UUID user_uuid, UUID conversation_uuid) throws ConversationNotFoundException, UserNotFoundException {
        //这个uuid主动离开这个conversation_uuid群聊
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        User user = userDatabase.get(user_uuid);
        ArrayList<UUID> modified_uuids = new ArrayList<>();
        if (user == null) {
            throw new UserNotFoundException();
        } else if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            for (UUID uuid : conversation.user_uuids) {
                if (user_uuid != uuid) {
                    modified_uuids.add(uuid);
                }
            }
            conversation.user_uuids = (UUID[]) modified_uuids.toArray();
        }

    }

    public ArrayList<UUID> allUser() {
        ArrayList<UUID> uuids = new ArrayList<>(userDatabase.uuids());
        return uuids;
    }

    public Message editMessage(String edit_message, UUID message_uuid) throws MessageNotFoundException {
        //message为更改后的内容
        //生成当前时间
        String original_message = messageDatabase.get(message_uuid).content;
        if (original_message == null) {
            throw new MessageNotFoundException();
        } else {
            edit_message = original_message;
        }
        return null;
    }

    public UUID[] listAllUUID(UUID conversation_uuid) throws ConversationNotFoundException {
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            return conversation.user_uuids;
        }
        //所有群聊人员的uuid
    }


    /**
     * Gets all message of the given conversation starting from given time.
     **/
//    public Conversation getMessages(UUID conversatioin_uuid) throws MessageNotFoundException {
//        //TODO
//        Conversation conversation = conversationDatabase.get(conversatioin_uuid);
//        if (conversation == null){
//            throw new MessageNotFoundException();
//        }else{
//            return conversation;
//        }
//    }
    public Message[] getConversationMessages(UUID conversation_uuid) throws ConversationNotFoundException {
        //
        ArrayList<UUID> uuids = new ArrayList<>();
        Conversation conversation = conversationDatabase.get(conversation_uuid);
        if (conversation == null) {
            throw new ConversationNotFoundException();
        } else {
            UUID[] message_uuid = conversation.message_uuids;
            uuids.addAll(Arrays.asList(message_uuid));
            return (Message[]) uuids.toArray();
        }
    }

    public Message getMessage(UUID conversation_uuid) throws MessageNotFoundException {
        //一条信息
        Message message = messageDatabase.get(conversation_uuid);
        if (message == null) {
            throw new MessageNotFoundException();
        } else {
            return message;
        }

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

    public Profile editProfile() {
        return null;
    }


}
