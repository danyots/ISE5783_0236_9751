package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
     * @param r the radius of the tube
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

    @Override
    public Vector getNormal(Point point) {
        Vector v = axisRay.getDir();
        double t = v.dotProduct(point.subtract(axisRay.getP0()));
        Point o = axisRay.getP0().add(v.scale(t));
        return point.subtract(o);
    }
}

