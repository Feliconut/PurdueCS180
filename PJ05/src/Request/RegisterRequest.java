package Request;

import Field.Credential;
import Field.Profile;

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
        return "RegisterRequest{" +
                "credential=" + credential +
                ", profile=" + profile +
                ", uuid=" + uuid +
                '}';
    }


}
