import java.util.Scanner;

/**
 * A class to calculate.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version January 19, 2021
 */
public class Calculator
{
    public static final String MENU = "Calculator" + "\n" +
            "1. Addition" + "\n" +
            "2. Subtraction" + "\n" +
            "3. Multiplication" + "\n" +
            "4. Division" + "\n" +
            "5. Factorial" + "\n" +
            "6. Quit" + "\n" +
            "Enter your selection:";
    public static final String ADDITION_PROMPT = "Enter each integer you would like added:";
    public static final String ADDITION_RESULT = "The sum of all these numbers is ";
    public static final String SUBTRACTION_PROMPT = "Enter each integer you would like subtracted:";
    public static final String SUBTRACTION_RESULT = "The difference of all these numbers is ";
    public static final String MULTIPLICATION_PROMPT = "Enter each integer you would like multiplied:";
    public static final String MULTIPLICATION_RESULT = "The product of all these numbers is ";
    public static final String DIVISION_PROMPT = "Enter each integer you would like divided:";
    public static final String DIVISION_RESULT = "The quotient of all these numbers is ";
    public static final String FACTORIAL_PROMPT = "Enter each integer you would like the factorial of:";
    public static final String FACTORIAL_RESULT = "The factorial of ";
    public static final String EXIT_MESSAGE = "Ending Calculator...";
    public static final String ERROR_MESSAGE = "Error, invalid option. Please select again.";


    /**
     * A method to calculate.
     *
     * <p>Purdue University -- CS18000 -- Spring 2021</p>
     *
     * @author Purdue CS
     * @version January 19, 2021
     */
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        int res;
        int in;
        boolean firstNum;
        while (true)
        {
            System.out.println(MENU);
            switch (scan.nextInt())
            {
                case 1:
                    System.out.println(ADDITION_PROMPT);
                    res = 0;
                    while (true)
                    {
                        in = scan.nextInt();
                        if (in == 0)
                        {
                            System.out.println(ADDITION_RESULT + String.valueOf(res) + ".");
                            break;
                        } else
                        {
                            res += in;
                        }
                    }
                    break;
                case 2:
                    System.out.println(SUBTRACTION_PROMPT);
                    firstNum = true;
                    res = 0;
                    while (true)
                    {
                        in = scan.nextInt();
                        if (in == 0)
                        {
                            System.out.println(SUBTRACTION_RESULT + String.valueOf(res) + ".");
                            break;
                        } else if (firstNum)
                        {
                            res = in;
                            firstNum = false;
                        } else
                        {
                            res -= in;
                        }
                    }
                    break;
                case 3:
                    System.out.println(MULTIPLICATION_PROMPT);
                    res = 1;
                    while (true)
                    {
                        in = scan.nextInt();
                        if (in == 0)
                        {
                            System.out.println(MULTIPLICATION_RESULT + String.valueOf(res) + ".");
                            break;
                        } else
                        {
                            res *= in;
                        }
                    }
                    break;
                case 4:
                    System.out.println(DIVISION_PROMPT);
                    firstNum = true;
                    res = 0;
                    while (true)
                    {
                        in = scan.nextInt();
                        if (in == 0)
                        {
                            System.out.println(DIVISION_RESULT + String.valueOf(res) + ".");
                            break;
                        } else if (firstNum)
                        {
                            res = in;
                            firstNum = false;
                        } else
                        {
                            res /= in;
                        }
                    }
                    break;
                case 5:
                    System.out.println(FACTORIAL_PROMPT);
                    res = 1;
                    while (true)
                    {
                        in = scan.nextInt();
                        if (in == 0)
                        {
                            break;
                        } else
                        {
                            res = factorial(in);
                            System.out.println(FACTORIAL_RESULT + String.valueOf(in) +
                                    " is " + String.valueOf(res) + ".");
                        }
                    }
                    break;
                case 6:
                    System.out.println(EXIT_MESSAGE);
                    return;
                default:
                    System.out.println(ERROR_MESSAGE);

            }
        }
    }

    static int factorial(int n)
    {
        return n <= 1 ? 1 : n * factorial(n - 1);
    }
}