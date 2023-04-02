package primitives;

import java.util.Objects;

/**
 * The Point class represents a point in 3D space.
 */
public class Point {
    /**
     * The 3D coordinates of this Point object.
     * package-friendly field
     */
    final Double3 xyz;

    /**
     * Constructs a new Point object with the specified coordinates.
     *
     * @param d1 the x-coordinate of the point
     * @param d2 the y-coordinate of the point
     * @param d3 the z-coordinate of the point
     */
    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);
    }

    /**
     * Constructs a new Point object from a Double3 object.
     *  package-friendly constructor
     *
     * @param coordinates the Double3 object to construct the Point from
     */
    Point(Double3 coordinates) {
        xyz = new Double3(coordinates.d1, coordinates.d2, coordinates.d3);
    }

    /**
     * Returns a new Vector object that represents the difference between this point and another point.
     * Uses the subtract function in Double3
     *
     * @param p the other point to subtract from this point
     * @return a new Vector object that represents the difference between this point and another point
     */
    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    /**
     * Returns a new Point object that represents the sum of this point and a vector.
     * Uses the add function in Double3
     *
     * @param v the vector to add to this point
     * @return a new Point object that represents the sum of this point and a vector
     */
    public Point add(Vector v) {
        return new Point(xyz.add(v.xyz));
    }

    /**
     * Returns the square of the distance between this point and another point.
     *
     * @param p the other point to calculate the distance to
     * @return the square of the distance between this point and another point
     */
    public double distanceSquared(Point p) {
        double dx=xyz.d1-p.xyz.d1;
        double dy=xyz.d2-p.xyz.d2;
        double dz=xyz.d3-p.xyz.d3;
        return dx*dx+dy*dy+dz*dz;
    }

    /**
     * Returns the distance between this point and another point by using distanceSquared function.
     *
     * @param p the other point to calculate the distance to
     * @return the distance between this point and another point
     */
    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public String toString() {
        return "Point{" + "xyz=" + xyz + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point other)
            return this.xyz.equals(other.xyz) ;
        return false;
    }
}
