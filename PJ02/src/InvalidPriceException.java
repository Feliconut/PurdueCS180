/**
 * A exception for invalid price.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class InvalidPriceException extends Exception
{
    public InvalidPriceException(String message)
    {
        super(message);
    }

    public InvalidPriceException()
    {
    }
}
