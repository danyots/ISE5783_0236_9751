package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.*;

/**
 * The `Sphere` class represents a three-dimensional sphere in space, which is defined by a center point and a radius.
 * It extends the `RadialGeometry` class and inherits its properties.
 */
public class Sphere extends RadialGeometry {
    final private Point center;

    /**
     * Constructs a new `Sphere` object with the specified center point and radius.
     *
     * @param center the center point of the sphere.
     * @param r      the radius of the sphere.
     */
    public Sphere(Point center, double r) {
        super(r);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */

    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" + "center=" + center + ", radius=" + radius + "}";
    }

    /**
     * Returns the normal to a point on the sphere.
     *
     * @param point is the point on the sphere
     */
    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (center.equals(ray.getP0())) return List.of(ray.getPoint(radius));
        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(u.dotProduct(ray.getDir()));
        if (tm < 0) return null;
        double d = alignZero(Math.sqrt(Math.pow(u.length(), 2) - Math.pow(tm, 2)));
        if (d >= radius) return null;
        double th = alignZero(Math.sqrt(Math.pow(radius, 2) - Math.pow(d, 2)));
        double t1 = alignZero(tm + th);
        double t2 = alignZero(tm - th);
        if (t1 > 0 && t2 > 0) {
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1, p2);
        }

        else if (t1 > 0 && t2 <= 0) {
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }
        else if (t1 <= 0 && t2 > 0) {
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }
        return null;
    }
}
