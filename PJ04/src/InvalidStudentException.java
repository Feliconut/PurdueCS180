/**
 * An Exception for Invalid Student Format.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */

public class InvalidStudentException extends Exception
{
    public InvalidStudentException()
    {
    }

    public InvalidStudentException(String message)
    {
        super(message);
    }
}
