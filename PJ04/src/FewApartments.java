import java.io.FileNotFoundException;

/**
 * A Class for FewApartments buildings.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version January 28, 2021
 */

public class FewApartments extends RoomsToRent
{

    public FewApartments(String roomStructureFileName,
                         String tenantInfoFilename)
            throws InvalidStudentException, FileNotFoundException
    {
        super(roomStructureFileName, tenantInfoFilename);
    }

    public double getMonthlyCost()
    {
        return 795.00;
    }
}
