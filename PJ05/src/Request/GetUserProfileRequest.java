package Request;

import Field.Profile;
import Field.User;


public class GetUserProfileRequest extends Request{
    public final Profile profile;
    public final User user;


    public GetUserProfileRequest(Profile profile, User user) {
        super();
        this.profile = profile;
        this.user = user;
    }
}
