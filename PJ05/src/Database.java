import Field.Storable;
import java.io.*;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

public class Database<V extends Storable>
{
    private final HashMap<UUID, V> map; // This is used as a cache.
    private final Class<V> clazz;

    public Database(String filename, Class<V> clazz)
    {
        map = new HashMap<>();
        this.clazz = clazz;
        //TODO read the file and store data in `map`
        File file = new File(filename);
        int count = 0;
        if (file.isFile() && file.exists()) { // 判断文件是否存在
            InputStreamReader read = new InputStreamReader(new FileInputStream(file), filename);
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;
            while ((lineTxt = bufferedReader.readLine()) != null) {
                System.out.println("lineTxt=" + lineTxt);
                if(!"".equals(lineTxt)){
                    String reds = lineTxt.split("//+")[0];
                    System.out.println(reds);
                    map.put(count, reds);
                    count ++;
                }
            }
        }

        ObjectInputStream objectInputStream;
        try
        {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));
        } catch (IOException e)
        {
            e.printStackTrace();
            return;
        }


        String line = "";
        try
        {
            Storable obj = (Storable) objectInputStream.readObject();

            map.put(obj.uuid, clazz.cast(obj));

        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    public void write()
    {
        //TODO write the new data into file.
    }

    public V get(UUID uuid)
    {
        return map.get(uuid);
    }

    public V put(UUID uuid, V value)
    {

        return map.put(uuid, value);
    }

    public boolean containsKey(UUID uuid)
    {
        return map.containsKey(uuid);
    }

    public Set<UUID> uuids()
    {
        return map.keySet();
    }


}
