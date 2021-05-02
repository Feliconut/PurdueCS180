package Request;

import Field.Profile;

/**
 * Project5-- EditProfileRequest
 * <p>
 * It is the request to edit user profile
 *
 * @author team 84
 * @version 04/30/2021
 */
public class EditProfileRequest extends Request {
    public final Profile profile;

    public EditProfileRequest(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "EditProfileRequest{" +
                "profile=" + profile +
                ", uuid=" + uuid +
                '}';
    }
}
