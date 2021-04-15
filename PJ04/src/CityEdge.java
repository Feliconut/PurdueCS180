import java.io.FileNotFoundException;

/**
 * A Class for CityEdge buildings.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version January 28, 2021
 */
public class CityEdge extends RoomsToRent
{
    private final double costMultiplier;

    public CityEdge(String roomStructureFileName,
                    String tenantInfoFilename, double costMultiplier)
            throws InvalidStudentException, FileNotFoundException
    {
        super(roomStructureFileName, tenantInfoFilename);
        this.costMultiplier = costMultiplier;
    }

    public double getMonthlyCost()
    {
        return 820.00 * costMultiplier;
    }
}
