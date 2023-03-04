package primitives;

import java.util.Objects;

public class Point {
    final Double3 xyz;

    public Point(double d1, double d2, double d3) {
        xyz = new Double3(d1, d2, d3);

    }

    Point(Double3 doub3) {
        xyz = new Double3(doub3.d1, doub3.d2, doub3.d3);

    }

    public Vector subtract(Point p) {
        return new Vector(xyz.subtract(p.xyz));
    }

    public Point add(Point p) {
        return new Point(xyz.add(p.xyz));
    }

    public double distanceSquared(Point p) {
        return xyz.d1 * p.xyz.d1 + xyz.d2 * p.xyz.d2 + xyz.d3 * p.xyz.d3;
    }

    public double distance(Point p) {
        return Math.sqrt(distanceSquared(p));
    }

    @Override
    public String toString() {
        return "Point{" +
                "xyz=" + xyz.toString() +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Point) {
            Point other = (Point) obj;
            return this.xyz.equals(other.xyz) ;
        }
        return false;
    }


}
