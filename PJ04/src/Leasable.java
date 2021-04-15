import java.util.ArrayList;

/**
 * An Interface for Leasable Building.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public interface Leasable
{
    int cancelContract(String purdueId) throws StudentNotFoundException;

    int countFloormates(int floor);

    int countResidents();

    double getContractCost();

    ArrayList<Student> listResidents();

    void saveResidentInfoToFile(String filename);

    int signContract(Student student) throws OccupiedRoomException;

    void signContract(Student student, int roomNo) throws OccupiedRoomException;

    Student viewResident(int roomNo) throws StudentNotFoundException;
}
