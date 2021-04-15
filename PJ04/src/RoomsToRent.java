import java.io.*;
import java.util.ArrayList;

/**
 * A base class for rentable rooms.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class RoomsToRent
{
    private final ArrayList<Integer> availableRooms;
    private final ArrayList<Student> currentTenants;
    private final String buildingName;
    private final String streetName;

    public RoomsToRent(
            String roomStructureFileName, String tenantInfoFilename)
            throws InvalidStudentException, FileNotFoundException
    {
        this.availableRooms = new ArrayList<>();
        this.currentTenants = new ArrayList<>();
        String tempBuildingName = "Undefined";
        String tempStreetName = "Undefined";
        try
        {
            File f = new File(roomStructureFileName);

            FileReader fr = new FileReader(f);
            try (BufferedReader bfr = new BufferedReader(fr))
            {
                while (true)
                {
                    String line1 = bfr.readLine();
                    if (line1 == null)
                    {
                        break;
                    }
                    String[] roomsStrings = line1.split(", ");
                    for (String roomString :
                            roomsStrings)
                    {
                        this.availableRooms.add(Integer.parseInt(roomString));
                    }

                }
            }


        } catch (FileNotFoundException e)
        {
            throw e;
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            File f = new File(tenantInfoFilename);
            FileReader fr = new FileReader(f);

            try (BufferedReader bfr = new BufferedReader(fr))
            {
                String line = bfr.readLine();
                String[] lineSplit = line.split(", ");

                tempBuildingName = lineSplit[0];
                tempStreetName = lineSplit[1];

                while (true)
                {

                    int gradYear;
                    int roomNumber;
                    String email;
                    String name;
                    String purdueId;
                    Student student;

                    line = bfr.readLine();
                    if (line == null)
                    {
                        break;
                    }

                    lineSplit = line.split(", ");
                    try
                    {
                        purdueId = lineSplit[0];
                        name = lineSplit[1];
                        email = lineSplit[2];
                        gradYear = Integer.parseInt(lineSplit[3]);
                        roomNumber = Integer.parseInt(lineSplit[4]);
                    } catch (Exception e)
                    {
                        throw new InvalidStudentException();
                    }
                    student = new Student(
                            purdueId, name, email, gradYear
                    );
                    this.addTenant(student, roomNumber);
                }
            }


        } catch (IOException | OccupiedRoomException e)
        {
            e.printStackTrace();
        }
        this.buildingName = tempBuildingName;
        this.streetName = tempStreetName;
    }

    public int getAvailability()
    {
        return availableRooms.size();
    }

    public double getMonthlyCost()
    {
        return 800.00;
    }

    public void addTenant(Student student, int roomNo) throws OccupiedRoomException
    {
        if (availableRooms.contains(roomNo))
        {
            availableRooms.remove((Integer) roomNo);
            student.setAddress(new Address(roomNo, buildingName, streetName));
            currentTenants.add(student);
        } else
        {
            throw new OccupiedRoomException();
        }
    }

    public int addTenant(Student student) throws OccupiedRoomException
    {
        if (availableRooms.size() > 0)
        {
            int roomNo = availableRooms.remove(0);
            student.setAddress(new Address(roomNo, buildingName, streetName));
            currentTenants.add(student);
            return roomNo;
        } else
        {
            throw new OccupiedRoomException();
        }
    }

    public Student locateTenant(String purdueId) throws StudentNotFoundException
    {
        for (Student student : currentTenants)
        {
            if (student.getPurdueId().equals(purdueId))
            {
                return student;
            }
        }
        throw new StudentNotFoundException();
    }

    public ArrayList<Student> listTenants()
    {
        return new ArrayList<>(currentTenants);
    }

    public int removeTenant(String purdueId) throws StudentNotFoundException
    {
        for (Student student : currentTenants)
        {
            if (student.getPurdueId().equals(purdueId))
            {
                currentTenants.remove(student);
                int roomNo = student.getAddress().getRoomNumber();
                availableRooms.add(roomNo);
                return roomNo;

            }
        }
        throw new StudentNotFoundException();
    }

    public void saveTenantInfoToFile(String filename)
    {
        File f = new File(filename);
        try
        {
            f.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            pw.println(buildingName + ", " + streetName);
            for (Student student : currentTenants)
            {
                pw.println(student.getPurdueId() +
                        ", " + student.getName() +
                        ", " + student.getEmail() +
                        ", " + student.getGradYear() +
                        ", " + student.getAddress().getRoomNumber());
            }

            pw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

}
