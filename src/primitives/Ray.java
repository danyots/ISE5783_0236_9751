package primitives;

import geometries.Intersectable.GeoPoint;
import renderer.Blackboard;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in 3D space. A ray is defined by a starting point and a direction.
 */
public class Ray {

    private static final double DELTA = 0.1;
    final private Point p0;
    final private Vector dir;

    /**
     * Constructs a new Ray object with the given starting point and direction.
     *
     * @param p The starting point of the ray.
     * @param v The direction of the ray.
     */
    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    /**
     * Constructs a Ray object with a starting point and direction.
     *
     * @param p The starting point of the ray.
     * @param v The direction vector of the ray.
     * @param n The normal vector to the ray's plane.
     */
    public Ray(Point p, Vector v, Vector n) {
        Vector epsVector = n.scale(n.dotProduct(v) > 0 ? DELTA : -DELTA);
        p0 = isZero(v.dotProduct(n)) ? p : p.add(epsVector);
        dir = v.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        return obj instanceof Ray other && this.p0.equals(other.p0) && this.dir.equals(other.dir);
    }

    /**
     * Calculates a point on the ray according to the parameter t.
     *
     * @param t The parameter t that determines the point location on the ray
     * @return A point on the ray according to the parameter t
     */
    public Point getPoint(double t) {
        return isZero(t) ? p0 : p0.add(dir.scale(t));
    }

    @Override
    public String toString() {
        return "Ray{" + "p0=" + p0 + ", dir=" + dir + "}";
    }

    /**
     * Finds the closest point from a list of points to the reference point p0.
     *
     * @param points The list of points to search for the closest point.
     * @return The closest point from the list, or null if the list is null or empty.
     */
    public Point findClosestPoint(List<Point> points) {
        return points == null || points.isEmpty() ? null
                : findClosestGeoPoint(points.stream().map(p -> new GeoPoint(null, p)).toList()).point;
    }

    /**
     * Finds the closest GeoPoint from a list of GeoPoints.
     *
     * @param geoPointList The list of GeoPoints. It can be empty or contain null values.
     * @return The closest GeoPoint, or null if the list is empty or contains null values.
     */
    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList) {
        if (geoPointList == null || geoPointList.size() == 0)
            return null;

        GeoPoint closest = null;
        double min = Double.POSITIVE_INFINITY;
        for (GeoPoint gp : geoPointList) {
            double dist = p0.distance(gp.point);
            if (min > dist) {
                closest = gp;
                min = dist;
            }
        }
        return closest;
    }

    /**
     * Calculates the beams based on the given blackboard.
     *
     * @param blackboard the blackboard containing the necessary information
     * @return a list of rays representing the calculated beams
     */
    public List<Ray> calculateBeam(Blackboard blackboard) {
        List<Ray> rays = new LinkedList<>();
        List<Point> points = blackboard.setRays(this);
        for (Point point : points) {
            rays.add(new Ray(p0, point.subtract(p0)));
        }
        return rays;
    }


}
