import java.io.File;
import java.io.FileNotFoundException;

/**
 * A class for Validating Inputs.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Validator
{
    public static int checkPrice(int price) throws InvalidPriceException
    {
        if (price < 0)
        {
            throw new InvalidPriceException("Invalid Price Format: " + price);
        } else
        {
            return price;
        }
    }

    public static int checkValueFormat(String line, String valueType) throws WrongFormatException
    {
        try
        {
            switch (valueType)
            {
                case "MaxValue" -> {
                    assert line.startsWith("Max:");
                    return Integer.parseInt(line.substring(4));
                }
                case "MinValue" -> {
                    assert line.startsWith("Min:");
                    return Integer.parseInt(line.substring(4));
                }
                case "CompanyNumberValue" -> {
                    assert line.startsWith("CompanyNumber:");
                    return Integer.parseInt(line.substring(14));
                }
                default -> throw new AssertionError();
            }
        } catch (AssertionError assertionError)
        {
            assertionError.printStackTrace();
            throw new WrongFormatException("Invalid " + valueType + " error");
        }
    }

    public static void checkFile(String fileName) throws FileNotFoundException
    {
        File f = new File(fileName);
        if (!f.exists())
        {
            throw new FileNotFoundException("File: " + fileName + " is invalid");
        }
    }
}
