import Exceptions.*;
import Field.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class MessageSystemTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Test begins.");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test ends.");
    }

    @Test
    public void getUser() {
        try {
            MessageSystem messageSystem = new MessageSystem("userFileTest.txt", "messageFileTest.txt"
                    , "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            // expected successful test case for add, remove method.
            student1 = messageSystem.addUser(student1.credential, student1.profile);
            student2 = messageSystem.addUser(student2.credential, student2.profile);
            messageSystem.deleteUser(student2.uuid);
            assertEquals(student1, messageSystem.getUser(student1.credential.usrName));
            assertEquals(student1, messageSystem.getUser(student1.uuid));
            assertEquals(student1, messageSystem.getUser(student1.credential));
        } catch (UserExistsException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test (expected = UserNotFoundException.class)
    public void getUserExceptionExpected() throws UserNotFoundException {
        // UserNotFoundException expected.
        try {
            MessageSystem ms2 = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            ms2.addUser(student2.credential, student2.profile);
            ms2.getUser(student1.uuid);
        } catch (UserNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        } catch (UserExistsException ex) {
            ex.printStackTrace();
            fail("Exception occurs when not expected.");
        }
        fail("UserNotFoundException did not throw when expected.");
    }


    @Test
    public void getConversation() {
        try {
            MessageSystem ms3 = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            Message message1 = new Message(student1.uuid, new Date(2022, 4, 26), "Hi");
            Message message2 = new Message(student2.uuid, new Date(2022, 4, 26), "Hi");
            UUID[] group1 = {student1.uuid, student2.uuid};
            UUID[] messageGroup = {message1.uuid, message2.uuid};
            Conversation conversationTest = new Conversation("Group1", group1, student1.uuid, messageGroup);
            // expected successful test case for addUser, addMessage, and remove method.
            Conversation conversation = ms3.createConversation("group1", group1);
            ms3.addUser2Conversation(student2.uuid, conversation.uuid);
            ms3.addMessage(message1.sender_uuid, conversation.uuid, message1.content);
            ms3.addMessage(message2.sender_uuid, conversation.uuid, message2.content);
            // 修改了addMessage method，添加了特定conversation_uuid，等整个写完要确认此处没有问题
            assertEquals(conversationTest, ms3.getConversation(conversation.uuid));
        } catch (MessageNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test (expected = UserExistsException.class)
    public void addUserExceptionExpected() throws UserExistsException {
        try {
            MessageSystem ms4 = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            ms4.addUser(student1.credential, student1.profile);
            ms4.addUser(student1.credential, student1.profile);
        } catch (UserExistsException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test (expected = UserNotFoundException.class)
    public void deleteUserExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            ms.deleteUser(student1.uuid);
        } catch (UserNotFoundException e) {
            String message = "";
            assertEquals(message, e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test (expected = MessageNotFoundException.class)
    public void deleteMessageExceptionExpected() throws MessageNotFoundException {
        try {
            MessageSystem ms5 = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(2022, 4, 26), "Hi");
            UUID[] uuids = {student1.uuid};
            Conversation conversation = ms5.createConversation("group1", uuids);
            ms5.deleteMessage(message1.uuid);
        } catch (MessageNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test (expected = ConversationNotFoundException.class)
    public void deleteConversationExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            Message message1 = new Message(student1.uuid, new Date(2022, 4, 26), "Hi");
            Message message2 = new Message(student2.uuid, new Date(2022, 4, 26), "Hi");
            UUID[] group1 = {student1.uuid, student2.uuid};
            UUID[] messageGroup = {message1.uuid, message2.uuid};
            Conversation conversationTest = new Conversation("Group1", group1, student1.uuid, messageGroup);
            ms.deleteConversation(conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test (expected = InvalidConversationNameException.class)
    public void createConversationExceptionExpected() throws InvalidConversationNameException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            UUID[] uuids = {student1.uuid};
            Conversation conversation = ms.createConversation(null, uuids);
            // needed edit.
        } catch (InvalidConversationNameException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test (expected = ConversationNotFoundException.class)
    public void setAdminExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(2022, 4, 26), "Hi");
            UUID[] messageGroup = {message1.uuid};
            UUID[] uuids = {student1.uuid};
            Conversation conversationTest = new Conversation("Group1", uuids, student1.uuid, messageGroup);
            ms.setAdmin(student1.uuid, conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void renameConversationTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid, student2.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1);
            ms.renameConversation("group", conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test (expected = ConversationNotFoundException.class)
    public void renameConversationExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid, student2.uuid};
            UUID[] messages = null;
            Conversation conversation = new Conversation("Group", group1, student1.uuid, messages);
            ms.renameConversation("group", conversation.uuid);
        } catch (ConversationNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void addUser2ConversationTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1);
            ms.addUser2Conversation(student2.uuid, conversationTest.uuid);
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test (expected = UserNotFoundException.class)
    public void addUser2ConversationExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1);
            ms.addUser2Conversation(UUID.randomUUID(), conversationTest.uuid);
        } catch (UserNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
        fail("UserExistException did not throw when expected.");
    }

}