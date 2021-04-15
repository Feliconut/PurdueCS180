import java.io.*;
import java.util.ArrayList;

/**
 * A Class for Residence Hall.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class ResidenceHall implements Leasable
{
    private final ArrayList<Integer> availableDorms;
    private final ArrayList<Student> currentResidents;
    private final String residentHallName;
    private final String streetName;

    public ResidenceHall(String filename) throws InvalidStudentException, FileNotFoundException
    {
        this.availableDorms = new ArrayList<>();
        this.currentResidents = new ArrayList<>();
        String tempResidentHallName = "Undefined";
        String tempStreetName = "Undefined";
        try
        {
            File f = new File(filename);
            FileReader fr = new FileReader(f);

            try (BufferedReader bfr = new BufferedReader(fr))
            {
                String line = bfr.readLine();
                String[] lineSplit = line.split(", ");

                tempResidentHallName = lineSplit[0];
                tempStreetName = lineSplit[1];

                while (true)
                {
                    line = bfr.readLine();
                    if (line == null)
                    {
                        break;
                    }
                    lineSplit = line.split(": ");

                    int roomNo = Integer.parseInt(lineSplit[0]);
                    this.availableDorms.add(roomNo);

                    line = lineSplit[1];
                    if (!line.equals("Unknown"))
                    {
                        int gradYear;
                        String email;
                        String name;
                        String purdueId;
                        Student student;

                        lineSplit = line.split(", ");
                        try
                        {
                            purdueId = lineSplit[0];
                            name = lineSplit[1];
                            email = lineSplit[2];
                            gradYear = Integer.parseInt(lineSplit[3]);
                        } catch (Exception e)
                        {
                            throw new InvalidStudentException();
                        }
                        student = new Student(
                                purdueId, name, email, gradYear
                        );
                        this.signContract(student, roomNo);
                    }
                }
            }


        } catch (FileNotFoundException e)
        {
            throw e;
        } catch (IOException | OccupiedRoomException e)
        {
            e.printStackTrace();
        }
        this.residentHallName = tempResidentHallName;
        this.streetName = tempStreetName;
    }


    @Override
    public int countResidents()
    {
        return currentResidents.size();
    }

    @Override
    public double getContractCost()
    {
        return 4860.00;
    }

    @Override
    public int signContract(Student student) throws OccupiedRoomException
    {
        if (availableDorms.size() > 0)
        {
            int roomNo = availableDorms.remove(0);
            student.setAddress(new Address(roomNo, residentHallName, streetName));
            currentResidents.add(student);
            return roomNo;
        } else
        {
            throw new OccupiedRoomException();
        }
    }

    @Override
    public void signContract(Student student, int roomNo) throws OccupiedRoomException
    {
        if (availableDorms.contains(roomNo))
        {
            availableDorms.remove((Integer) roomNo);
            student.setAddress(new Address(roomNo, residentHallName, streetName));
            currentResidents.add(student);
        } else
        {
            throw new OccupiedRoomException();
        }

    }

    @Override
    public int cancelContract(String purdueId) throws StudentNotFoundException
    {
        for (Student student : currentResidents)
        {
            if (student.getPurdueId().equals(purdueId))
            {
                currentResidents.remove(student);
                int roomNo = student.getAddress().getRoomNumber();
                availableDorms.add(roomNo);
                return roomNo;

            }
        }
        throw new StudentNotFoundException();
    }


    @Override
    public int countFloormates(int floor)
    {
        int count = 0;
        for (Student student :
                currentResidents)
        {
            int roomNo = student.getAddress().getRoomNumber();
            int thisFloor = Integer.parseInt(String.valueOf(roomNo).substring(0, 1));
            if (floor == thisFloor)
            {
                count++;
            }
        }
        return count;
    }


    @Override
    public ArrayList<Student> listResidents()
    {
        return new ArrayList<>(currentResidents);
    }


    @Override
    public Student viewResident(int roomNo) throws StudentNotFoundException
    {
        for (Student student : currentResidents)
        {
            if (student.getAddress().getRoomNumber() == roomNo)
            {
                return student;
            }
        }
        throw new StudentNotFoundException();
    }

    @Override
    public void saveResidentInfoToFile(String filename)
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
            pw.println(residentHallName + ", " + streetName);


            for (Student student : currentResidents)
            {
                pw.println(student.getAddress().getRoomNumber() +
                        ": " + student.getPurdueId() +
                        ", " + student.getName() +
                        ", " + student.getEmail() +
                        ", " + student.getGradYear());
            }

            for (int roomNo : availableDorms)
            {
                pw.println(roomNo + ": Unknown");
            }

            pw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
