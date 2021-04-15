/**
 * An interface for Residential.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public interface Residential
{
    int bookRoom() throws OutOfSpaceException;

    String[] getAmenities();
}
