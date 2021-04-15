/**
 * A class for FoodItem.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 30 2021
 */
public class FoodItem extends Product
{
    private final int calories;

    public FoodItem(String name, int price, int calories)
    {
        super(name, price);
        if (calories < 0)
        {
            throw new IllegalArgumentException();
        }
        this.calories = calories;
    }

    public int getCalories()
    {
        return calories;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof FoodItem)) return false;
        if (!super.equals(o)) return false;
        FoodItem foodItem = (FoodItem) o;
        return calories == foodItem.calories;
    }

    @Override
    public String toString()
    {
        return "FoodItem<" +
                "name=" + this.getName() +
                ", price=" + this.getPrice() +
                ", calories=" + this.getCalories() +
                '>';
    }
}
