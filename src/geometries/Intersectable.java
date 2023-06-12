package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * The abstract class Intersectable represents intersectable objects.
 */
public abstract class Intersectable {
    protected Box box;

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

    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }


    /**
     * Helper method for finding the geometric intersections of a ray with the geometry.
     * Subclasses must implement this method.
     *
     * @param ray The ray to intersect with the geometry
     * @return A list of GeoPoint objects representing the intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public abstract boolean isIntersectBox(Ray ray);

    /**
     * Representation of a geometric intersection point.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a GeoPoint object with the specified geometry and point.
         *
         * @param geometry The geometry object
         * @param point    The intersection point
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }


        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            return obj instanceof GeoPoint other && this.geometry == other.geometry && this.point.equals(other.point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" + "geometry=" + geometry + ", point=" + point + "}";
        }
    }

}
