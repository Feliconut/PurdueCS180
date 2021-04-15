/**
 * Student data structure.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 11 -- Walkthrough</p>
 *
 * @author Xiaoyu Liu
 * @version March 31, 2021
 */
public class Student
{
    private String purdueId;
    private String name;
    private String email;
    private int gradYear;
    private Address address;

    public Student(String purdueId, String name, String email, int gradYear)
    {
        this.purdueId = purdueId;
        this.name = name;
        this.email = email;
        this.gradYear = gradYear;
        this.address = new Address(0, "Unassigned", "Unassigned");
    }

    public Student(String purdueId, String name, String email, int gradYear, Address address)
    {
        this.purdueId = purdueId;
        this.name = name;
        this.email = email;
        this.gradYear = gradYear;
        this.address = address;
    }

    public String getPurdueId()
    {
        return purdueId;
    }

    public void setPurdueId(String purdueId)
    {
        this.purdueId = purdueId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public int getGradYear()
    {
        return gradYear;
    }

    public void setGradYear(int gradYear)
    {
        this.gradYear = gradYear;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return "Student{" +
                "purdueId='" + purdueId + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", gradYear=" + gradYear +
                ", address=" + address +
                '}';
    }
}
