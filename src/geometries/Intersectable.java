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
    public List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    public static class GeoPoint {
        public Geometry geometry;
        public Point point;
        public GeoPoint(Geometry geometry,Point point){
            this.geometry=geometry;
            this.point=point;
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj instanceof GeoPoint other) {
                return this.geometry.equals(other.geometry) && this.point.equals(other.point);
            }
            return false;
        }
        @Override
        public String toString() {
            return "Ray{" + "geometry=" + geometry + ", point=" + point + "}";
        }
    }
    public List<GeoPoint> findGeoIntersections(Ray ray){
        return findGeoIntersectionsHelper(ray);
    }
    public abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray);

}
