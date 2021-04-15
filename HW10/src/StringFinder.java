import java.util.concurrent.atomic.AtomicInteger;

/**
 * A class for Finding String using a lot of threads.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Xiaoyu Liu
 * @version March 29 2021
 */
public class StringFinder extends Thread
{
    private static AtomicInteger counter = new AtomicInteger(0);
    private int start;
    private int end;
    private String inputText;
    private String wordToFind;

    public StringFinder(String inputText, String wordToFind, int start, int end)
    {
        this.start = start;
        this.end = end;
        this.inputText = inputText;
        this.wordToFind = wordToFind;
    }

    public static void main(String[] args)
    {
        String inputText = "She didn’t understand how change worked." +
                " When she looked at today compared to yesterday," +
                " there was nothing that she could see that was different." +
                " Yet, when she looked at today compared to last year, " +
                "she couldn’t see how anything was ever the same.";
        StringFinder one = new StringFinder(inputText, "she", 0, 40);
        StringFinder two = new StringFinder(inputText, "she", 40, 88);

        one.start();
        two.start();
        try
        {
            one.join();
            two.join();
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println(StringFinder.counter.get());
    }

    @Override
    public void run()
    {
        int result = 0;
        String scope = inputText.substring(start, end).toLowerCase();
        String toFind = wordToFind.toLowerCase();

        final String[] words = scope.split(" ");
        for (String s :
                words)
        {
            if (s.contains(toFind))
            {
                result++;
            }
        }
        counter.addAndGet(result);
    }
}
