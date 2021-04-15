import java.util.Arrays;
import java.util.Objects;

/**
 * A class for Hotel.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Hotel extends Building implements Residential, Workplace
{

    private final String[] amenities;
    private final String meetingHours;
    private int openingTime;
    private int closingTime;
    private int availableRooms;

    public Hotel(int numFloors, String location, int numRooms,
                 int openingTime, int closingTime, String[] amenities,
                 String meetingHours)
    {
        super(numFloors, location, numRooms);
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.amenities = amenities;
        this.meetingHours = meetingHours;
        this.availableRooms = numRooms;
    }

    @Override
    public int bookRoom() throws OutOfSpaceException
    {
        if (availableRooms > 0)
        {
            availableRooms--;
            return availableRooms;
        } else
        {
            throw new OutOfSpaceException();
        }
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Hotel)) return false;
        if (!super.equals(o)) return false;
        Hotel hotel = (Hotel) o;
        return openingTime == hotel.openingTime &&
                closingTime == hotel.closingTime &&
                availableRooms == hotel.availableRooms &&
                Arrays.equals(amenities, hotel.amenities) &&
                Objects.equals(meetingHours, hotel.meetingHours);
    }

    @Override
    public String[] getAmenities()
    {
        return amenities;
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
    public int getClosingTime()
    {
        return closingTime;
    }

    @Override
    public void setClosingTime(int closingTime)
    {
        this.closingTime = closingTime;
    }

    public int getWorkingHours()
    {
        if (meetingHours == null)
        {
            return 0;
        } else
        {
            int splitIndex = meetingHours.indexOf('-');
            int start = Integer.parseInt(meetingHours.substring(0, splitIndex));
            int end = Integer.parseInt(meetingHours.substring(splitIndex+1));
            return end - start;
        }
    }

//    public static void main(String[] args)
//    {
//        Hotel hotel = new Hotel(4, "SH", 100, 830, 1830, null, "830-1000");
//        System.out.println(hotel.getWorkingHours());
//    }
}
