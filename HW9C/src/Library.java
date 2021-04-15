/**
 * A class for Library.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Library extends Building implements Workplace
{
    private int openingTime;
    private int closingTime;

    public Library(int numFloors, String location, int numRooms, int openingTime, int closingTime)
    {
        super(numFloors, location, numRooms);
        this.openingTime = openingTime;
        this.closingTime = closingTime;
    }

    @Override
    public int getClosingTime()
    {
        return closingTime;
    }

    @Override
    public void setClosingTime(int closingTime)
    {
        this.closingTime = closingTime;
    }

    @Override
    public int getOpeningTime()
    {
        return openingTime;
    }

    @Override
    public void setOpeningTime(int openingTime)
    {
        this.openingTime = openingTime;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Library)) return false;
        if (!super.equals(o)) return false;
        Library library = (Library) o;
        return closingTime == library.closingTime && openingTime == library.openingTime;
    }

    public int getWorkingHours()
    {
        return closingTime - openingTime;
    }
}
