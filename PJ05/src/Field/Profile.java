package Field;

import java.io.Serializable;

public class Profile implements Serializable {
    public final String name;
    public final int age;

    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
