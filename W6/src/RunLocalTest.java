import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

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

            Vector v = new Vector();

            String message = "Ensure that you are returning the right value in the getters";

            assertEquals("Ensure that you are setting the values of x, y, and z to 1 in the default constructor; "
                    + message,1, v.getX(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z to 1 in the default constructor; "
                    + message,1, v.getY(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z to 1 in the default constructor; "
                    + message,1, v.getZ(), 0.00001);


            double x = 10.0;
            double y = 20.0;
            double z = 30.0;

            Vector v1 = new Vector(x, y, z);

            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the constructor; "
                    + message, x, v1.getX(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the constructor; "
                    + message, y, v1.getY(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the constructor; "
                    + message, z, v1.getZ(), 0.00001);

            Vector v2 = new Vector(v1);
            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the copy constructor; "
                    + message, x, v2.getX(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the copy constructor; "
                    + message, y, v2.getY(), 0.00001);
            assertEquals("Ensure that you are setting the values of x, y, and z correctly in the copy constructor; "
                    + message, z, v2.getZ(), 0.00001);
        }

        @Test(timeout = 1000)
        public void testExpectedTwo() {


            double x1 = 10.0;
            double y1 = 20.0;
            double z1 = 30.0;

            double x2 = 15.0;
            double y2 = 25.0;
            double z2 = 35.0;

            Vector v1 = new Vector(x1, y1, z1);
            Vector v2 = new Vector(x2, y2, z2);

            Vector result1 = new Vector(x1 + x2, y1 + y2, z1 + z2);

            assertEquals("Ensure that your add method returns the right value", result1, v1.add(v2));

            Vector result2 = new Vector(x1 - x2, y1 - y2, z1 - z2);

            assertEquals("Ensure that your subtract method returns the right value", result2, v1.subtract(v2));
        }



    }
}
