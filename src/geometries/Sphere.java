package geometries;

import primitives.Point;
import primitives.Vector;

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
     * @param r the radius of the sphere.
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

    @Override
    public Vector getNormal(Point point) {
        Vector v = point.subtract(center).normalize();
        return v;
    }
}
