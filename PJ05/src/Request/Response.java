package Request;

import java.util.UUID;

public class Response extends Request
{
    public final boolean state;
    public final String msg;
    public final UUID request_uuid;

    public Response(boolean state, String msg, UUID request_uuid)
    {
        this.state = state;
        this.msg = msg;
        this.request_uuid = request_uuid;
    }

}
