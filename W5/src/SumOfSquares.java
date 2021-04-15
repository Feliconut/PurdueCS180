import java.util.Scanner;

public class SumOfSquares {

    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter an integer:");
        int lim = scan.nextInt();
        int s = 0;
        int i = 1;
        while (s < lim){
            s+= i*i;
            i++;
        }
        System.out.println(String.format("The sum is %d.", s));
    }
}
