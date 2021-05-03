package Field;

import java.io.*;
import java.util.*;

public class Credential implements Serializable {
    public final String usrName;
    public final String passwd;

    public Credential(String usrName, String passwd) {
        this.usrName = usrName;
        this.passwd = passwd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Credential)) {
            return false;
        }
        Credential that = (Credential) o;
        return Objects.equals(usrName, that.usrName) && Objects.equals(passwd, that.passwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usrName, passwd);
    }
}
