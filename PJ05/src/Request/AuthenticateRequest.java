package Request;

import Field.Credential;

/**
 * Project5-- AuthenticateRequest
 * <p>
 * It is the request authenticate the user
 *
 * @author team 84
 * @version 04/30/2021
 */
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
