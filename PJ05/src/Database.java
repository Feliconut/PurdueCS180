import Field.Storable;

import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Database<V extends Storable> {
    private final HashMap<UUID, V> map; // This is used as a cache.
    private final Class<V> clazz;
    private final File file;

    public Database(String filename, Class<V> clazz) {
        map = new HashMap<>();
        this.clazz = clazz;

        //read the file and store data in `map`
        this.file = new File(filename);


        try {
            if (!file.isFile() && file.exists()) {
                throw new FileNotFoundException("This is not a file.");
            }
            if (!file.getName().endsWith("txt")) {
                throw new FileNotFoundException("This is not a .txt file.");
            }
            // create new file if it does not exist.
            if (!file.createNewFile()) {
                // only if the file is not new, we do need to read its data.
                try (FileInputStream fis = new FileInputStream(file);
                     ObjectInputStream objectInputStream = new ObjectInputStream(fis)) {

                    while (true) {
                        try {
                            // read a new object from the file
                            Storable storable = (Storable) objectInputStream.readObject();
                            // put the object into memory
                            map.put(storable.uuid, clazz.cast(storable));
                        } catch (EOFException e) {
                            // we reach end of file. end the loop.
                            break;
                        }
                    }
                } catch (EOFException ignored) {

                }
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Database Initialization Error");
        }
    }

    public void write() {
        try {
            // we refresh the file.
            file.delete();
            file.createNewFile();

            try (FileOutputStream fos = new FileOutputStream(file);
                 ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos)) {

                for (UUID uuid :
                        map.keySet()) {
                    Storable storable = map.get(uuid);
                    objectOutputStream.writeObject(clazz.cast(storable));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public V get(UUID uuid) {
        return map.get(uuid);
    }

    public V put(UUID uuid, V value) {
        V res = map.put(uuid, value);
        write();
        return clazz.cast(res);
    }

    public boolean containsKey(UUID uuid) {
        return map.containsKey(uuid);
    }

    public Set<UUID> uuids() {
        return map.keySet();
    }

    public V remove(UUID uuid) {
        V res = map.remove(uuid);
        write();
        return clazz.cast(res);
    }


}
