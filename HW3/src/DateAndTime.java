import java.util.Scanner;
/**
 * A program that converts from military time (24 hour) to AM/PM time (12 hour).
 * If the mode isn't military, then it should assume that it's either AM or PM.
 *
 * If mode is military, it should print the time in the format "HH:MM AM/PM".
 * If mode is "AM" or "PM", it should return the time in military time, in the format "HH:MM"
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 04 -- Debugging</p>
 *
 * @author Purdue CS 
 * @version January 19, 2021
 */
public class DateAndTime {



    public static void main(String[] args) {

        int hour;
        int minute;
        String mode;
        String finalTime = "Time";
        String hourError = "You have entered an invalid hour";
        String minuteError = "You have entered an invalid minute";

        Scanner scan = new Scanner(System.in);

        System.out.println("Hour:");
        hour = scan.nextInt();
        System.out.println("Minute:");
        minute = scan.nextInt();
        scan.nextLine();
        System.out.println("Mode:");
        mode = scan.nextLine();


        switch (mode) {



            case ("Military"):
                if (hour < 0 || hour > 24) {
                    System.out.println(hourError);
                } else if (minute < 0 || minute > 59) {
                    System.out.println(minuteError);
                } else {
                    String ampm = (hour < 12) ? "AM" : "PM";
                    if (hour > 12) {
                        hour -= 12;
                    }
                    finalTime = "" + hour + ":" + minute + " " + ampm;
                }
                break;


            case "AM":
                if (hour < 0 || hour > 12) {
                    System.out.println(hourError);
                } else if (minute < 0 || minute > 59) {
                    System.out.println(minuteError);
                } else {
                    finalTime = "" + hour + ":" + minute;
                }
                break;

            case "PM":
                if (hour < 0 || hour > 12) {
                    System.out.println(hourError);
                } else if (minute < 0 || minute > 59) {
                    System.out.println(minuteError);
                } else {
                    hour += 12;
                    finalTime = "" + hour + ":" + minute;
                }
        }


        System.out.println(finalTime);

    }

}