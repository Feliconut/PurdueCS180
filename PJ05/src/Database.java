import Field.Storable;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class Database<T extends Storable>
{
    private Map<UUID, T> map; // This is used as a cache.

    public Database(String filename){
        //TODO read the file and store data in `map`

        String line = "";
        T obj = (T) T.parse(line);

        map.put(obj.uuid, obj);
    }

    public void write(){
        //TODO write the new data into file.
    }

    public T get(UUID uuid){
        return map.get(uuid);
    }

    public void set(UUID uuid, T value){
        map.put(uuid, value);
        write();
    }

    public boolean contains(UUID uuid){
        return map.containsKey(uuid);
    }

    public Set<UUID> uuids(){
        return map.keySet();
    }


}
