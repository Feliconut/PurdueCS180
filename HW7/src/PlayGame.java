import java.util.Scanner;

/**
 * A class that Hosts the game
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March, 2021
 */
public class PlayGame
{

    public static void main(String[] args)
    {
        final String welcome = "Welcome to Students of CS! Good luck with your college career!";
        final String name = "Enter your name:";
        final String university = "Enter your university:";
        final String customLevel = "Would you like to customize the number of levels?";
        final String changeLevel = "Enter the number of levels to add or remove(use negative value to remove):";
        final String customTopics = "Would you like to customize the number of hard topics per level?";
        final String changeTopics = "Enter the number of hard topics per level to " +
                "add or remove(use negative value to remove):";
        final String customProjects = "Would you like to customize the number of projects per level?";
        final String changeProjects = "Enter the number of projects per level to " +
                "add or remove(use negative value to remove):";
        final String numAcedTests = "Enter the number of aced tests in level ";
        final String topicsNotUnderstood = "Enter the number of topics not understood in level ";
        final String regularProjects = "Enter the number of regular projects incomplete in level ";
        final String capstoneProjects = "Enter the number of capstone projects incomplete in level ";
        final String timeTaken = "Enter the time taken in level ";
        final String failedLevel = "General information about the failed level is as follows:";
        final String playAgainBeginning = "Would you like to play level ";
        final String playAgainEnd = " again?";
        final String descriptionOne = "Object-Oriented Programming and Problem Solving";
        final String descriptionTwo = "Programming in C";
        final String descriptionThree = "Computer Architecture";
        final String descriptionFour = "Data Structures & Algorithms";
        final String descriptionFive = "Systems Programming";
        final String descriptionSix = "Software Engineering I";
        final String descriptionSeven = "Compilers";
        final String descriptionEight = "Operating Systems";
        final String descriptionNine = "Introduction to Analysis and Algorithms";
        final String descriptionTen = "Data Mining and Machine Learning";
        final String descriptionEleven = "Software Testing";
        final String descriptionTwelve = "Software Engineering II";
        final String diabolical = "Diabolical Software Engineering Course";
        final String congratulationsBeginning = "Congratulations ";
        final String congratulationsEnd = ", you have graduated!";
        final String thankYou = "Thank you for playing Students of CS!";
        Scanner scan = new Scanner(System.in);
        System.out.println(welcome);
        System.out.println(name);
        String nameIn = scan.nextLine();
        System.out.println(university);
        String universityIn = scan.nextLine();

        Student student = new Student(nameIn, universityIn, 0, 0, false, 0);

        GameSettings gameSettings = new GameSettings();

        int topicsChange = 0;
        int levelsChange = 0;
        int projectsChange = 0;
        System.out.println(customLevel);
        if (scan.nextLine().toLowerCase().equals("yes"))
        {
            System.out.println(changeLevel);
            levelsChange = scan.nextInt();
            scan.nextLine();
        }
        System.out.println(customTopics);
        if (scan.nextLine().toLowerCase().equals("yes"))
        {
            System.out.println(changeTopics);
            topicsChange = scan.nextInt();
            scan.nextLine();
        }
        System.out.println(customProjects);
        if (scan.nextLine().toLowerCase().equals("yes"))
        {
            System.out.println(changeProjects);
            projectsChange = scan.nextInt();
            scan.nextLine();
        }
        gameSettings.customize(levelsChange, topicsChange, projectsChange);

        int levelsCompleted = 0;
        double rawCareerTime = 0;
        while (levelsCompleted < gameSettings.getLevels())
        {
            int levelNumber = levelsCompleted + 1;
            System.out.println(numAcedTests + levelNumber + ":");
            int acedTests = scan.nextInt();
            scan.nextLine();
            System.out.println(topicsNotUnderstood + levelNumber + ":");
            int hardTopics = scan.nextInt();
            scan.nextLine();
            System.out.println(regularProjects + levelNumber + ":");
            int regularProjectsIncomplete = scan.nextInt();
            scan.nextLine();
            System.out.println(capstoneProjects + levelNumber + ":");
            int capstoneProjectsIncomplete = scan.nextInt();
            scan.nextLine();
            System.out.println(timeTaken + levelNumber + ":");
            double time = scan.nextDouble();
            scan.nextLine();

            Level level = new Level(levelNumber, acedTests, hardTopics,
                    regularProjectsIncomplete, capstoneProjectsIncomplete, time);

            rawCareerTime += time;
            student.roundCareerTime(rawCareerTime);

            if (level.getPassed())
            {
                student.setHardTopicsMastered(student.getHardTopicsMastered() +
                        gameSettings.getHardTopicsPerLevel() - hardTopics);
                student.setProjectsCompleted(student.getProjectsCompleted() +
                        gameSettings.getProjectsPerLevel() - regularProjectsIncomplete - capstoneProjectsIncomplete);

                levelsCompleted++;
            } else
            {
                System.out.println(failedLevel);
                System.out.println(level.toString());
                System.out.println(playAgainBeginning + levelNumber + playAgainEnd);
                if (!scan.nextLine().toLowerCase().equals("yes"))
                {
                    student.setHardTopicsMastered(student.getHardTopicsMastered() +
                            gameSettings.getHardTopicsPerLevel() - hardTopics);
                    student.setProjectsCompleted(student.getProjectsCompleted() +
                            gameSettings.getProjectsPerLevel()
                            - regularProjectsIncomplete - capstoneProjectsIncomplete);

                    System.out.println(student.toString());
                    System.out.println(thankYou);
                    return;
                }
            }
        }
        student.setGraduated(true);
        System.out.println(congratulationsBeginning + student.getName() + congratulationsEnd);
        System.out.println(gameSettings.toString());
        System.out.println(student.toString());
        System.out.println(thankYou);

    }

}