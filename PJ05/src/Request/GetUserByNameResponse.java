package Request;

import Field.User;

import java.util.UUID;

/**
 * Project5-- GetUserByNameResponse
 * <p>
 * It is the request to get a user name
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetUserByNameResponse extends Response {
    public final User user;

    public GetUserByNameResponse(boolean state, String msg, UUID requestUUID, User user) {
        super(state, msg, requestUUID);
        this.user = user;
    }
}
