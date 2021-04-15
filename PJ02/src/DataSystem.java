import java.io.*;
import java.util.ArrayList;

/**
 * A class for Stock Data System.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 16 2021
 */
public class DataSystem
{
    public static void main(String[] args)
    {
        String fileName = args[0];
        DataSystem ds = new DataSystem();
        Report report;
        ArrayList<Company> companyArrayList = new ArrayList<>();
        try
        {
            Validator.checkFile(fileName);
            ArrayList<String> lines = ds.readFile(fileName);
            int maxValue = Validator.checkValueFormat(lines.get(0), "MaxValue");
            int minValue = Validator.checkValueFormat(lines.get(1), "MinValue");
            int companyNumber = Validator.checkValueFormat(lines.get(2), "CompanyNumberValue");
            for (String line :
                    lines.subList(3, 3 + companyNumber))
            {
                companyArrayList.add(Company.parseCompany(line));
            }
            report = new Report(minValue, maxValue, companyArrayList);
            report.generateReport();
            report.generateReportMax();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private ArrayList<String> readFile(String filename) throws FileNotFoundException
    {
        File f = new File(filename);
        FileReader fr = new FileReader(f);
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader bfr = new BufferedReader(fr))
        {

            String line = bfr.readLine();
            while (line != null)
            {
                lines.add(line);
                line = bfr.readLine();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return lines;
    }

}
