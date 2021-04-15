package Field;

import java.util.UUID;

public abstract class Storable
{

    public UUID uuid;

    @Override
    public abstract String toString();

    public static Storable parse(String str){
        return null;
    }
}
