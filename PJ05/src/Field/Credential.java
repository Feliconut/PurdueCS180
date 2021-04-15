package Field;

public class Credential
{
    public final String usrName;
    public final String passwd;

    public Credential(String usrName, String passwd)
    {
        this.usrName = usrName;
        this.passwd = passwd;
    }

    public Credential()
    {
        this.usrName = "undefined";
        this.passwd = "undefined";
    }

    public static Credential parseCredential(String credentialStr)
    {
        //TODO
        return null;
    }

    public String toString()
    {
        //TODO
        return null;
    }
}
