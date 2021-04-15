import java.util.Arrays;

/**
 * A class that generates Restaurant
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version Mar 3, 2021
 */
public class Restaurant
{
    private String name;
    private Flavor[] flavors;
    private int[][] cupsSold;
    private int hours;
    private boolean summerDiscount;

    public Restaurant()
    {
        this.flavors = new Flavor[3];
        this.cupsSold = new int[3][7];
        this.name = null;
        this.hours = 0;
        this.summerDiscount = false;
    }

    public Restaurant(String name, Flavor[] flavors, int[][] cupsSold, int hours, boolean summerDiscount)
    {
        this.name = name;
        this.flavors = flavors;
        this.cupsSold = cupsSold;
        this.hours = hours;
        this.summerDiscount = summerDiscount;
    }

    public double totalSales()
    {
        double sum = 0;
        for (int i = 0; i < flavors.length; i++)
        {
            Flavor flavor = flavors[i];
            int[] solds = cupsSold[i];
            sum += Arrays.stream(solds).sum() * flavor.getCost();
        }
        return sum;
    }

    public void calculateDiscounts(double discount)
    {
        if (summerDiscount)
        {
            for (Flavor flavor : flavors)
            {
                flavor.setCost(flavor.getCost() * (1 - discount));
            }
        }

    }

    public double closeRestaurant()
    {
        double ts = totalSales();
        name = null;
        flavors = null;
        cupsSold = null;
        hours = 0;
        summerDiscount = false;
        return ts;
    }

    public void addFlavor(Flavor newFlavor)
    {
        for (int i = 0; i < flavors.length; i++)
        {
            if (flavors[i] == null)
            {
                flavors[i] = newFlavor;
                return;
            }
        }
        System.out.println("Error, no available space!");
    }

    public Flavor[] getFlavors()
    {
        return flavors;
    }

    public void setFlavors(Flavor[] flavors)
    {
        this.flavors = flavors;
    }

    public int[][] getCupsSold()
    {
        return cupsSold;
    }

    public void setCupsSold(int[][] cupsSold)
    {
        this.cupsSold = cupsSold;
    }

    public int getHours()
    {
        return hours;
    }

    public void setHours(int hours)
    {
        this.hours = hours;
    }

    public boolean hasSummerDiscount()
    {
        return summerDiscount;
    }

    public void setSummerDiscount(boolean summerDiscount)
    {
        this.summerDiscount = summerDiscount;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
}