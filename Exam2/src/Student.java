/**
 * A class for Student.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 30 2021
 */
public class Student
{
    private final String name;
    private final int age;
    private final long id;

    public Student(String name, int age, long id)
    {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    public Student(Student student)
    {
        this.name = student.getName();
        this.age = student.getAge();
        this.id = student.getId();
    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public long getId()
    {
        return id;
    }

    @Override
    public String toString()
    {
        return "Student<" +
                "name=" + name +
                ", age=" + age +
                ", id=" + id +
                '>';
    }
}
