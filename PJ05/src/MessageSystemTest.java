import Field.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageSystemTest {


    @Test
    public void getUser() {
        try {
            MessageSystem messageSystem = new MessageSystem("userFileTest", "messageFileTest"
                    , "conversationFileTest");
            User student1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            messageSystem.addUser(student1.credential, student1.profile);
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
    }

    @Test
    public void addUser() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void deleteMessage() {
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