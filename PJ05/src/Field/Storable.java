package Field;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public abstract class Storable implements Serializable {

    public final UUID uuid;

    public Storable(UUID uuid) {
        this.uuid = uuid;
    }

    public Storable() {
        this.uuid = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Storable)) return false;
        Storable storable = (Storable) o;
        return uuid.equals(storable.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
