package Field;

import java.io.Serializable;

public class Credential implements Serializable {
    public final String usrName;
    public final String passwd;

    public Credential(String usrName, String passwd) {
        this.usrName = usrName;
        this.passwd = passwd;
    }

}
