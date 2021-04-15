public class Circle implements Shape
{

    private int radius;

    public Circle(int radius) {
        this.radius = radius;
    }

    @Override
    public double calculatePerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public double calculateArea() {
        return 2 * Math.PI * radius;
    }

    @Override
    public int getNumSides()
    {
        return 0;
    }
}
