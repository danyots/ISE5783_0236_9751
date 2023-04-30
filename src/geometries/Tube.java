package geometries;

import primitives.*;

import java.util.List;

/**
 * The `Tube` class represents a tube in 3D space, defined by a central `Ray` axis and a radius.
 * A tube is a type of `RadialGeometry` object that has no end caps.
 */
public class Tube extends RadialGeometry {

    /**
     * The central axis `Ray` of the tube.
     */
    final protected Ray axisRay;

    /**
     * Constructs a new `Tube` object with the specified central `Ray` axis and radius.
     *
     * @param axisray the central `Ray` axis of the tube
     * @param r       the radius of the tube
     */
    public Tube(Ray axisray, double r) {
        super(r);
        axisRay = axisray;
    }

    /**
     * Returns the central axis `Ray` of this tube.
     *
     * @return the central axis `Ray` of this tube
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" + "axisRay=" + axisRay + ", radius=" + radius + "}";
    }

    /**
     * Returns the normal to a point on the tube.
     *
     * @param point is the point on the tube.
     */
    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        Point p0 = axisRay.getP0();

        double t = v.dotProduct(point.subtract(p0));
        if (Util.isZero(t)) return point.subtract(p0).normalize();

        Point o = p0.add(v.scale(t));
        return point.subtract(o).normalize();
    }
    @Override
    public List<Point> findIntersections(Ray ray) {
        return null;
    }
}

