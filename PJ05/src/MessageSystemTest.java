import Exceptions.*;
import Field.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class MessageSystemTest {

    @Before
    public void setUp() {
        System.out.println("Test begins.");
        (new File("userFileTest.txt")).delete();
        (new File("messageFileTest.txt")).delete();
        (new File("conversationFileTest.txt")).delete();
    }

    @After
    public void tearDown() {
        System.out.println("Test ends. Clear Local Cache.");

        (new File("userFileTest.txt")).delete();
        (new File("messageFileTest.txt")).delete();
        (new File("conversationFileTest.txt")).delete();
    }

    @Test
    public void getUser() {
        try {
            MessageSystem messageSystem = new MessageSystem("userFileTest.txt", "messageFileTest.txt",
                    "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            // expected successful test case for add, remove method.
            student1 = messageSystem.addUser(student1.credential, student1.profile);
            student2 = messageSystem.addUser(student2.credential, student2.profile);
            messageSystem.deleteUser(student2.uuid);
            assertEquals(student1, messageSystem.getUser(student1.credential.usrName));
            assertEquals(student1, messageSystem.getUser(student1.uuid));
            assertEquals(student1, messageSystem.getUser(student1.credential));
        } catch (UserExistsException | UserNotFoundException | InvalidUsernameException | InvalidPasswordException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void getUserExceptionExpected() throws UserNotFoundException {
        // UserNotFoundException| InvalidUsernameException expected.
        try {
            MessageSystem ms2 = new MessageSystem("userFileTest.txt", "messageFileTest.txt",
                    "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            ms2.addUser(student2.credential, student2.profile);
            ms2.getUser(student1.uuid);
        } catch (UserNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (UserExistsException | InvalidUsernameException ex) {
            ex.printStackTrace();
            fail("Exception occurs when not expected.");
        }
        fail("UserNotFoundException did not throw when expected.");
    }


    @Test
    public void getConversation() {
        try {
            MessageSystem ms3 = new MessageSystem("userFileTest.txt", "messageFileTest.txt",
                    "conversationFileTest.txt");
            User student1 = ms3.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = ms3.addUser(new Credential("std2", "0123"), new Profile("student2", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            Message message2 = new Message(student2.uuid, new Date(), "Hi");
            UUID[] group1 = {student1.uuid};
            UUID[] messageGroup = {message1.uuid, message2.uuid};
            new Conversation("Group1", group1, student1.uuid, messageGroup);
            // expected successful test case for addUser, addMessage, and remove method.
            Conversation conversation = ms3.createConversation("group1", group1, group1[0]);
            ms3.addUser2Conversation(student2.uuid, conversation.uuid);
            ms3.addMessage(conversation.uuid, message1.senderUUID, message1.content);
            ms3.addMessage(conversation.uuid, message2.senderUUID, message2.content);
            // 修改了addMessage method，添加了特定conversationUUID，等整个写完要确认此处没有问题
            assertEquals(conversation, ms3.getConversation(conversation.uuid));
        } catch (UserNotFoundException | InvalidConversationNameException | ConversationNotFoundException | UserExistsException | IllegalContentException | InvalidUsernameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test(expected = UserExistsException.class)
    public void addUserExceptionExpected() throws UserExistsException {
        try {
            MessageSystem ms4 = new MessageSystem("userFileTest.txt", "messageFileTest.txt",
                    "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            student1 = ms4.addUser(student1.credential, student1.profile);
            ms4.addUser(student1.credential, student1.profile);
        } catch (UserExistsException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            ms.deleteUser(student1.uuid);
        } catch (UserNotFoundException e) {
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test(expected = MessageNotFoundException.class)
    public void deleteMessageExceptionExpected() throws MessageNotFoundException {
        try {
            MessageSystem ms5 = new MessageSystem("userFileTest.txt", "messageFileTest.txt",
                    "conversationFileTest.txt");
            User student1 = ms5.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            UUID[] uuids = {student1.uuid};
            ms5.createConversation("group1", uuids, uuids[0]);
            ms5.deleteMessage(message1.uuid);
        } catch (MessageNotFoundException e) {
            // Exception prompt.

            assertNull(e.getMessage());
            throw e;
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.ConversationNotFoundException | Exceptions.InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ConversationNotFoundException.class)
    public void deleteConversationExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            Message message2 = new Message(student2.uuid, new Date(), "Hi");
            UUID[] group1 = {student1.uuid, student2.uuid};
            UUID[] messageGroup = {message1.uuid, message2.uuid};
            Conversation conversationTest = new Conversation("Group1", group1, student1.uuid, messageGroup);
            ms.deleteConversation(conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test(expected = InvalidConversationNameException.class)
    public void createConversationExceptionExpected() throws InvalidConversationNameException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            UUID[] uuids = {student1.uuid};
            ms.createConversation(null, uuids, uuids[0]);
            // needed edit.
        } catch (InvalidConversationNameException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (UserNotFoundException | InvalidUsernameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test(expected = ConversationNotFoundException.class)
    public void setAdminExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            UUID[] messageGroup = {message1.uuid};
            UUID[] uuids = {student1.uuid};
            Conversation conversationTest = new Conversation("Group1", uuids, student1.uuid, messageGroup);
            ms.setAdmin(student1.uuid, conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void renameConversationTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = ms.addUser(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid, student2.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1, group1[0]);
            ms.renameConversation("group", conversationTest.uuid);
        } catch (Exceptions.ConversationNotFoundException | Exceptions.InvalidConversationNameException | Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ConversationNotFoundException.class)
    public void renameConversationExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid, student2.uuid};
            Conversation conversation = new Conversation("Group", group1, student1.uuid, null);
            ms.renameConversation("group", conversation.uuid);
        } catch (ConversationNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void addUser2ConversationTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = ms.addUser(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1, group1[0]);
            ms.addUser2Conversation(student2.uuid, conversationTest.uuid);
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.InvalidConversationNameException | Exceptions.ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserExistsException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void addUser2ConversationExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1, group1[0]);
            ms.addUser2Conversation(UUID.randomUUID(), conversationTest.uuid);
        } catch (UserNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (Exceptions.ConversationNotFoundException | Exceptions.InvalidConversationNameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test(expected = UserNotFoundException.class)
    public void quitConversationTestExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            student1 = ms.addUser(student1.credential, student1.profile);
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1, group1[0]);
            ms.quitConversation(student2.uuid, conversationTest.uuid);
        } catch (Exceptions.UserExistsException | Exceptions.InvalidConversationNameException | Exceptions.ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (UserNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        } catch (InvalidUsernameException e) {
            e.printStackTrace();
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void quitConversationTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            student1 = ms.addUser(student1.credential, student1.profile);
            UUID[] group1 = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", group1, group1[0]);
            ms.quitConversation(student1.uuid, conversationTest.uuid);
        } catch (Exceptions.UserExistsException | Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.InvalidConversationNameException | Exceptions.ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test
    public void editMessageTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            Conversation conversation = ms.createConversation("Group1", new UUID[]{student1.uuid}, student1.uuid);
            Message message1 = ms.addMessage(conversation.uuid, student1.uuid, "Hi");
            ms.editMessage("Hi", message1.uuid);
        } catch (MessageNotFoundException | UserExistsException | ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.IllegalContentException | Exceptions.InvalidConversationNameException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = MessageNotFoundException.class)
    public void editMessageExceptionExpected() throws MessageNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            UUID[] uuids = {student1.uuid};
            ms.createConversation("Group1", uuids, student1.uuid);
            //            message1 = ms.addMessage(conversationTest.uuid, student1.uuid, message1.content);
            ms.editMessage("Hi", message1.uuid);
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.UserExistsException | Exceptions.InvalidConversationNameException e) {
            e.printStackTrace();
        }
        fail("MessageNotFoundException did not throw when expected.");
    }

    @Test
    public void getConversationMessagesTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            ms.addUser(new Credential("std2", "0123"), new Profile("student2", 19));
            UUID[] group1 = {student1.uuid};
            // expected successful test case for addUser, addMessage, and remove method.
            Conversation conversation = ms.createConversation("group1", group1, group1[0]);
            Message message = ms.addMessage(conversation.uuid, student1.uuid, "Hi!");
            Message[] messages = ms.getConversationMessages(conversation.uuid);
            assertEquals(messages[0], message);
        } catch (ConversationNotFoundException | UserExistsException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.IllegalContentException | Exceptions.InvalidConversationNameException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = ConversationNotFoundException.class)
    public void getConversationMessageExceptionExpected() throws ConversationNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            UUID[] messageGroup = {message1.uuid};
            UUID[] uuids = {student1.uuid};
            Conversation conversationTest = new Conversation("Group1", uuids, student1.uuid, messageGroup);
            conversationTest = ms.deleteConversation(conversationTest.uuid);
            ms.getConversationMessages(conversationTest.uuid);
        } catch (ConversationNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void getMessageTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = ms.addUser(new Credential("std1", "0123"), new Profile("student1", 19));
            UUID[] uuids = {student1.uuid};
            Conversation conversationTest = ms.createConversation("Group1", uuids, student1.uuid);
            Message message1 = ms.addMessage(conversationTest.uuid, student1.uuid, "Hi!");
            assertEquals(message1, ms.getConversationMessages(conversationTest.uuid)[0]);
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        } catch (Exceptions.UserNotFoundException | Exceptions.InvalidUsernameException | Exceptions.UserExistsException | Exceptions.InvalidConversationNameException | Exceptions.IllegalContentException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = MessageNotFoundException.class)
    public void getMessageExceptionExpected() throws MessageNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Message message1 = new Message(student1.uuid, new Date(), "Hi");
            ms.getMessage(message1.uuid);
        } catch (MessageNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }

    @Test
    public void editProfileTest() {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Profile profile = new Profile("name", 18);
            student1 = ms.addUser(student1.credential, student1.profile);
            Profile profile1 = ms.editProfile(student1.uuid, profile);
            assertEquals(profile, profile1);
        } catch (UserExistsException | UserNotFoundException | InvalidUsernameException e) {
            e.printStackTrace();
            fail("Exception occurs when not expected.");
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void editProfileExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms = new MessageSystem("userFileTest.txt", "messageFileTest.txt", "conversationFileTest.txt");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            Profile profile = new Profile("name", 18);
            ms.editProfile(student1.uuid, profile);
        } catch (UserNotFoundException e) {
            // Exception prompt.
            assertNull(e.getMessage());
            throw e;
        }
        fail("UserExistException did not throw when expected.");
    }
}