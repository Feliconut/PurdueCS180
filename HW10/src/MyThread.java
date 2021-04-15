public class MyThread implements Runnable
{

    public static void main(String[] args)
    {

        Thread toRun = new Thread(new MyThread()); // passing our class to the Java-defined Thread Object

        toRun.start(); // starts the Thread. The logic in MyThread’s run() method will execute.


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

    @Override
    public void run()
    {
        System.out.println("Running!");
    }

}
