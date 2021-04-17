package Request;

import java.io.Serializable;
import java.util.UUID;

public abstract class Request implements Serializable
{
    public final UUID uuid;

    public Request()
    {

        this.uuid = UUID.randomUUID();
    }
}

