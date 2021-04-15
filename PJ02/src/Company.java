/**
 * A class for Company.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Company
{
    private String name;
    private int[] prices;

    public Company(String name, int[] prices)
    {
        this.name = name;
        this.prices = prices;
    }

    public static Company parseCompany(String line) throws InvalidPriceException
    {
        int columnSplit = line.indexOf(':');
        String companyName = line.substring(0, columnSplit);
        String numListString = line.substring(columnSplit + 1);
        String[] pricesString = numListString.split(",");
        int[] prices = new int[pricesString.length];
        for (int i = 0; i < pricesString.length; i++)
        {
            prices[i] = Validator.checkPrice(Integer.parseInt(pricesString[i]));
        }
        return new Company(companyName, prices);
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int[] getPrices()
    {
        return prices;
    }

    public void setPrices(int[] prices)
    {
        this.prices = prices;
    }
}
