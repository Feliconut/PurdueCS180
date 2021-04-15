import java.util.Scanner;

import static java.lang.Math.abs;

/**
 * A framework to score football.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version February 19, 2021
 */
public class FootballScores
{

    /**
     * A framework to score football.
     *
     * <p>Purdue University -- CS18000 -- Spring 2021</p>
     *
     * @author Purdue CS
     * @version February 19, 2021
     */
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome!");
//        System.out.println("Enter sequence of scores:");
//        scanner.nextLine();
        String scores = scanner.nextLine();
//        System.out.println("Enter number of possessions to find:");
//        scanner.nextLine();
        int possessions = scanner.nextInt();
        scanner.close();

        // The values of each of the scores are defined below, you should use these int variables to make your patterns
        // Each string has 7 scores so the format of the string is oneOne
        // - oneTwo, twoOne - twoTwo, threeOne - threeTwo, ...
        int currentScoreIndex = 0;

        int oneOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int oneTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int twoOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int twoTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int threeOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int threeTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int fourOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int fourTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int fiveOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int fiveTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int sixOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int sixTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int sevenOne = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));
        currentScoreIndex += 3;
        int sevenTwo = Integer.parseInt(scores.substring(currentScoreIndex, currentScoreIndex + 2));

        //Write Implementation Here
        String result = "";
        int dif = abs(oneOne - oneTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", oneOne, oneTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", oneOne, oneTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", oneOne, oneTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", oneOne, oneTwo);
            result += ", ";
        }
        dif = abs(twoOne - twoTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", twoOne, twoTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", twoOne, twoTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", twoOne, twoTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", twoOne, twoTwo);
            result += ", ";
        }
        dif = abs(threeOne - threeTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", threeOne, threeTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", threeOne, threeTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", threeOne, threeTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", threeOne, threeTwo);
            result += ", ";
        }
        dif = abs(fourOne - fourTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", fourOne, fourTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", fourOne, fourTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", fourOne, fourTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", fourOne, fourTwo);
            result += ", ";
        }
        dif = abs(fiveOne - fiveTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", fiveOne, fiveTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", fiveOne, fiveTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", fiveOne, fiveTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", fiveOne, fiveTwo);
            result += ", ";
        }
        dif = abs(sixOne - sixTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", sixOne, sixTwo);
            result += ", ";
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", sixOne, sixTwo);
            result += ", ";
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", sixOne, sixTwo);
            result += ", ";
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", sixOne, sixTwo);
            result += ", ";
        }
        dif = abs(sevenOne - sevenTwo);
        if (dif < 9 && possessions == 1)
        {
            result += String.format("%d-%d", sevenOne, sevenTwo);
        } else if (dif >= 9 && dif < 17 && possessions == 2)
        {
            result += String.format("%d-%d", sevenOne, sevenTwo);
        } else if (dif >= 17 && dif < 25 && possessions == 3)
        {
            result += String.format("%d-%d", sevenOne, sevenTwo);
        } else if (dif >= 25 && dif < 33 && possessions == 4)
        {
            result += String.format("%d-%d", sevenOne, sevenTwo);
        }
        System.out.println(result);
    }
}
