/**
 * A class to make vector.
 *
 * <p>Purdue University -- CS18000 -- Spring 2021</p>
 *
 * @author Purdue CS
 * @version Feburary 23, 2021
 */
public class Vector
{
    private final double x;
    private final double y;
    private final double z;

    public Vector()
    {
        this.x = 1;
        this.y = 1;
        this.z = 1;
    }

    public Vector(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector(Vector v)
    {
        this.x = v.getX();
        this.y = v.getY();
        this.z = v.getZ();
    }

    public double getX()
    {
        return x;
    }

    public double getY()
    {
        return y;
    }

    public double getZ()
    {
        return z;
    }

    public Vector add(Vector v)
    {
        return new Vector(
                (double) x + v.getX(),
                (double) y + v.getY(),
                (double) z + v.getZ()
        );
    }

    public Vector subtract(Vector v)
    {
        return new Vector(
                (double) x - v.getX(),
                (double) y - v.getY(),
                (double) z - v.getZ()
        );
    }

    public Vector crossProduct(Vector v)
    {
        double x1 = v.getX();
        double y1 = v.getY();
        double z1 = v.getZ();

        return new Vector(
                (double) y * z1 - z * y1,
                (double) z * x1 - x * z1,
                (double) x * y1 - y * x1
        );
    }

    public double dotProduct(Vector v)
    {
        double x1 = v.getX();
        double y1 = v.getY();
        double z1 = v.getZ();

        return x * x1 + y * y1 + z * z1;
    }

    public double length()
    {
        return Math.pow(Math.pow(x, 2) + Math.pow(y, 2) + Math.pow(z, 2), 0.5);
    }

    public boolean isOrthogonal(Vector v)
    {
        return dotProduct(v) == 0;
    }

    public Vector unitVector()
    {
        double l = length();
        return new Vector(
                (double) x / l,
                (double) y / l,
                (double) z / l
        );
    }

    /**
     * Returns the {@code String} representation of this vector. The returned {@code String} consists of a
     * comma-separated list of this vector's numerator and denominator surrounded by this class' name and
     * square brackets ("[]").
     *
     * <p>
     * <b>
     * Example:
     * </b>
     * </p>
     * <pre>
     *     Vector v = new Vector(1, 2, 3);
     *     System.out.println(v);
     *     //"Vector[1, 2, 3]" is printed
     * </pre>
     *
     * @return the {@code String} representation of this rational number
     */
    @Override
    public String toString()
    {
        return "Vector[" + x + ", " + y + ", " + z + ']';
    }


    /**
     * Determines whether or not the specified object is equal to this Vector. {@code true} is returned if and
     * only if the specified object is an instance of {@code Vector} and x, y, and z components are
     * equal to this Vector's.
     *
     * @param o the object to be used in the comparisons
     * @return {@code true}, if the specified object is equal to this Vector and {@code false} otherwise
     */

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Double.compare(vector.x, x) == 0 &&
                Double.compare(vector.y, y) == 0 &&
                Double.compare(vector.z, z) == 0;
    }
}
