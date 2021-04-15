public class Square implements  Shape{
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public double calculatePerimeter() {
        return side * 4;
    }

    public double calculateArea() {
        return side * side;
    }

    @Override
    public int getNumSides()
    {
        return 4;
    }

    ;
}
