import java.util.Scanner;

public class Shopkeeper
{
    public static final String Q_NAME = "What is the customer's name?";
    public static final String Q_NUM_ITEMS = "How many items did the customer purchase?";
    public static final String Q_ITEM_NAME = "Enter the %s item name.";
    public static final String Q_HOW_MUCH = "How much did this item cost?";
    public static final String CUST_STR = "Your customer string is %s";
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        String receipt_code = "";
        System.out.println(Q_NAME);
        String name = scan.nextLine();
        System.out.println(Q_NUM_ITEMS);
        int num_items = scan.nextInt();
        scan.nextLine();

        receipt_code+= Character.toUpperCase(name.charAt(0));
        if (name.contains(" ")){
            receipt_code += Character.toUpperCase(name.charAt(name.indexOf(' ')+1));
        }
        receipt_code += String.valueOf(num_items);

//        System.out.println(receipt_code);
        double total_price = 0;

        for (int i=1;i<=num_items;i++){
//            System.out.println(numformat(i));
            System.out.println(String.format(Q_ITEM_NAME, numformat(i)));

            String item_name = scan.nextLine();
            System.out.println(Q_HOW_MUCH);
            double item_price = scan.nextDouble();
            scan.nextLine();
            receipt_code += item_name;
            total_price += item_price;

            if(i != num_items)
            {
                receipt_code += ',';
            }

        }
        receipt_code += ':';
        receipt_code += String.format("%.2f", total_price);
        System.out.println(String.format(CUST_STR ,receipt_code) );

    }

    private static String numformat(int num){
        if (10<num && num<20){
            return String.valueOf(num)+"th";
        }
        String numstr = String.valueOf(num);
        switch (num % 10){
            case 1:
                return numstr+"st";
            case 2:
                return numstr+"nd";
            case 3:
                return numstr+"rd";
            default:
                return numstr+"th";
        }
    }

}
