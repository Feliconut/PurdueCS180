package Field;

import java.io.Serializable;
import java.util.UUID;

public abstract class Storable implements Serializable
{

    public final UUID uuid;

    public Storable(UUID uuid)
    {
        this.uuid = uuid;
    }
}
