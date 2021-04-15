import java.util.concurrent.atomic.AtomicInteger;

/**
 * A simple Player class which may cause race conditions in multi-thread program.
 */
public class Player
{
    public static final Object obj = new Object();
    private final AtomicInteger y;    //y position of the player
    private int x;    //x position of the player
    private int hp;        //health point of the player

    public Player(int x, int y, int hp)
    {
        this.y = new AtomicInteger();
        this.x = x;
        this.y.set(y);
        this.hp = hp;
    }

    public void printPlayer()
    {
        System.out.printf("x position:\t%d\ny position:\t%d\nhealth point:\t%d\n", x, y.get(), hp);
    }

    public synchronized void moveLeft()
    {
        x--;
    }

    public synchronized void moveRight()
    {
        x++;
    }

    public void moveUp()
    {
        y.decrementAndGet();
    }

    public void moveDown()
    {
        y.incrementAndGet();
    }

    public void loseHealth()
    {

        synchronized (obj)
        {
            hp--;
        }
    }

    public void gainHealth()
    {
        synchronized (obj)
        {
            hp++;
        }
    }

}
