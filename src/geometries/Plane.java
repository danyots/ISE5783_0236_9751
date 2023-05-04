package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Plane class represents a mathematical plane in three-dimensional space.
 * It is defined by a point and a normal vector or three non-collinear points.
 */
public class Plane implements Geometry {
    final private Point q0;
    final private Vector normal;

    /**
     * Constructs a plane from three non-collinear points.
     *
     * @param p1 The first point
     * @param p2 The second point
     * @param p3 The third point
     */
    public Plane(Point p1, Point p2, Point p3) {
        q0 = p1;
        Vector v1 = p2.subtract(p1);
        Vector v2 = p3.subtract(p1);
        normal = v1.crossProduct(v2).normalize();
    }

    /**
     * Constructs a plane from a point and a normal vector.
     *
     * @param p a point in the plane
     * @param v The normal vector
     */
    public Plane(Point p, Vector v) {
        q0 = p;
        normal = v.normalize();
    }

    /**
     * Returns the normal to a point on the plane.
     *
     * @param point is the point on the plane
     */
    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    /**
     * Returns the point that defines the plane.
     *
     * @return The point that defines the plane
     */
    public Point getQ0() {
        return q0;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return The normal vector of the plane
     */
    public Vector getNormal() {
        return normal;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (q0.equals(ray.getP0())) return null;
        double nv = normal.dotProduct(ray.getDir());
        if (isZero(nv)) return null;
        double t = alignZero(normal.dotProduct(q0.subtract(ray.getP0())) / nv);
        return t > 0 ? List.of(ray.getPoint(t)) : null;
    }


    @Override
    public String toString() {
        return "Plane{" + "q0=" + q0 + ", normal=" + normal + "}";
    }
}
