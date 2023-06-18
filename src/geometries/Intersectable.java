package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;

import java.util.List;

/**
 * The abstract class Intersectable represents intersectable objects.
 */
public abstract class Intersectable {
    /**
     * The bounding box (AABB) of the geometry.
     */
    protected Box box;

    /**
     * Searches for intersections between a Ray and geometry body.
     *
     * @param ray a Ray object representing the ray to check for intersections
     * @return a List object of Point type representing the intersections found
     **/
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }


    /**
     * Finds the geometric intersections of the geometry with the given ray and returns a list of GeoPoint objects representing the intersections.
     *
     * @param ray The ray to find intersections with.
     * @return A list of GeoPoint objects representing the intersections, or null if no intersections were found.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Finds the geometric intersections of the geometry with the given ray up to the specified maximum distance and returns a list of GeoPoint objects representing the intersections.
     *
     * @param ray         The ray to find intersections with.
     * @param maxDistance The maximum distance for intersection.
     * @return A list of GeoPoint objects representing the intersections, or null if no intersections were found.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        if (ray.isAABB) {
            return (!isIntersectBox(ray, maxDistance)) ? null : findGeoIntersectionsHelper(ray, maxDistance);
        }
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Constructs the bounding box (AABB) of the geometry.
     */
    public abstract void constructBox();


    /**
         * Helper method for finding the geometric intersections of a ray with the geometry.
         * Subclasses must implement this method.
         *
         * @param ray         The ray to intersect with the geometry.
         * @param maxDistance The maximum distance for intersection.
         * @return A list of GeoPoint objects representing the intersections.
         */
        protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

        /**
         * Checks if the geometry intersects with the given ray within the maximum distance.
         *
         * @param ray         The ray to check for intersection.
         * @param maxDistance The maximum distance for intersection.
         * @return true if the geometry intersects with the ray, false otherwise.
         */
        public abstract boolean isIntersectBox(Ray ray, double maxDistance);

        /**
         * Represents an Axis-Aligned Bounding Box (AABB).
         */
        public static class Box {
            private final double minX;
            private final double minY;
            private final double minZ;
            private final double maxX;
            private final double maxY;
            private final double maxZ;

            /**
             * Constructs a new Box object with the specified minimum and maximum coordinates.
             *
             * @param minX The minimum x-coordinate of the box.
             * @param minY The minimum y-coordinate of the box.
             * @param minZ The minimum z-coordinate of the box.
             * @param maxX The maximum x-coordinate of the box.
             * @param maxY The maximum y-coordinate of the box.
             * @param maxZ The maximum z-coordinate of the box.
             */
            public Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
                this.minX = minX;
                this.minY = minY;
                this.minZ = minZ;
                this.maxX = maxX;
                this.maxY = maxY;
                this.maxZ = maxZ;
            }

            /**
             * Checks if the box intersects with the given ray within the maximum distance.
             *
             * @param ray         The ray to check for intersection.
             * @param maxDistance The maximum distance for intersection.
             * @return true if the box intersects with the ray, false otherwise.
             */
            public boolean intersects(Ray ray, double maxDistance) {
                double dirX = ray.getDir().getX();
                double dirY = ray.getDir().getY();
                double dirZ = ray.getDir().getZ();
                double p0X = ray.getP0().getX();
                double p0Y = ray.getP0().getY();
                double p0Z = ray.getP0().getZ();
                double txmin = Util.alignZero(minX - p0X) / dirX;
                double txmax = Util.alignZero(maxX - p0X) / dirX;
                double tymin = Util.alignZero(minY - p0Y) / dirY;
                double tymax = Util.alignZero(maxY - p0Y) / dirY;
                double tzmin = Util.alignZero(minZ - p0Z) / dirZ;
                double tzmax = Util.alignZero(maxZ - p0Z) / dirZ;
                double tmin = Math.max(Math.max(Math.min(txmin, txmax), Math.min(tymin, tymax)), Math.min(tzmin, tzmax));
                double tmax = Math.min(Math.min(Math.max(txmin, txmax), Math.max(tymin, tymax)), Math.max(tzmin, tzmax));

                // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
                if (tmax < 0 || tmin > maxDistance) {
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
