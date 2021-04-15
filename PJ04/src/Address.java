/**
 * Data Structure for Address.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version January 28, 2021
 */
public class Address
{
    private final int roomNumber;
    private final String building;
    private final String street;

    public Address(int roomNumber, String building, String street)
    {
        this.roomNumber = roomNumber;
        this.building = building;
        this.street = street;
    }

    public int getRoomNumber()
    {
        return roomNumber;
    }

    public String getBuilding()
    {
        return building;
    }

    public String getStreet()
    {
        return street;
    }

    @Override
    public String toString()
    {
        return "Address{" +
                "street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", roomNumber=" + roomNumber +
                '}';
    }
}
