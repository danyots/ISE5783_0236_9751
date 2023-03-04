package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        normal = null;
    }

    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    public Point getQ0() {
        return q0;
    }

    public Vector getNormal() {
        return normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + q0 +
                ", normal=" + normal +
                '}';
    }
}
