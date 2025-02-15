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

    @Override
    public void constructBox() {
        Point endPoint1 = axisRay.getP0().add(axisRay.getDir().scale(height));
        Point startPoint = axisRay.getP0();
        // Determine the minimum and maximum coordinates
        double minX = Math.min(startPoint.getX(), endPoint1.getX()) - radius;
        double maxX = Math.max(startPoint.getX(), endPoint1.getX()) + radius;
        double minY = Math.min(startPoint.getY(), endPoint1.getY()) - radius;
        double maxY = Math.max(startPoint.getY(), endPoint1.getY()) + radius;
        double minZ = Math.min(startPoint.getZ(), endPoint1.getZ()) - radius;
        double maxZ = Math.max(startPoint.getZ(), endPoint1.getZ()) + radius;
        box = new Box(minX,minY,minZ,maxX,maxY,maxZ);
    }

    @Override
    public boolean isIntersectBox(Ray ray, double maxDistance) {
        return box.intersects(ray,maxDistance);
    }

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

    /**
     * Checks if a given point is inside the cylinder.
     *
     * @param p The point to check
     * @return {@code true} if the point is inside the cylinder, {@code false} otherwise
     */
    public boolean inCylinder(Point p) {
        Vector v = p.subtract(axisRay.getP0());
        double delta = Util.alignZero(p.subtract(axisRay.getP0()).length());
        double distance = Util.alignZero(Math.sqrt(delta * delta - radius * radius));
        return distance < height && distance > 0 && v.dotProduct(axisRay.getDir()) > 0;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (axisRay.getDir().equals(ray.getDir())) {
            if (axisRay.getP0().equals(ray.getP0())) {
                return Util.alignZero(height - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getPoint(height))) : null;
            }
            Vector delta = ray.getP0().subtract(axisRay.getP0());
            double t = Util.alignZero(delta.dotProduct(axisRay.getDir()));
            double distance = Util.alignZero(Math.sqrt(delta.lengthSquared() - t * t));
            if (distance < radius && t < 0) {
                if (Util.alignZero(-t - maxDistance) > 0) return null;
                else if (Util.alignZero(-t - maxDistance) <= 0 && Util.alignZero(-t + height - maxDistance) > 0)
                    return List.of(new GeoPoint(this, ray.getPoint(-t)));
                else {
                    return List.of(new GeoPoint(this, ray.getPoint(-t)), new GeoPoint(this, ray.getPoint(-t + height)));
                }
            } else if (distance < radius && t > 0 && t < height)
                return (Util.alignZero(height - t - maxDistance) <= 0) ? List.of(new GeoPoint(this, ray.getPoint(height - t))) : null;
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
            else if (distance < radius && t > 0 && t < height)
                return Util.alignZero(-t - maxDistance) <= 0 ? List.of(new GeoPoint(this, ray.getPoint(-t))) : null;
            else if (distance < radius && t > height) {
                if (Util.alignZero(t - height - maxDistance) > 0) return null;
                else if (Util.alignZero(t - height - maxDistance) <= 0 && Util.alignZero(t - maxDistance) > 0)
                    return List.of(new GeoPoint(this, ray.getPoint(t - height)));
                else {
                    return List.of(new GeoPoint(this, ray.getPoint(t)), new GeoPoint(this, ray.getPoint(t - height)));
                }
            } else {
                return null;
            }
        }
        // Check intersection with the bottom base
        GeoPoint geoPointBaseTop = null;
        GeoPoint geoPointBaseBottom = null;
        Point baseCenter = axisRay.getP0();
        Point upperCenter = baseCenter.add(axisRay.getDir().scale(height));
        Plane lowwer = new Plane(baseCenter, axisRay.getDir());
        Plane upper = new Plane(upperCenter, axisRay.getDir());
        List<Point> intersection1 = lowwer.findIntersections(ray);
        List<Point> intersection2 = upper.findIntersections(ray);
        if (intersection1 != null && intersection1.get(0).distance(baseCenter) < radius)
            geoPointBaseBottom = new GeoPoint(this, intersection1.get(0));
        if (intersection2 != null && intersection2.get(0).distance(upperCenter) < radius)
            geoPointBaseTop = new GeoPoint(this, intersection2.get(0));
        List<GeoPoint> list1 = super.findGeoIntersectionsHelper(ray, maxDistance);
        if (geoPointBaseTop == null && geoPointBaseBottom == null) {
            if (list1 == null) return null;
            if (list1.size() == 1) {
                Point p = list1.get(0).point;
                boolean in = inCylinder(p);
                if (in) {
                    return list1;
                }
                return null;
            } else if (list1.size() == 2) {
                Point p1 = list1.get(0).point;
                Point p2 = list1.get(1).point;
                boolean intersect1 = inCylinder(p1);
                boolean intersect2 = inCylinder(p2);
                if (intersect1 && intersect2) return list1;
                if (!intersect1 && intersect2) return List.of(new GeoPoint(this, p2));
                if (intersect1 && !intersect2) return List.of(new GeoPoint(this, p1));
                if (!intersect1 && !intersect2) return null;
            }
            return null;
        } else if (geoPointBaseTop != null && geoPointBaseBottom == null) {
            if (list1 == null) return List.of(geoPointBaseTop);
            if (list1.size() == 1) {
                Point p = list1.get(0).point;
                boolean in = inCylinder(p);
                if (in) {
                    return List.of(list1.get(0), geoPointBaseTop);
                }
                return null;
            } else if (list1.size() == 2) {
                Point p1 = list1.get(0).point;
                Point p2 = list1.get(1).point;
                boolean intersect1 = inCylinder(p1);
                boolean intersect2 = inCylinder(p2);
                if (intersect1 && intersect2) {
                    return List.of(list1.get(0), list1.get(1), geoPointBaseTop);
                }
                if (!intersect1 && intersect2) return List.of(new GeoPoint(this, p2), geoPointBaseTop);
                if (intersect1 && !intersect2) return List.of(new GeoPoint(this, p1), geoPointBaseTop);
                if (!intersect1 && !intersect2) return List.of(geoPointBaseTop);
            }
            return null;
        } else if (geoPointBaseTop == null && geoPointBaseBottom != null) {
            if (list1 == null) return List.of(geoPointBaseBottom);
            if (list1.size() == 1) {
                Point p = list1.get(0).point;
                boolean in = inCylinder(p);
                if (in) {
                    return List.of(list1.get(0), geoPointBaseBottom);
                }
                return null;
            } else if (list1.size() == 2) {
                Point p1 = list1.get(0).point;
                Point p2 = list1.get(1).point;
                boolean intersect1 = inCylinder(p1);
                boolean intersect2 = inCylinder(p2);
                if (intersect1 && intersect2) {
                    return List.of(list1.get(0), list1.get(1), geoPointBaseBottom);
                }
                if (!intersect1 && intersect2) return List.of(new GeoPoint(this, p2), geoPointBaseBottom);
                if (intersect1 && !intersect2) return List.of(new GeoPoint(this, p1), geoPointBaseBottom);
                if (!intersect1 && !intersect2) return List.of(geoPointBaseBottom);
            }
            return null;
        } else {
            if (list1 == null) return List.of(geoPointBaseTop, geoPointBaseBottom);
            if (list1.size() == 1) {
                Point p = list1.get(0).point;
                boolean in = inCylinder(p);
                if (in) {
                    return List.of(list1.get(0), geoPointBaseBottom, geoPointBaseTop);
                }
                return null;
            } else if (list1.size() == 2) {
                Point p1 = list1.get(0).point;
                Point p2 = list1.get(1).point;
                boolean intersect1 = inCylinder(p1);
                boolean intersect2 = inCylinder(p2);
                if (intersect1 && intersect2) {
                    return List.of(list1.get(0), list1.get(1), geoPointBaseBottom, geoPointBaseTop);
                }
                if (!intersect1 && intersect2)
                    return List.of(new GeoPoint(this, p2), geoPointBaseTop, geoPointBaseBottom);
                if (intersect1 && !intersect2)
                    return List.of(new GeoPoint(this, p1), geoPointBaseTop, geoPointBaseBottom);
                if (!intersect1 && !intersect2) return List.of(geoPointBaseTop, geoPointBaseBottom);
            }
            return null;
        }
    }
}
