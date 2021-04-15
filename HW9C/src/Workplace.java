/**
 * An interface for Workplace.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public interface Workplace
{
    int getClosingTime();

    void setClosingTime(int closingTime);

    int getOpeningTime();

    void setOpeningTime(int openingTime);

    int getWorkingHours();
}
