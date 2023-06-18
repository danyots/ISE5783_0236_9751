package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;

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
        if(ray.isAABB)
            return (!isIntersectBox(ray,maxDistance)) ? null:findGeoIntersectionsHelper(ray, maxDistance);
        return findGeoIntersectionsHelper(ray, maxDistance);
    }
    public abstract void constructBox();


    /**
     * Helper method for finding the geometric intersections of a ray with the geometry.
     * Subclasses must implement this method.
     *
     * @param ray The ray to intersect with the geometry
     * @return A list of GeoPoint objects representing the intersections
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    public abstract boolean isIntersectBox(Ray ray, double maxDistance);
    public static class Box {
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;

        public Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }
        public boolean intersects(Ray ray, double maxDistance) {
            double dirX= ray.getDir().getX();
            double dirY=ray.getDir().getY();
            double dirZ=ray.getDir().getZ();
            double p0X= ray.getP0().getX();
            double p0Y=ray.getP0().getY();
            double p0Z=ray.getP0().getZ();
            double txmin= Util.alignZero(minX - p0X) /dirX;
            double txmax= Util.alignZero(maxX - p0X) / dirX;
            double tymin = Util.alignZero(minY - p0Y) / dirY;
            double tymax = Util.alignZero(maxY - p0Y) / dirY;
            double tzmin = Util.alignZero(minZ - p0Z) / dirZ;
            double tzmax = Util.alignZero(maxZ - p0Z) / dirZ;
            double tmin = Math.max(Math.max(Math.min(txmin, txmax), Math.min(tymin, tymax)), Math.min(tzmin, tzmax));
            double tmax = Math.min(Math.min(Math.max(txmin, txmax), Math.max(tymin, tymax)), Math.max(tzmin, tzmax));

                // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
            if (tmax < 0||tmin>maxDistance)
                {
                    return false;
                }
                // if tmin > tmax, ray doesn't intersect AABB
            return !(tmin > tmax);
        }


    }

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
