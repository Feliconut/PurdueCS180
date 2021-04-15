import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ColorMixer
{
    private static final String PROMPT1 = "Enter the filename of the color map.";
    private static final String PROMPT2 = "Enter the filename to output the colors to.";
    private static final String PROMPT3 = "Either the file doesn't exist or the file is in the wrong format!";
    private static final String PROMPT4 = "The file was written to!";
    private static final String PROMPT5 = "There was an error writing to the file.";

    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        System.out.println(PROMPT1);
        String inputFileName = scan.nextLine();
        scan.next();
        Color[] colors = readFile(inputFileName);
        if (colors == null){return;}
        System.out.println(PROMPT2);
        String outputFileName = scan.nextLine();
        if (writeFile(colors, outputFileName))
        {
            System.out.println(PROMPT4);
        } else
        {
            System.out.println(PROMPT5);
        }
    }


    public static boolean writeFile(Color[] colors, String filename)
    {
        File f = new File(filename);
        try
        {
            f.createNewFile();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        try
        {
            FileOutputStream fos = new FileOutputStream(f, false);
            PrintWriter pw = new PrintWriter(fos);
            for (Color color : colors)
            {
                pw.println(color.toString());
            }

            pw.close();
            return true;
        } catch (IOException e)
        {
            e.printStackTrace();
            return false;
        }

    }


    public static Color[] readFile(String filename)
    {
        try
        {
            File f = new File(filename);

            FileReader fr = new FileReader(f);
            ArrayList<Color> colors = new ArrayList<>();
            try (BufferedReader bfr = new BufferedReader(fr))
            {
                while (true)
                {
                    String line1 = bfr.readLine();
                    if (line1 == null)
                    {
                        break;
                    }

                    String line2 = bfr.readLine();
                    String line3 = bfr.readLine();

                    int r = Integer.parseInt(line1);
                    int g = Integer.parseInt(line2);
                    int b = Integer.parseInt(line3);

                    colors.add(new Color(r, g, b));

                }
            }
            return (Color[]) colors.toArray();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
