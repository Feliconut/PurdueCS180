/**
 * An Exception for Occupied Room.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class OccupiedRoomException extends Exception
{
    public OccupiedRoomException()
    {
    }

    public OccupiedRoomException(String message)
    {
        super(message);
    }
}
