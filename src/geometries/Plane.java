package geometries;

import geometries.Geometry;
import primitives.Point;
import primitives.Vector;

/**
 * The Plane class represents a mathematical plane in three-dimensional space.
 * It is defined by a point and a normal vector or three non-collinear points.
 */
public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    /**
     * Constructs a plane from three non-collinear points.
     * @param p1 The first point
     * @param p2 The second point
     * @param p3 The third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        normal = null;
    }

    /**
     * Constructs a plane from a point and a normal vector.
     * @param p The point
     * @param v The normal vector
     */
    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }


    @Override
    public Vector getNormal(Point point) {
        return null;
    }

    /**
     * Returns the point that defines the plane.
     * @return The point that defines the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane.
     * @return The normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }


    @Override
    public String toString() {
        return "Plane{" + "q0=" + q0 + ", normal=" + normal + "}";
    }
}
