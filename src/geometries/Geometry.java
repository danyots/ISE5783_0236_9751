/**
 * The Geometry interface defines a contract for all geometry objects.
 * Geometry objects must provide a method for computing the normal vector at a given point.
 */
package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {

    /**
     * Computes the normal vector at the specified point on the geometry object.
     *
     * @param point The point at which to compute the normal vector
     * @return The normal vector at the specified point
     */
    public Vector getNormal(Point point);
}
