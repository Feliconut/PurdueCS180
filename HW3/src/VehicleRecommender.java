import org.junit.After;
import org.junit.Before;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.testng.annotations.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 * A framework to recommend vehicle.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version January 19, 2021
 */
public class VehicleRecommender {
    public static final String WELCOME_MESSAGE = "Welcome to the Vehicle Recommender! Would you like to participate?";
    public static final String TRAVEL_MEDIUM = "Does the vehicle need to travel on land, water, air, or space?";
    public static final String LAND_PASSENGERS = "How many passengers does the vehicle need to transport?";
    public static final String LAND_TRAVEL_SPEED = "Do you need to travel quickly?";
    public static final String LAND_FUEL_ECONOMY = "Is fuel economy important?";
    public static final String LAND_COMMERCIAL = "Do you need to transport passengers commercially?";
    public static final String WATER_GOODS = "Do you plan on carrying a large amount of material?";
    public static final String WATER_AIRPLANES = "Do airplanes need to take off from the vehicle?";
    public static final String WATER_SPEED = "Is speed important?";
    public static final String WATER_LUXURY = "Is luxury important?";
    public static final String WATER_UNDERWATER = "Do you need to travel underwater?";
    public static final String AIR_QUICKLY = "Do you need to travel quickly?";
    public static final String AIR_PASSENGERS = "Can other travelers join you?";
    public static final String AIR_WATER = "Do you need to land in the water?";
    public static final String AIR_LANDSCAPES = "Can you use natural landscapes when taking off?";
    public static final String NO_PARTICIPATION = "That's too bad. Maybe next time!";
    public static final String GOODBYE_MESSAGE = "Thank you for participating. Goodbye!";
    public static final String ERROR_MESSAGE = "Error: Unable to process response. Ending program... ";


    /**
     * A method to recommend vehicle.
     *
     * <p>Purdue University -- CS18000 -- Spring 2021</p>
     *
     * @author Purdue CS
     * @version January 19, 2021
     */
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        System.out.println(WELCOME_MESSAGE);
        switch (scan.nextLine().toLowerCase()) {
            case "yes" -> {
                System.out.println(TRAVEL_MEDIUM);
                switch (scan.nextLine().toLowerCase()) {
                    case "land" -> {
                        System.out.println(LAND_PASSENGERS);
                        switch (scan.nextLine().toLowerCase()) {
                            case "1" -> {
                                System.out.println(LAND_TRAVEL_SPEED);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Sports car!");
                                    case "no" -> System.out.println("You need a Bicycle!");
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            case "1-4" -> {
                                System.out.println(LAND_FUEL_ECONOMY);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Hybrid!");
                                    case "no" -> System.out.println("You need a Sedan!");
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            case "4+" -> {
                                System.out.println(LAND_COMMERCIAL);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Greyhound bus!");
                                    case "no" -> System.out.println("You need a Mini-van!");
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            default -> {
                                System.out.println(ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    case "water" -> {
                        System.out.println(WATER_GOODS);
                        switch (scan.nextLine().toLowerCase()) {
                            case "yes" -> {
                                System.out.println(WATER_AIRPLANES);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Aircraft carrier!");
                                    case "no" -> System.out.println("You need a Cargo ship!");
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            case "no" -> {
                                System.out.println(WATER_SPEED);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> {
                                        System.out.println(WATER_LUXURY);
                                        switch (scan.nextLine().toLowerCase()) {
                                            case "yes" -> System.out.println("You need a Yacht!");
                                            case "no" -> System.out.println("You need a Speedboat!");
                                            default -> {
                                                System.out.println(ERROR_MESSAGE);
                                                return;
                                            }
                                        }
                                    }
                                    case "no" -> {
                                        System.out.println(WATER_UNDERWATER);
                                        switch (scan.nextLine().toLowerCase()) {
                                            case "yes" -> System.out.println("You need a Submarine!");
                                            case "no" -> System.out.println("You need a Sailboat!");
                                            default -> {
                                                System.out.println(ERROR_MESSAGE);
                                                return;
                                            }
                                        }
                                    }
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            default -> {
                                System.out.println(ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    case "air" -> {
                        System.out.println(AIR_QUICKLY);
                        switch (scan.nextLine().toLowerCase()) {
                            case "yes" -> {
                                System.out.println(AIR_PASSENGERS);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Passenger jet!");
                                    case "no" -> System.out.println("You need a Private jet!");
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            case "no" -> {
                                System.out.println(AIR_WATER);
                                switch (scan.nextLine().toLowerCase()) {
                                    case "yes" -> System.out.println("You need a Seaplane!");
                                    case "no" -> {
                                        System.out.println(AIR_LANDSCAPES);
                                        switch (scan.nextLine().toLowerCase()) {
                                            case "yes" -> System.out.println("You need a Hang glider!");
                                            case "no" -> System.out.println("You need a Hover craft!");
                                            default -> {
                                                System.out.println(ERROR_MESSAGE);
                                                return;
                                            }
                                        }
                                    }
                                    default -> {
                                        System.out.println(ERROR_MESSAGE);
                                        return;
                                    }
                                }
                            }
                            default -> {
                                System.out.println(ERROR_MESSAGE);
                                return;
                            }
                        }
                    }
                    case "space" -> System.out.println("You need a Space ship!");
                    default -> {
                        System.out.println(ERROR_MESSAGE);
                        return;
                    }
                }
                System.out.println(GOODBYE_MESSAGE);
            }
            case "no" -> System.out.println(NO_PARTICIPATION);
            default -> System.out.println(ERROR_MESSAGE);
        }
    }
}

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version January 19, 2021
 */
class RunLocalTest {
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
        // Each of the correct outputs
        public static final String WELCOME_MESSAGE = "Welcome to the Vehicle Recommender! Would you like to participate?";
        public static final String TRAVEL_MEDIUM = "Does the vehicle need to travel on land, water, air, or space?";
        public static final String LAND_PASSENGERS = "How many passengers does the vehicle need to transport?";
        public static final String LAND_TRAVEL_SPEED = "Do you need to travel quickly?";
        public static final String LAND_FUEL_ECONOMY = "Is fuel economy important?";
        public static final String LAND_COMMERCIAL = "Do you need to transport passengers commercially?";
        public static final String WATER_GOODS = "Do you plan on carrying a large amount of material?";
        public static final String WATER_AIRPLANES = "Do airplanes need to take off from the vehicle?";
        public static final String WATER_SPEED = "Is speed important?";
        public static final String WATER_LUXURY = "Is luxury important?";
        public static final String WATER_UNDERWATER = "Do you need to travel underwater?";
        public static final String AIR_QUICKLY = "Do you need to travel quickly?";
        public static final String AIR_PASSENGERS = "Can other travelers join you?";
        public static final String AIR_WATER = "Do you need to land in the water?";
        public static final String AIR_LANDSCAPES = "Can you use natural landscapes when taking off?";
        public static final String NO_PARTICIPATION = "That's too bad. Maybe next time!";
        public static final String GOODBYE_MESSAGE = "Thank you for participating. Goodbye!";
        public static final String ERROR_MESSAGE = "Error: Unable to process response. Ending program... ";
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

        @Test()
        public void testExpectedOne() {

            // Set the input
            String input = "Yes\nSpace\n";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + "\n" +
                    TRAVEL_MEDIUM + "\n" +
                    "You need a Space ship!" + "\n" +
                    GOODBYE_MESSAGE + "\n";

            // Runs the program with the input values
            receiveInput(input);
            VehicleRecommender.main(new String[0]);

            // Retreives the output from the program
            String stuOut = getOutput();

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Make sure you follow the flowchart and use the given strings for the result!",
                    expected.trim(), stuOut.trim());
        }

        @Test()
        public void testExpectedTwo() {

            // Set the input
            String input = "Yes\nAir\nNo\nNo\nincorrect";

            // Pair the input with the expected result
            String expected = WELCOME_MESSAGE + "\n" +
                    TRAVEL_MEDIUM + "\n" +
                    AIR_QUICKLY + "\n" +
                    AIR_WATER + "\n" +
                    AIR_LANDSCAPES + "\n" +
                    ERROR_MESSAGE + "\n";

            // Runs the program with the input values
            receiveInput(input);
            VehicleRecommender.main(new String[0]);

            // Retreives the output from the program
            String stuOut = getOutput();

            // Trims the output and verifies it is correct.
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Make sure you follow the flowchart and use the given strings for the result!",
                    expected.trim(), stuOut.trim());
        }


    }
}
