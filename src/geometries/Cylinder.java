package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

/**
 * The `Cylinder` class represents a cylinder in 3D space, defined by a central `Ray` axis, a radius, and a height.
 * A cylinder is a type of `Tube` object that has a finite height and no end caps.
 */
public class Cylinder extends Tube {


    final private double height;

    /**
     * Constructs a new `Cylinder` object with the specified height, central `Ray` axis, and radius.
     *
     * @param h  the height of the cylinder
     * @param axisray the central `Ray` axis of the cylinder
     * @param r  the radius of the cylinder
     */
    public Cylinder(double h, Ray axisray, double r) {
        super(axisray, r);
        height = h;
    }

    /**
     * Returns the height of this cylinder.
     *
     * @return the height of this cylinder
     */
    public double getHeight() {
        return height;
    }

    /**
     * Returns the radius  of the cylinder.
     *
     * @return the radius  of the cylinder.
     */
    public double getRadius() {
        return radius;
    }

    @Override
    public String toString() {
        return "Cylinder{" + "height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "}";
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
