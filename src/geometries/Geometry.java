package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The abstract class Geometry represents a geometry object.
 */

public interface Geometry extends Intersectable {

    /**
     * Computes the normal vector at the specified point on the surface of the geometry object.
     *
     * @param point The point on the surface of the object
     * @return The normal vector at the specified point
     */
    public Vector getNormal(Point point);
}
