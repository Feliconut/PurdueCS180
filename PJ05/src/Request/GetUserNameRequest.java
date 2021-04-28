package Request;

public class GetUserNameRequest extends Request {
    public final String name;


    public GetUserNameRequest(String name) {
        this.name = name;
    }
}
