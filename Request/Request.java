package Request;

import java.io.Serializable;
import java.util.UUID;

/**
 * Project5-- Request
 * <p>
 * Every request class are based on that. It made a unique ID for every request
 *
 * @author team 84
 * @version 04/30/2021
 */
public abstract class Request implements Serializable {
    public final UUID uuid;

    public Request() {

        this.uuid = UUID.randomUUID();
    }
}

