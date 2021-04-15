import java.util.Objects;

public class Person implements Identifiable
{
    private final String name;
    private final int age;

    public Person(String name, int age)
    {
        if (name == null){
            throw new NullPointerException();
        }
        if (age < 0){
            throw new IllegalArgumentException();
        }
        this.name = name;
        this.age = age;
    }

    public Person (Person person)
    {
        if (person == null){
            throw new NullPointerException();
        }
        else{
            this.name = person.getName();
            this.age = person.getAge();
        }
    }

    @Override
    public String getName()
    {
        return name;
    }

    public int getAge()
    {
        return age;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Person)) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name);
    }

    @Override
    public String toString()
    {
        return "Person<" +
                "name=" + this.getName() +
                ", age=" + this.getAge() +
                '>';
    }
}
