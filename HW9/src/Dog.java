/**
 * A class that represents a Dog.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version January 19, 2021
 */
public class Dog implements Animal {
    @Override
    public String getSound() {
        return "woof";
    }

    @Override
    public boolean isFriendly() {
        return true;
    }
}
