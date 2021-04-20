package Request;

import Exceptions.RequestFailedException;

import java.util.UUID;

public class Response extends Request {
    public final boolean state;
    public final String msg;
    public final UUID request_uuid;
    public final RequestFailedException exception;

    public Response(boolean state, String msg, UUID request_uuid) {
        this.state = state;
        this.msg = msg;
        this.request_uuid = request_uuid;
        this.exception = null;
    }

    public Response(boolean state, String msg, UUID request_uuid, RequestFailedException exception) {
        this.state = state;
        this.msg = msg;
        this.request_uuid = request_uuid;
        this.exception = exception;
    }
}
