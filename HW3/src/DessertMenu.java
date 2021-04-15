import java.util.Scanner;

/**
 * A program that calculates a user's dessert order! 
 *
 * <p>Purdue University -- CS18000 -- Spring 2021 -- Homework 04 -- Walkthrough</p>
 *
 * @author Purdue CS 
 * @version January 19, 2021
 */
public class DessertMenu {
    public static void main(String[] args) {
        int totalPrice = 0;
        Scanner scan = new Scanner(System.in);

        System.out.println("Would you like to order?");
        System.out.println("1. Baklava");
        System.out.println("2. Mochi");
        System.out.println("3. Skyr");
        System.out.println("4. Frozen Custard");

        int choice = scan.nextInt();
        switch (choice) {
            case 1:
                totalPrice += 5;
                break;
            case 3:
                totalPrice += 3;
                break;
            case 4:
                totalPrice += 6;
                break;
            case 5:
                totalPrice += 4;
                break;
            default:
            System.out.println("Error! Choose an item on the menu!");


        }


        System.out.println("Your total is: $" + totalPrice);

    }
}