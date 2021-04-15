/**
 * A class to build and organize coffee producers.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 2 2021
 */
public class Producer
{
    private String name;
    private String location;

    /**
     * Generate a Producer instance by supplying the
     * name and location of that coffee producer.
     */
    public Producer(String name, String location)
    {
        this.name = name;
        this.location = location;
    }

    /**
     * Get the name of producer.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Set the name of producer.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    /**
     * Get the location of producer.
     */
    public String getLocation()
    {
        return location;
    }

    /**
     * Set the location of producer.
     */
    public void setLocation(String location)
    {
        this.location = location;
    }

    /**
     * String expression of coffee producer.
     */
    public String toString()
    {
        return String.format("Producer<name=%s, location=%s>",
                name, location);
    }

}
