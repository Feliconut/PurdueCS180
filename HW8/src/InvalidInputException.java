/**
 * An exception for invalid input.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 11 2021
 */
public class InvalidInputException extends Exception
{
    public InvalidInputException(String errorMessage)
    {
        super(errorMessage);
    }

    public InvalidInputException()
    {
        super();
    }
}
