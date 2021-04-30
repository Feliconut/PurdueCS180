package Request;

public class GetUserByNameRequest extends Request {
    public final String username;


    public GetUserByNameRequest(String username) {
        this.username = username;
    }
}
