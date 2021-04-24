import Field.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.UUID;

import static org.junit.Assert.*;

public class MessageSystemTest {
    private final Database<User> db1 = (new Database<User>("userFileTest", User.class));
    private final Database<Message> db2 = (new Database<Message>("messageFileTest", Message.class));
    private final Database<Conversation> db3 = (new Database<Conversation>("conversationFileTest", Conversation.class));
    private MessageSystem messageSystem;
    private final User user1 = new User(new Credential("std1", "0123")
            , new Profile("student1", 19));
    private final User user2 = new User(new Credential("std2", "0123")
            , new Profile("student2", 19));
    private final User user3 = new User(new Credential("std3", "0123")
            , new Profile("student3", 19));
    private final UUID[] group1 = {user1.uuid, user2.uuid};
    private final UUID[] group2 = {user1.uuid, user2.uuid, user3.uuid};
    private final Message message1 = new Message(user1.uuid, (new Date(2022, 4, 24)), "Hi");
    private final Message message2 = new Message( user2.uuid, (new Date(2021, 4, 24)), "hi");
    private final UUID[] mess_uuids = {message1.uuid, message2.uuid};
    private final Conversation conversation1 = new Conversation("groupOne", group1, mess_uuids);
    private final Conversation conversation2 = new Conversation("groupTwo", group2, mess_uuids);


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
        db1.put(user1.uuid, user1);
        db1.put(user2.uuid, user2);
        db1.put(user3.uuid, user3);
        db1.write();
        db2.put(message1.uuid, message1);
        db2.put(message2.uuid, message2);
        db2.write();
        db3.put(conversation1.uuid, conversation1);
        db3.put(conversation2.uuid, conversation2);
        db3.write();
        messageSystem = new MessageSystem("userFileTest",
                "messageFileTest", "conversationFileTest");
        try {
            assertEquals(user1, messageSystem.getUser(user1.profile.name));
            assertEquals(user3, messageSystem.getUser(user2.profile.name));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            System.out.println("UserNotFoundException appears when improper situation.");
            fail();
        }
    }

    @Test (expected = UserNotFoundException.class)
    public void getUserExceptionExpected() throws UserNotFoundException {
        try {
            db1.put(user1.uuid, user1);
            db1.put(user2.uuid, user2);
            db1.write();
            messageSystem = new MessageSystem("userFileTest",
                    "messageFileTest", "conversationFileTest");
            messageSystem.getUser(user3.profile.name);
        } catch(UserNotFoundException ex) {
            String message = ""; // Exception prompt.
            assertEquals(message, ex.getMessage());
            throw ex;
        }
        fail("UserNotFoundException did not throw when expected.");
    }

    @Test
    public void getUserCredential() {
        db1.put(user1.uuid, user1);
        db1.put(user2.uuid, user2);
        db1.put(user3.uuid, user3);
        db1.write();
        messageSystem = new MessageSystem("userFileTest",
                "messageFileTest", "conversationFileTest");
        try {
            assertEquals(user1, messageSystem.getUser(user1.credential));
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            System.out.println("UserNotFoundException appears when improper situation.");
            fail();
        } catch (InvalidPasswordException ex) {
            ex.printStackTrace();
            System.out.println("InvalidPasswordException appears when improper situation.");
            fail();
        }
    }

    @Test (expected = InvalidPasswordException.class)
    public void getUserExceptionExpected2() throws InvalidPasswordException, UserNotFoundException {
        try {
            db1.put(user1.uuid, user1);
            db1.put(user2.uuid, user2);
            db1.write();
            messageSystem = new MessageSystem("userFileTest",
                    "messageFileTest", "conversationFileTest");
            messageSystem.getUser(user3.credential);
        } catch(InvalidPasswordException | UserNotFoundException ex) {
            String message = ""; // Exception prompt.
            assertEquals(message, ex.getMessage());
            throw ex;
        }
        fail("InvalidPasswordException did not throw when expected.");
    }

    @Test
    public void getMessage() {
    }

    @Test
    public void testGetMessage() {
    }

    @Test
    public void getConversation() {
    }

    @Test
    public void testGetConversation() {
    }
}