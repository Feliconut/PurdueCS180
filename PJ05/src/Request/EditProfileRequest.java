package Request;

import Field.Profile;

public class EditProfileRequest extends Request {
    public final Profile profile;

    public EditProfileRequest(Profile profile) {
        this.profile = profile;
    }
}
