package Request;

/**
 * Project5-- GetUserNameRequest
 * <p>
 * It is the request to get a user name
 *
 * @author team 84
 * @version 04/30/2021
 */
public class GetUserNameRequest extends Request {
    public final String name;


    public GetUserNameRequest(String name) {
        this.name = name;
    }
}
