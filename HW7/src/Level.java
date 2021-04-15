/**
 * A class that generates Level
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March, 2021
 */
public class Level
{
    private int levelNumber;
    private String description;
    private String difficulty;
    private int acedTests;
    private int hardTopicsIncomplete;
    private int regularProjectsIncomplete;
    private int capstoneProjectsIncomplete;
    private double time;
    private boolean passed;

    public Level(int levelNumber, int acedTests, int hardTopicsIncomplete,
                 int regularProjectsIncomplete, int capstoneProjectsIncomplete, double time)
    {
        this.levelNumber = levelNumber;
        this.acedTests = acedTests;
        this.hardTopicsIncomplete = hardTopicsIncomplete;
        this.regularProjectsIncomplete = regularProjectsIncomplete;
        this.capstoneProjectsIncomplete = capstoneProjectsIncomplete;
        this.time = time;
        this.description = switch (levelNumber)
                {
                    case 1 -> "Object-Oriented Programming and Problem Solving";
                    case 2 -> "Programming in C";
                    case 3 -> "Computer Architecture";
                    case 4 -> "Data Structures & Algorithms";
                    case 5 -> "Systems Programming";
                    case 6 -> "Software Engineering I";
                    case 7 -> "Compilers";
                    case 8 -> "Operating Systems";
                    case 9 -> "Introduction to Analysis and Algorithms";
                    case 10 -> "Data Mining and Machine Learning";
                    case 11 -> "Software Testing";
                    case 12 -> "Software Engineering II";
                    default -> "Diabolical Software Engineering Course";
                };
        this.difficulty = levelNumber <= 4 ? "Easy" :
                (levelNumber <= 8 ? "Intermediate" :
                        (levelNumber <= 12 ? "Hard" : "Diabolical"));
        this.passed = capstoneProjectsIncomplete <= 0 &&
                (hardTopicsIncomplete + regularProjectsIncomplete <= acedTests + 2);
    }

    public boolean getPassed()
    {
        return passed;
    }

    public int getCapstoneProjectsIncomplete()
    {
        return capstoneProjectsIncomplete;
    }

    public int getLevelNumber()
    {
        return levelNumber;
    }

    public String getDescription()
    {
        return description;
    }

    public String getDifficulty()
    {
        return difficulty;
    }

    public int getAcedTests()
    {
        return acedTests;
    }

    public int getHardTopicsIncomplete()
    {
        return hardTopicsIncomplete;
    }

    public int getRegularProjectsIncomplete()
    {
        return regularProjectsIncomplete;
    }

    public double getTime()
    {
        return time;
    }

    public String toString()
    {
        return "Level Number: " + levelNumber + "\n" +
                "Description: " + description + "\n" +
                "Difficulty: " + difficulty;
    }
}
