import Field.Storable;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Database<V extends Storable> {
    private final HashMap<UUID, V> map; // This is used as a cache.
    private final Class<V> clazz;

    public Database(String filename, Class<V> clazz) {
        map = new HashMap<>();
        this.clazz = clazz;
        //TODO read the file and store data in `map`
        ObjectInputStream objectInputStream;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }


        String line = "";
        try {
            Storable obj = (Storable) objectInputStream.readObject();

            map.put(obj.uuid, clazz.cast(obj));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void write() {
        //TODO write the new data into file.
    }

    public V get(UUID uuid) {
        return map.get(uuid);
    }

    public V put(UUID uuid, V value) {

        return map.put(uuid, value);
    }

    public boolean containsKey(UUID uuid) {
        return map.containsKey(uuid);
    }

    public Set<UUID> uuids() {
        return map.keySet();
    }


}
