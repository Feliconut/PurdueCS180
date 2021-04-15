/**
 * A class that represents an exception that is thrown
 * if the there are no rooms available for booking.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class OutOfSpaceException extends Exception
{

    public OutOfSpaceException()
    {
    }

    public OutOfSpaceException(String message)
    {
        super(message);
    }
}
