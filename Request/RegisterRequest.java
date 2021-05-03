package Request;

import Field.Credential;
import Field.Profile;

/**
 * Project5-- RegisterRequest
 * <p>
 * It is the request to let user register
 *
 * @author team 84
 * @version 04/30/2021
 */
public class RegisterRequest extends Request {

    public final Credential credential;
    public final Profile profile;

    public RegisterRequest(Credential credential, Profile profile) {
        super();
        this.credential = credential;
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "RegisterRequest{" + "credential=" + credential + ", profile=" + profile + ", uuid=" + uuid + '}';
    }


}
