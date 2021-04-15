/**
 * A class that represents a Student. 
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS 
 * @version January 19, 2021
 */
public class Student extends Person {
    private int grade;

    public Student(String name, int age, int grade) {
        super(name, age);
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }
}