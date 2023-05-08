package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The abstract class Intersectable represents intersectable objects.
 */
public interface Intersectable {
    /**
     * Searches for intersections between a Ray and geometry body.
     *
     * @param ray a Ray object representing the ray to check for intersections
     * @return a List object of Point type representing the intersections found
     **/
    List<Point> findIntersections(Ray ray);
}
