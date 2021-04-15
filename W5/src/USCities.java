import java.util.Scanner;

public class USCities {

    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please enter a String of 10 cities separated by commas.");
        String cities = scanner.nextLine();

        for (int i = 0; i < 10; i++) {
            String currentCity = "";
            if (i != 9) {
                int index = cities.indexOf(",");
                currentCity = cities.substring(0, index);
                if (currentCity.matches("^[B-T].*$")) {
                    System.out.println(currentCity);
                }
                cities = cities.substring(index+1);
            } else {
                currentCity = cities;
                if (currentCity.matches("^[B-T].*$")) {
                    System.out.println(currentCity);
                }
            }
        }

        scanner.close();
    }
}
