package Request;

import Exceptions.RequestFailedException;

import java.util.UUID;

/**
 * Project5-- Response
 * <p>
 * Every response class are based on that.
 * It made a constant response for some request that don't need a specific response
 *
 * @author team 84
 * @version 04/30/2021
 */

public class Response extends Request {
    public final boolean state;
    public final String msg;
    public final UUID requestUUID;
    public final RequestFailedException exception;

    public Response(boolean state, String msg, UUID requestUUID) {
        this.state = state;
        this.msg = msg;
        this.requestUUID = requestUUID;
        this.exception = null;
    }

    public Response(boolean state, String msg, UUID requestUUID, RequestFailedException exception) {
        this.state = state;
        this.msg = msg;
        this.requestUUID = requestUUID;
        this.exception = exception;
    }

    @Override
    public String toString() {
        return "Response{" + "uuid=" + uuid + ", state=" + state + ", msg='" + msg + '\'' + ", requestUUID=" +
                requestUUID + ", exception=" + exception + '}';
    }
}
