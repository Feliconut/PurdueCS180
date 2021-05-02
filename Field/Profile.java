package Field;

import java.io.*;
import java.util.*;

public class Profile implements Serializable {
    public final String name;
    public final int age;

    public Profile(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Profile)) {
            return false;
        }
        Profile profile = (Profile) o;
        return age == profile.age && name.equals(profile.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
