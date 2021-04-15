import java.util.Scanner;

/**
 * A class to build and organize courses.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 2 2021
 */
public class CourseBuilder
{
    public static final String Q_HOW_MANY = "How many classes are you taking this semester?";
    public static final String Q_PT_FT = "Are you a part time or full time student? Enter PT for part time and FT for full time.";
    public static final String Q_CLASS_CODE = "What is the class code?";
    public static final String Q_CLASS_NUM = "What is the class number?";
    public static final String PROMPT_CLASS_LOG = "Your class log is %s";
    public static final String PROMPT_DIFFICULTY = "'Your approximate semester difficulty is %.1f";

    /**
     * Prompts the user and generates course log.
     */
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        String class_log = "";
        System.out.println(Q_HOW_MANY);
        int num_classes = scan.nextInt();
        scan.nextLine();
        System.out.println(Q_PT_FT);
        String class_mode = scan.nextLine();

        if (class_mode.equals("PT"))
        {
            class_mode = "Part";
        } else if (class_mode.equals("FT"))
        {
            class_mode = "Full";
        } else
        {
            class_mode = "Inv";
        }

        class_log += String.valueOf(num_classes);
        class_log += class_mode;

        int max_difficulty = 0;
        int sum_difficulty = 0;

        for (int i = 1; i <= num_classes; i++)
        {
            System.out.println(Q_CLASS_CODE);
            String class_name = scan.nextLine();
            System.out.println(Q_CLASS_NUM);
            String class_code = scan.nextLine();

            int difficulty = Integer.parseInt(class_code.substring(0, 1));
            sum_difficulty += difficulty;
            if (difficulty > max_difficulty)
            {
                max_difficulty = difficulty;
            }


            class_log += class_name.charAt(0);
            class_log += class_code.substring(0, 3);


        }

        double semester_difficulty;
        semester_difficulty = 1.3 * num_classes * sum_difficulty * max_difficulty;
        System.out.println(String.format(PROMPT_CLASS_LOG, class_log));
        System.out.println(String.format(PROMPT_DIFFICULTY, semester_difficulty));

    }


}
