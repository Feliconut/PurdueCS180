public class Cat implements Animal
{
    @Override
    public String getSound()
    {
        return "meow";
    }

    @Override
    public boolean isFriendly()
    {
        return false;
    }
}
