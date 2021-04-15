import java.util.Objects;

/**
 * A class for Product.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 30 2021
 */
public class Product implements Sellable
{
    private final String name;
    private final int price;

    public Product(String name, int price)
    {
        if (name == null)
        {
            throw new NullPointerException();
        }
        if (price < 0)
        {
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.price = price;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int getPrice()
    {
        return price;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(name, product.name);
    }

    @Override
    public String toString()
    {
        return "Product<" +
                "name=" + name +
                ", price=" + price +
                '>';
    }
}
