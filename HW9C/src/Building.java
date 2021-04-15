/**
 * A super class for Building.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Building
{
    private final int numFloors;
    private final String location;
    private final int numRooms;


    public Building(int numFloors, String location, int numRooms)
    {
        this.numFloors = numFloors;
        this.location = location;
        this.numRooms = numRooms;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Building)) return false;
        Building building = (Building) o;
        return numFloors == building.numFloors && numRooms == building.numRooms && location.equals(building.location);
    }


    public String getLocation()
    {
        return location;
    }

    public int getNumFloors()
    {
        return numFloors;
    }

    public int getNumRooms()
    {
        return numRooms;
    }

    public double roomsPerFloor()
    {
        return 1. * getNumRooms() / getNumFloors();
    }
}
