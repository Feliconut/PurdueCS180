public class Person
{
    private int age;
    private String name;


    public Person(String two,int one )
    {
        this.age = one;
        this.name = two;

    }

    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public void setAge(int age)
    {
        this.age = age;
    }
}

