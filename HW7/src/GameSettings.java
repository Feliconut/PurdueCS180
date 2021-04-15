/**
 * A class that generates Game Settings
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March, 2021
 */
public class GameSettings
{
    private int levels;
    private int hardTopicsPerLevel;
    private int projectsPerLevel;

    public GameSettings()
    {
        this.levels = 12;
        this.hardTopicsPerLevel = 4;
        this.projectsPerLevel = 5;
    }

    public int getLevels()
    {
        return levels;
    }

    public int getProjectsPerLevel()
    {
        return projectsPerLevel;
    }

    public int getHardTopicsPerLevel()
    {
        return hardTopicsPerLevel;
    }

    public void customize(int levelsChange, int topicsChange, int projectsChange)
    {
        this.levels += levelsChange;
        this.hardTopicsPerLevel += topicsChange;
        this.projectsPerLevel += projectsChange;
    }

    public String toString()
    {
        return String.format("Course Obstacles:\nLevels: %d\nHard Topics Per Level: %d\nProjects Per Level: %d",
                levels, hardTopicsPerLevel, projectsPerLevel);
    }

}
