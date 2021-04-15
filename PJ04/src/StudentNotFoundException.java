/**
 * An Exception for student not found.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class StudentNotFoundException extends Exception
{
    public StudentNotFoundException()
    {
    }

    public StudentNotFoundException(String message)
    {
        super(message);
    }
}
