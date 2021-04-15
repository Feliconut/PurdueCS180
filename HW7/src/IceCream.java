/**
 * A class that generates IceCream
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version Mar 3, 2021
 */
public class IceCream
{
    private Restaurant[] restaurants;
    private boolean summer;
    private int newBusinesses;
    private int newBusinessThreshold;

    public IceCream(Restaurant[] restaurants, boolean summer, int newBusinesses, int newBusinessesThreshold)
    {
        this.restaurants = restaurants;
        this.summer = summer;
        this.newBusinesses = newBusinesses;
        this.newBusinessThreshold = newBusinessesThreshold;
    }

    public int openBusinesses()
    {
        int closedRestaurants = 0;
        while (newBusinesses > newBusinessThreshold)
        {
            Restaurant restaurantToClose = restaurants[0];
            for (Restaurant restaurant :
                    restaurants)
            {
                if (restaurantToClose == null)
                {
                    restaurantToClose = restaurant;
                } else
                {
                    if (restaurant.totalSales() < restaurantToClose.totalSales())
                    {
                        restaurantToClose = restaurant;
                    }
                }
            }
            restaurantToClose.closeRestaurant();
            newBusinesses--;
            closedRestaurants++;
        }
        newBusinesses = 0;
        return closedRestaurants;
    }

    public void applySummerDiscounts()
    {
        if (summer)
        {
            for (Restaurant restaurant :
                    restaurants)
            {
//                restaurant.setSummerDiscount(true);
                double ts = restaurant.totalSales();
                if (ts <= 150)
                {
                    restaurant.calculateDiscounts(0.25);
                } else if (ts <= 300)
                {
                    restaurant.calculateDiscounts(0.15);
                } else
                {
                    restaurant.calculateDiscounts(0.1);
                }
            }
        }
    }

    public Restaurant[] getRestaurants()
    {
        return restaurants;
    }

    public void setRestaurants(Restaurant[] restaurants)
    {
        this.restaurants = restaurants;
    }

    public boolean isSummer()
    {
        return summer;
    }

    public void setSummer(boolean summer)
    {
        this.summer = summer;
    }

    public int getNewBusinesses()
    {
        return newBusinesses;
    }

    public void setNewBusinesses(int newBusinesses)
    {
        this.newBusinesses = newBusinesses;
    }

    public int getNewBusinessThreshold()
    {
        return newBusinessThreshold;
    }

    public void setNewBusinessThreshold(int newBusinessThreshold)
    {
        this.newBusinessThreshold = newBusinessThreshold;
    }
}
