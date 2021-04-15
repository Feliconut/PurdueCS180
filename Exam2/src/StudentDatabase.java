import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class for Manipulating Student Data.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 30 2021
 */
public class StudentDatabase
{
    private static final String PROMPT0 = "What is the name of the file?";
    private static final String PROMPT1 = "An error occurred!";
    private static final String PROMPT2 = "(1) Students below threshold";
    private static final String PROMPT3 = "(2) Students above or at threshold";
    private static final String PROMPT4 = "Do you want students below or above the age threshold? (true for above, false for below)";
    private static final String PROMPT5 = "What is the age threshold you want to define?";
    private static final String PROMPT6 = "What is the name of the file you want to output students to?";

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(PROMPT0);
        String filenameIn = scan.nextLine();
//        scan.next();
        Student[] students = readFile(filenameIn);
        if (students == null)
        {
            System.out.println(PROMPT1);
        } else
        {

            System.out.println(PROMPT4);
            boolean comparator = scan.nextBoolean();
            System.out.println(PROMPT5);
            int age = scan.nextInt();
            scan.nextLine();
            System.out.println(PROMPT6);
            String filenameOut = scan.nextLine();

            writeFile(students, comparator, age, filenameOut);
        }

    }

    public static Student[] readFile(String filename)
    {
        try
        {
            File f = new File(filename);

            FileReader fr = new FileReader(f);
            ArrayList<Student> students = new ArrayList<>();
            try (BufferedReader bfr = new BufferedReader(fr))
            {
                while (true)
                {
                    String line1 = bfr.readLine();
                    if (line1 == null)
                    {
                        break;
                    }

                    String[] line_split = line1.split(",");
                    String name = line_split[0];
                    int age = Integer.parseInt(line_split[1]);
                    long id = Long.parseLong(line_split[2]);

                    students.add(new Student(name, age, id));

                }
            }

            Student[] studentsArray = new Student[students.size()];
            return students.toArray(studentsArray);
        } catch (IOException | IndexOutOfBoundsException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeFile(Student[] students, boolean comparator, int age, String filename)
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
            for (Student student : students)
            {
                if (comparator && student.getAge() >= age || !comparator && student.getAge() < age)
                {
                    pw.println(student.toString());
                }
            }

            pw.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }
}
