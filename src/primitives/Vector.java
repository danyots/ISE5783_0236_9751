package primitives;
/**
 * The Vector class represents a vector in 3D space. A vctor is defined by 3 coordinates.
 */
public class Vector extends Point {
    /**
     * Constructs a new Vector object using three coordinates.
     *
     * @param d1 the x coordinate of the vector
     * @param d2 the y coordinate of the vector
     * @param d3 the z coordinate of the vector
     * @throws IllegalArgumentException if the vector is zero
     */
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (xyz.equals(xyz.ZERO)) throw new IllegalArgumentException("vector cannot be zero");
    }

    /**
     * Constructs a new Vector object using a Double3 object.
     * package-friendly constructor
     *
     * @param coordinates the Double3 object representing the coordinates of the vector
     * @throws IllegalArgumentException if the vector is zero
     */
    Vector(Double3 coordinates) {
        super(coordinates);
        if (xyz.equals(xyz.ZERO)) throw new IllegalArgumentException("vector cannot be zero");
    }

    /**
     * Adds another vector to this vector and returns the result.
     *
     * @param v the vector to be added to this vector
     * @return the resulting vector
     */
    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    /**
     * Scales this vector by a given scalar value and returns the result.
     *
     * @param d the scalar value to scale this vector by
     * @return the resulting vector
     */
    public Vector scale(double d) {
        return new Vector(xyz.scale(d));
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param v the vector to calculate the dot product with
     * @return the dot product
     */
    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    /**
     * Calculates the cross product of this vector with another vector.
     *
     * @param v the vector to calculate the cross product with
     * @return the resulting vector
     */
    public Vector crossProduct(Vector v) {
        double d1 = xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2;
        double d2 = xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3;
        double d3 = xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1;
        return new Vector(d1, d2, d3);
    }

    /**
     * Calculates the squared length of this vector.
     *
     * @return the squared length of this vector
     */
    public double lengthSquared() {
        return dotProduct(this);
    }

    /**
     * Calculates the length of this vector.
     *
     * @return the length of this vector
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Returns a normalized vector with the same direction as this vector.
     *
     * @return the normalized vector
     */
    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other) {
            return this.xyz.equals(other.xyz) ;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Vector{" + "xyz=" + xyz + "}";
    }


}
