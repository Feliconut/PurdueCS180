import javax.swing.*;

public class OrderFormGUI
{
    public static void main(String[] args)
    {
        /** DO NOT CHANGE VALUES BELOW **/
        boolean hoodieInStock = true;
        boolean tshirtInStock = false;
        boolean longsleeveInStock = true;
        String item = "";
        int quantity = 0;
        String name = "";
        /** DO NOT CHANGE VALUES ABOVE **/

        while (true)
        {
            String[] options = {"Hoodie", "T-shirt", "Long sleeve"};
            do
            {
                item = (String) JOptionPane.showInputDialog(null, "Select item style ", "Order Form",
                        JOptionPane.QUESTION_MESSAGE, null, options, null);
                if ((!item.equals(
                        "Hoodie") || !hoodieInStock) &&
                        (!item.equals("T-shirt") || !tshirtInStock) &&
                        (!item.equals("Long sleeve") || !longsleeveInStock))
                {
                    JOptionPane.showMessageDialog(null, "Out of Stock", "Order Form",
                            JOptionPane.ERROR_MESSAGE);
                } else
                {
                    break;
                }
            } while (true);

            do
            {
                try
                {
                    quantity = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter quantity", "Order Form",
                            JOptionPane.QUESTION_MESSAGE));
                    if (quantity <= 0)
                    {
                        JOptionPane.showMessageDialog(null, "Enter a number greater than 0", "Order Form",
                                JOptionPane.ERROR_MESSAGE);
                    } else
                    {
                        break;
                    }
                } catch (NumberFormatException ignored)
                {
                    JOptionPane.showMessageDialog(null, "Enter an integer", "Order Form",
                            JOptionPane.ERROR_MESSAGE);

                }
            } while (true);

            do
            {
                name = JOptionPane.showInputDialog(null, "Enter your Name", "Order Form",
                        JOptionPane.QUESTION_MESSAGE);
                if (!name.contains(" "))
                {
                    JOptionPane.showMessageDialog(null, "Enter first and last name", "Order Form",
                            JOptionPane.ERROR_MESSAGE);

                } else
                {
                    break;
                }
            } while (true);

            /** Order Confirmation Message **/
            String resultMessage = "Name: " + name + "\nItem: " + item + "\nQuantity: " + quantity;
            JOptionPane.showMessageDialog(null, resultMessage, "Order Confirmation", JOptionPane.INFORMATION_MESSAGE);

            int option = JOptionPane.showConfirmDialog(null, "Would you like to place another order?", "Order Form", JOptionPane.YES_NO_OPTION);
            if (option != 0)
            {
                break;
            }
        }

    }
}
