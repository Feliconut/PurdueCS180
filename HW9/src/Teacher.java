public class Teacher extends Person
{
    private String subject;

    public Teacher(String name, int age, String subject)
    {
        super(name, age);
        this.subject = subject;
    }

    public String getSubject()
    {
        return subject;
    }
    public static void main(String[] args) {

        Teacher teacher = new Teacher("New Teacher", 29, "Math");

        System.out.println(teacher.getName());

        System.out.println(teacher.getAge());

        System.out.println(teacher.getSubject());

    }
}
