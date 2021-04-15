public class MyThreadTwo extends Thread
{

    public static void main(String[] args)
    {

        MyThreadTwo toRun = new MyThreadTwo();

        toRun.start(); // starts the Thread. The logic in MyThreadTwo’s run() method will execute.


        try
        { // MUST encapsulate in a try-catch block for InterruptedExceptions.

            // Other forms of handling may work
            // (i.e. appending throws InterruptedException to the method header)

            toRun.join(); // pauses the program here until toRun has finished executing its run() method.

        } catch (InterruptedException ie)
        {

            ie.printStackTrace(); // will print the stack trace of the origin of the InterruptedException

        }

    }

    public void run()
    { // MUST NOT have any parameters!
        // thread logic
        System.out.println("Running!");
    }
}