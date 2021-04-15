import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * A class for Report.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class Report
{
    private final int minPrice;
    private final int maxPrice;
    private final ArrayList<Company> companyList;

    public Report(int minPrice, int maxPrice, ArrayList<Company> companyList)
    {
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.companyList = companyList;
    }

    public int getMinPrice()
    {
        return minPrice;
    }

    public int getMaxPrice()
    {
        return maxPrice;
    }

    public ArrayList<Company> getCompanyList()
    {
        return companyList;
    }

    private void writeFile(ArrayList<String> lines, String filename)
    {
        File f = new File(filename);
        try
        {
//            f.delete();
            f.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
//        {
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            // For an ArrayList named `names`
            for (String line : lines)
            {
                pw.println(line);
            }

            pw.close();
//            fos.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void generateReport()
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Company company : companyList)
        {
            lines.add(company.getName() + " Report");
            boolean allWithin = true;
            for (int i = 0; i < company.getPrices().length; i++)
            {
                int price = company.getPrices()[i];
                if (price < minPrice)
                {
                    lines.add("Below Minimum Price at " + i + " with " + price + ".");
                    allWithin = false;
                } else if (price > maxPrice)
                {
                    lines.add("Above Maximum Price at " + i + " with " + price + ".");
                    allWithin = false;
                }
            }
            if (allWithin)
            {
                lines.add("All prices are within the range.");
            }
        }
        writeFile(lines, "Report.txt");
    }

    public void generateReportMax()
    {
        ArrayList<String> lines = new ArrayList<>();
        for (Company company : companyList)
        {
            int max = 0;
            for (int price :
                    company.getPrices())
            {
                if (price > max)
                {
                    max = price;
                }
            }
            lines.add(company.getName() + "-" + max);
        }
        writeFile(lines, "ReportMax.txt");
    }

}
