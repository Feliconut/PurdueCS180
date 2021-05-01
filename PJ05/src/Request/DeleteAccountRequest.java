package Request;

import Field.Credential;

/**
 * Project5-- DeleteAccountRequest
 * <p>
 * It is the request to delete a account
 *
 * @author team 84
 * @version 04/30/2021
 */
public class DeleteAccountRequest extends Request {
    public final Credential credential;


    public DeleteAccountRequest(Credential credential) {
        super();
        this.credential = credential;
    }

    @Override
    public String toString() {
        return "DeleteAccountRequest{" +
                "credential=" + credential +
                ", uuid=" + uuid +
                '}';
    }
}
