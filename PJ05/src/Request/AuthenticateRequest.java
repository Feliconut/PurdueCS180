package Request;

import Field.Credential;

public class AuthenticateRequest extends Request {
    public final Credential credential;


    public AuthenticateRequest(Credential credential) {
        super();
        this.credential = credential;
    }

    @Override
    public String toString() {
        return "AuthenticateRequest{" +
                "credential=" + credential +
                ", uuid=" + uuid +
                '}';
    }
}
