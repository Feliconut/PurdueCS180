import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version January 19, 2021
 */
public class RunLocalTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Spring 2021</p>
     *
     * @author Purdue CS
     * @version January 19, 2021
     */
    public static class TestCase {
        private final PrintStream originalOutput = System.out;
        private final InputStream originalSysin = System.in;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;

        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalSysin);
            System.setOut(originalOutput);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @SuppressWarnings("SameParameterValue")
        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }


        @Test(timeout = 1000)
        public void testExpectedOne() {

            String input = "Percy\nBoston College\nno\nno\nno\n0\n0\n0\n0\n0.3\n0\n2\n1\n0\n0.4\nno\n";

            String expected = "Welcome to Students of CS! Good luck with your college career!\nEnter your name:\nEnter your university:\n"
                    + "Would you like to customize the number of levels?\n"
                    + "Would you like to customize the number of hard topics per level?\nWould you like to customize the number of projects per level?\n"
                    + "Enter the number of aced tests in level 1:\nEnter the number of topics not understood in level 1:\n"
                    + "Enter the number of regular projects incomplete in level 1:\nEnter the number of capstone projects incomplete in level 1:\n"
                    + "Enter the time taken in level 1:\nEnter the number of aced tests in level 2:\n"
                    + "Enter the number of topics not understood in level 2:\nEnter the number of regular projects incomplete in level 2:\n"
                    + "Enter the number of capstone projects incomplete in level 2:\nEnter the time taken in level 2:\n"
                    + "General information about the failed level is as follows:\nLevel Number: 2\n"
                    + "Description: Programming in C\nDifficulty: Easy\nWould you like to play level 2 again?\n"
                    + "--- Now displaying information about the student ---\n"
                    + "Name: Percy\nUniversity: Boston College\nHard Topics Mastered: 6\nProjects Completed: 9\n"
                    + "Graduated: false\nCareer Time: 1.0 years\n"
                    + "Thank you for playing Students of CS!\n";

            try {
                receiveInput(input);
                PlayGame.main(new String[0]);
                String stuOut = getOutput();

                Assert.assertEquals("Ensure your playGame output is correct!", expected, stuOut);

            } catch (Exception e) {

                e.printStackTrace();
                Assert.fail();

            }
        }




    }
}
