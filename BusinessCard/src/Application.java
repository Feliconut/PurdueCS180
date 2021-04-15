/**
 * Business Card
 * <p>
 * This program produces Business Card
 * <p>
 * The challenge description page on Brightspace guided me through this problem.
 *
 * @author Xiaoyu Liu, LE1
 * @version 02/02/2021
 */

import java.util.Scanner;


/**
 * Inputs the application.
 * <p>
 *
 * @param args the input parameter.
 * @author Xiaoyu Liu, LE1
 * @version 02/02/2021
 */
public class Application {

    /**
     * Let the user input the information and
     * generates a comprehensive namecard and provide
     * a confirmation code.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Full Name:");
        String name = scanner.nextLine();
        System.out.println("Date of Birth:");
        String dob = scanner.nextLine();
        System.out.println("Expected Salary:");
        double expsal = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Social Security Number:");
        String ssn = scanner.nextLine();
        System.out.println("Phone Number:");
        long phone = scanner.nextLong();
        scanner.nextLine();
        System.out.println("Email:");
        String email = scanner.nextLine();
        System.out.println("Full Address:");
        String addr = scanner.nextLine();
        System.out.println("Zip Code:");
        String zip = scanner.nextLine();
        System.out.println("City and State:");
        String cityState = scanner.nextLine();

        // generate confirmation code

        String day = dob.substring(dob.indexOf("-") + 1, dob.indexOf("-") + 3);
        String firstName = name.substring(0, name.indexOf(' '));
        String lastName = name.substring(name.indexOf(' ') + 1);
        char a = name.charAt(name.length() - 1);
        int code0 = (int) a;
        int month = Integer.parseInt(dob.substring(0, 2));
        char lastNameLetter = name.charAt(name.indexOf(" ") + 1);
        char code1 = (char) ((int) month + (int) lastNameLetter);
        int code2 = ((int) expsal) % 77;
        char code3 = email.charAt(email.indexOf("@") - 1);
        String code4 = String.valueOf(phone).substring(0, 2);
        String confCode = String.valueOf(code0) + code1 + String.valueOf(code2) + code3 + code4;
        String username = firstName.toLowerCase().charAt(0) + lastName.toLowerCase() + day;
        String phoneStr = String.valueOf(phone);


        System.out.printf("%s %s", capitalize(firstName), capitalize(lastName));
        System.out.print("\n");
        System.out.printf("Date of Birth: %s", dob);
        System.out.print("\n");
        System.out.printf("Expected Salary: $%.2f", expsal);
        System.out.print("\n");
        System.out.printf("SSN: XX-XXX-%s", ssn.substring(ssn.length() - 4, ssn.length()));
        System.out.print("\n");
        System.out.printf("Phone: (%s)%s-%s", phoneStr.substring(0, 3),
                phoneStr.substring(3, 6), phoneStr.substring(6, 10));
        System.out.print("\n");
        System.out.printf("Email: %s", email);
        System.out.print("\n");
        System.out.printf("Full Address:\n%s\n%s\n%s", addr, cityState, zip);
        System.out.print("\n");
        System.out.printf("Username: %s", username);
        System.out.print("\n");
        System.out.printf("Confirmation Code: %s", confCode);

    }

    /**
     * Capitalizes a word.
     */
    private static String capitalize(String string) {
        return Character.toUpperCase(string.charAt(0)) + (string.substring(1).toLowerCase());
    }
}
