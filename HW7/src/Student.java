/**
 * A class that generates Student
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March, 2021
 */
public class Student
{
    private String name;
    private String university;
    private int hardTopicsMastered;
    private int projectsCompleted;
    private boolean graduated;
    private double careerTime;

    public Student(String name, String university, int hardTopicsMastered,
                   int projectsCompleted, boolean graduated, double careerTime)
    {
        this.name = name;
        this.university = university;
        this.hardTopicsMastered = hardTopicsMastered;
        this.projectsCompleted = projectsCompleted;
        this.graduated = graduated;
        this.careerTime = careerTime;
    }

    public String getName()
    {
        return name;
    }

    public String getUniversity()
    {
        return university;
    }

    public int getHardTopicsMastered()
    {
        return hardTopicsMastered;
    }

    public void setHardTopicsMastered(int hardTopicsMastered)
    {
        this.hardTopicsMastered = hardTopicsMastered;
    }

    public int getProjectsCompleted()
    {
        return projectsCompleted;
    }

    public void setProjectsCompleted(int projectsCompleted)
    {
        this.projectsCompleted = projectsCompleted;
    }

    public boolean isGraduated()
    {
        return graduated;
    }

    public void setGraduated(boolean graduated)
    {
        this.graduated = graduated;
    }

    public double getCareerTime()
    {
        return careerTime;
    }

    public void roundCareerTime(double careerTimeNotRounded)
    {
        double temp = Math.ceil(careerTimeNotRounded / 0.5);
        this.careerTime = temp * 0.5;
    }

    public String toString()
    {
        return String.format("--- Now displaying information about the student ---\n" +
                        "Name: %s\nUniversity: %s\nHard Topics Mastered: %d\n" +
                        "Projects Completed: %d\nGraduated: %b\nCareer Time: %.1f years",
                name, university, hardTopicsMastered, projectsCompleted, graduated, careerTime);
    }
}
