/**
 * A class that generates Flavor
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version Mar 3, 2021
 */
public class Flavor
{
    private String name;
    private double cost;

    public Flavor()
    {
        this.name = null;
        this.cost = 0;
    }

    public Flavor(String name, double cost)
    {
        this.name = name;
        this.cost = cost;
    }

    public String getName()
    {
        return this.name;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }
}
