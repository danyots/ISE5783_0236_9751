package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

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

    @Override
    public String toString() {
        return "Cylinder{" + "height=" + height + ", axisRay=" + axisRay + ", radius=" + radius + "}";
    }

    /**
     * Returns the normal to a point on the cylinder.
     *
     * @param point is the point on the cylinder
     *@throws IllegalArgumentException if the point in the center of one of the bases or on the edge of one of the bases.
     */
    @Override
    public Vector getNormal(Point point) {
        if(point.equals(axisRay.getP0()))throw new IllegalArgumentException("point in the center of first base");
        if(point.equals(axisRay.getP0().add(axisRay.getDir().scale(height))))throw new IllegalArgumentException("point in the center of second base");
        if(point.equals(axisRay.getP0().subtract(point).length()==radius))throw new IllegalArgumentException("point on the edge of first base");
        if(point.equals(axisRay.getP0().add(axisRay.getDir().scale(height)).subtract(point).length()==radius))throw new IllegalArgumentException("point on the edge of second base");
        if(!(!isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())) && !isZero(point.subtract(axisRay.getP0().add(axisRay.getDir().scale(height))).dotProduct(axisRay.getDir()))))
            return axisRay.getDir();
        return super.getNormal(point);
    }
}
