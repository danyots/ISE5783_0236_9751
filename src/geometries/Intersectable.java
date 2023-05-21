package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * The abstract class Intersectable represents intersectable objects.
 */
public abstract class Intersectable {
    /**
     * Searches for intersections between a Ray and geometry body.
     *
     * @param ray a Ray object representing the ray to check for intersections
     * @return a List object of Point type representing the intersections found
     **/
    //public List<Point> findIntersections(Ray ray);
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**

     Representation of a geometric intersection point.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**

         Constructs a GeoPoint object with the specified geometry and point.
         @param geometry The geometry object
         @param point The intersection point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }
        /**

         Checks if this GeoPoint is equal to another object.
         @param obj The object to compare
         @return true if the objects are equal, false otherwise
         */
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint other) {
                return this.geometry.equals(other.geometry) && this.point.equals(other.point);
            }
            return false;
        }
        /**

         Returns a string representation of the GeoPoint object.
         @return The string representation of the GeoPoint object
         */
        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + "}";
        }
    }
    /**

     Finds the geometric intersections of a ray with the geometry.
     @param ray The ray to intersect with the geometry
     @return A list of GeoPoint objects representing the intersections
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersectionsHelper(ray);
    }
    /**

     Helper method for finding the geometric intersections of a ray with the geometry.
     Subclasses must implement this method.
     @param ray The ray to intersect with the geometry
     @return A list of GeoPoint objects representing the intersections
     */
    public abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
