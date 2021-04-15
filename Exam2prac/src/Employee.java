import java.util.Objects;

public class Employee extends Person
{
    private final int hourlyRate;

    public Employee(String name, int age, int hourlyRate)
    {
        super(name, age);
        if (hourlyRate < 0){
            throw new IllegalArgumentException();
        }
        this.hourlyRate = hourlyRate;
    }

    public Employee(Employee employee)
    {
        super(employee);
        this.hourlyRate = employee.getHourlyRate();
    }

    public int getHourlyRate()
    {
        return hourlyRate;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return hourlyRate == employee.hourlyRate;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(hourlyRate);
    }

    @Override
    public String toString()
    {
        return "Employee<" +
                "name=" + this.getName() +
                ", age=" + this.getAge() +
                ", hourlyRate=" + this.getHourlyRate() +
                '>';
    }

    public int calculateIncome(int hours)
    {
        if (hours < 0){
            throw new IllegalArgumentException();
        }
        return hours * this.getHourlyRate();
    }
}
