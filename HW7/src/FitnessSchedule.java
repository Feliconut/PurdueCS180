import java.util.Scanner;

public class FitnessSchedule {
    //Task: fix main method and exerciseToday() method so that they can display output as shown in the handout
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] fitnessMinutes = new int[7];
        int day = 0;
        System.out.println("Welcome to the fitness schedule display!");

        System.out.println("Enter the number of minutes of fitness activity on Monday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Tuesday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Wednesday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Thursday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Friday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Saturday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();
        day++;

        System.out.println("Enter the number of minutes of fitness activity on Sunday.");
        fitnessMinutes[day] = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter today's day number(0-6).");
        int today = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter today's exercise.");
        String exercise = scanner.nextLine();
        int sum = 0;
        for (int i = 0; i < fitnessMinutes.length; i++) {
            switch (i) {
                case 0:
                    System.out.print("Mon:");
                    break;
                case 1:
                    System.out.print("Tues:");
                    break;
                case 2:
                    System.out.print("Wed:");
                    break;
                case 3:
                    System.out.print("Thurs:");
                    break;
                case 4:
                    System.out.print("Fri:");
                    break;
                case 5:
                    System.out.print("Sat:");
                    break;
                case 6:
                    System.out.print("Sun:");
                    break;
            }
            sum += fitnessMinutes[i];
            System.out.print(fitnessMinutes[i] + " minutes\n");
        }
        System.out.println("Total fitness minutes for the week:" + sum + " minutes");
        exerciseToday(fitnessMinutes,today, exercise);
        scanner.close();
    }

    public static void exerciseToday(int[] fitnessMinutes, int day, String exercise) {
        System.out.println("Today's exercise is " + exercise + " for " + fitnessMinutes[day] + " minutes!");
    }
}
