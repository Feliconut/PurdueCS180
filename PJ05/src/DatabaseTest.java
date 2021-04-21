import Field.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class DatabaseTest {

    @Before
    public void setUp() throws Exception {
        System.out.println("Test begins.");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("Test ends.");
    }


    @Test
    public void write() {
        try {
            // write
            User user1 = new User(new Credential("std1", "0123"), new Profile("student1", 19));
            User user2 = new User(new Credential("std2", "0123"), new Profile("student2", 19));
            User user3 = new User(new Credential("std3", "0123"), new Profile("student3", 19));

            Database<User> db1 = (new Database<User>("testWriteFile.txt", User.class));
            db1.put(user1.uuid, user1);
            db1.put(user2.uuid, user2);
            db1.put(user3.uuid, user3);
            db1.write();
            // read
            Database<User> db2 = new Database<>("testWriteFile.txt", User.class);
            // get
            assertEquals(db2.get(user1.uuid),user1);
            // containsKey
            assertTrue(db2.containsKey(user1.uuid));
            assertTrue(db2.containsKey(user2.uuid));
            assertTrue(db2.containsKey(user3.uuid));
            // uuids
            assertTrue(Arrays.asList(db2.uuids().toArray()).contains(user1.uuid));
            assertTrue(Arrays.asList(db2.uuids().toArray()).contains(user2.uuid));
            assertTrue(Arrays.asList(db2.uuids().toArray()).contains(user3.uuid));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Exception occurs.");
            fail();
        }
    }


}