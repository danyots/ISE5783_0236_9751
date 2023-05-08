package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

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
     * @param h       the height of the cylinder
     * @param axisray the central `Ray` axis of the cylinder
     * @param r       the radius of the cylinder
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
     * @throws IllegalArgumentException if the point in the center of one of the bases or on the edge of one of the bases.
     */
    @Override
    public Vector getNormal(Point point) {
        if (point.equals(axisRay.getP0())) return axisRay.getDir();
        if (point.equals(axisRay.getP0().add(axisRay.getDir().scale(height))))
            return axisRay.getDir();
        if (point.equals(axisRay.getP0().subtract(point).length() == radius))
            return point.subtract(axisRay.getP0()).normalize();
        if (point.equals(axisRay.getP0().add(axisRay.getDir().scale(height)).subtract(point).length() == radius))
            return point.subtract(axisRay.getP0()).normalize();
        if (!(!isZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir())) && !isZero(point.subtract(axisRay.getP0().add(axisRay.getDir().scale(height))).dotProduct(axisRay.getDir()))))
            return axisRay.getDir();
        return super.getNormal(point);
    }

    public boolean inCylinder(Point p) {
        Vector v = p.subtract(axisRay.getP0());
        double delta = Util.alignZero(p.subtract(axisRay.getP0()).length());
        double distance = Util.alignZero(Math.sqrt(delta * delta - radius * radius));
        return distance < height && distance > 0 && v.dotProduct(axisRay.getDir()) > 0;
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        if (axisRay.getDir().equals(ray.getDir())) {
            if (axisRay.getP0().equals(ray.getP0())) return List.of(ray.getPoint(height));
            Vector delta = ray.getP0().subtract(axisRay.getP0());
            double t = Util.alignZero(delta.dotProduct(axisRay.getDir()));
            double distance = Util.alignZero(Math.sqrt(delta.lengthSquared() - t * t));
            if (distance < radius && t < 0) return List.of(ray.getPoint(-t), ray.getPoint(-t + height));
            else if (distance < radius && t > 0 && t < height) return List.of(ray.getPoint(height - t));
            else if (distance < radius && t >= height) return null;
            else {
                return null;
            }
        } else if (axisRay.getDir().equals(ray.getDir().scale(-1))) {
            if (axisRay.getP0().equals(ray.getP0())) return null;
            Vector delta = ray.getP0().subtract(axisRay.getP0());
            double t = Util.alignZero(delta.dotProduct(axisRay.getDir()));
            double distance = Util.alignZero(Math.sqrt(delta.lengthSquared() - t * t));
            if (distance < radius && t < 0) return null;
            else if (distance < radius && t > 0 && t < height) return List.of(ray.getPoint(-t));
            else if (distance < radius && t > height) return List.of(ray.getPoint(t), ray.getPoint(t - height));
            else {
                return null;
            }
        }
        List<Point> list1 = super.findIntersections(ray);
        if (list1 == null) return null;
        if (list1.size() == 1) {
            Point p = list1.get(0);
            boolean in = inCylinder(p);
            if (in) {
                return list1;
            }
            return null;
        } else if (list1.size() == 2) {
            Point p1 = list1.get(0);
            Point p2 = list1.get(1);
            boolean intersect1 = inCylinder(p1);
            boolean intersect2 = inCylinder(p2);
            if (intersect1 && intersect2) return list1;
            if (!intersect1 && intersect2) return List.of(p2);
            if (intersect1 && !intersect2) return List.of(p1);
            if (!intersect1 && !intersect2) return null;
        }
        return null;
    }
}
