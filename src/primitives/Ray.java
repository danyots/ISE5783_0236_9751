package primitives;

import java.util.List;

import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in 3D space. A ray is defined by a starting point and a direction.
 */
public class Ray {

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
     * @param pointList The list of points to search for the closest point.
     * @return The closest point from the list, or null if the list is null or empty.
     */
    public Point findClosestPoint(List<Point> pointList) {
        if (pointList == null || pointList.size() == 0)
            return null;
        if (pointList.size() == 1)
            return pointList.get(0);

        Point closest = pointList.get(0);

        for (Point p : pointList) {
            if (p0.distance(closest) > p0.distance(p))
                closest = p;
        }

        return closest;
    }

}
