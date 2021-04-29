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
            MessageSystem messageSystem = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User student2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            // expected successful test case for add, remove method.
            messageSystem.addUser(student1.credential, student1.profile);
            messageSystem.addUser(student2.credential, student2.profile);
            messageSystem.deleteUser(student2.uuid);
            assertEquals(student1, messageSystem.getUser(student1.credential.usrName));
            assertEquals(student1, messageSystem.getUser(student1.uuid));
            assertEquals(student1, messageSystem.getUser(student1.credential));
        } catch (UserExistsException e) {
            e.printStackTrace();
            fail();
        } catch (UserNotFoundException ex) {
            ex.printStackTrace();
            fail();
        } catch (InvalidPasswordException e) {
            e.printStackTrace();
            fail();
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
            fail();
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
            ms3.addMessage(conversation.uuid, student1.uuid, "Hi");
            ms3.addMessage(conversation.uuid, student2.uuid, "Hi");
            assertEquals(conversationTest, ms3.getConversation(conversation.uuid));
        } catch (MessageNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            fail();
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail();
        } catch (ConversationNotFoundException e) {
            e.printStackTrace();
            fail();
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

    @Test(expected = MessageNotFoundException.class)
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
            fail();
        } catch (InvalidConversationNameException e) {
            e.printStackTrace();
            fail();
        }
    }

    @Test(expected = UserNotFoundException.class)
    public void deleteUserExceptionExpected() throws UserNotFoundException {
        try {
            MessageSystem ms6 = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            ms6.deleteUser(student1.uuid);
        } catch (UserNotFoundException e) {
            String message = ""; // Exception prompt.
            assertEquals(message, e.getMessage());
            throw e;
        }
    }

    @Test
    public void deleteConversation() {
    }

    @Test
    public void addMessage() {
    }

    @Test
    public void createConversation() {
    }

    @Test
    public void setAdmin() {
    }

    @Test
    public void renameConversation() {
    }

    @Test
    public void addUser2Conversation() {
    }

    @Test
    public void removeUserFormConversation() {
    }

    @Test
    public void quitConversation() {
    }

    @Test
    public void allUser() {
    }

    @Test
    public void editMessage() {
    }

    @Test
    public void listAllUUID() {
    }

    @Test
    public void getMessages() {
    }

    @Test
    public void getConversationMessages() {
    }

    @Test
    public void getMessage() {
    }

    @Test
    public void getUserConversations() {
    }
}