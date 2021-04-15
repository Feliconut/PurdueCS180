package Request;

public class Response
{
    public final BaseClientRequest request;
    public final boolean state;
    public final String msg;

    public Response(boolean state, String msg, BaseClientRequest request)
    {
        this.request = request;
        this.state = state;
        this.msg = msg;
    }
}
