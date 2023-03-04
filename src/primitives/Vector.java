package primitives;

public class Vector extends Point {
    public Vector(double d1, double d2, double d3) {
        super(d1, d2, d3);
        if (xyz.equals(xyz.ZERO)) throw new IllegalArgumentException("vector cannot be zero");
    }

    Vector(Double3 doub3) {
        super(doub3);
        if (xyz.equals(xyz.ZERO)) throw new IllegalArgumentException("vector cannot be zero");
    }

    public Vector add(Vector v) {
        return new Vector(xyz.add(v.xyz));
    }

    public Vector scale(double d) {
        return new Vector(xyz.scale(d));
    }

    public double dotProduct(Vector v) {
        return xyz.d1 * v.xyz.d1 + xyz.d2 * v.xyz.d2 + xyz.d3 * v.xyz.d3;
    }

    public Vector crossProduct(Vector v) {
        double d1 = xyz.d2 * v.xyz.d3 - xyz.d3 * v.xyz.d2;
        double d2 = xyz.d3 * v.xyz.d1 - xyz.d1 * v.xyz.d3;
        double d3 = xyz.d1 * v.xyz.d2 - xyz.d2 * v.xyz.d1;
        return new Vector(d1, d2, d3);
    }

    public double lengthSquared() {
        return dotProduct(this);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public Vector normalize() {
        return new Vector(xyz.reduce(length()));
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector) {
            Vector other = (Vector) obj;
            return this.xyz.equals(other.xyz) ;
        }
        return false;
    }
    @Override
    public String toString() {
        return "Vector{" +
                "xyz=" + xyz.toString() +
                '}';
    }


}
