/*
  Business Card
  <p>
  This program produces Business Card
  <p>
  The challenge description page on Brightspace guided me through this problem.

  @author Xiaoyu Liu, LE1
 * @version 02/02/2021
 */

import java.util.Scanner;

public class BusinessCard {
    /**
     * Creates and prints name card
     */
    public static void main(String[] args) {
        /**
         * This is the main method.
         */
        // Creat scanner object
        Scanner scan = new Scanner(System.in);

        //Prompt the user to enter info.
        print("Enter your name:");
        String name = scan.nextLine();
        print("Enter your age:");
        Integer age = scan.nextInt();
        print("Enter your gpa:");
        Float gpa = scan.nextFloat();
        print("Enter your major:");
        scan.nextLine();
        String major = scan.nextLine();
        print("Enter your email:");
        String email = scan.nextLine();

        //Print the card
        print(String.format("Name: %s", name));
        print(String.format("Age: %s", age));
        print(String.format("GPA: %.2f", gpa));
        print(String.format("Major: %s", major));
        print(String.format("Email: %s", email));


    }

    private static void print(String str) {
        /**
         * This method prints a string.
         */
        System.out.println(str);
    }
}