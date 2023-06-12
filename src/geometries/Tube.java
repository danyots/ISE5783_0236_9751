package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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

    @Override
    public boolean isIntersectBox(Ray ray) {
        return true;
    }

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Vector v = ray.getDir();
        Vector vTube = axisRay.getDir();
        Point p0 = ray.getP0();
        Point q0 = axisRay.getP0();
        if (v.equals(vTube) || v.equals(vTube.scale(-1))) return null;
        Vector firstV;
        if (Util.isZero(v.dotProduct(vTube))) {
            firstV = v;
        } else {
            firstV = v.subtract(vTube.scale(v.dotProduct(vTube)));
        }
        double a = Util.alignZero(firstV.lengthSquared());
        if (p0.equals(q0)) {
            Point p = ray.getPoint(Util.alignZero(radius / Util.alignZero(v.subtract(vTube.scale(v.dotProduct(vTube))).length())));
            return (p.distance(p0) <= maxDistance) ? List.of(new GeoPoint(this, p)) : null;
        }
        Vector deltaP = p0.subtract(q0);
        Vector secondV;
        double b;
        double c;
        if (Util.isZero(deltaP.dotProduct(vTube))) {
            secondV = deltaP;
            b = 2 * Util.alignZero(firstV.dotProduct(secondV));
            c = Util.alignZero(secondV.lengthSquared() - radius * radius);
        } else {
            if (deltaP.equals(vTube.scale(deltaP.dotProduct(vTube)))) {
                b = 0;
                c = Util.alignZero(-1 * radius * radius);
            } else {
                secondV = deltaP.subtract(vTube.scale(deltaP.dotProduct(vTube)));
                b = 2 * Util.alignZero(firstV.dotProduct(secondV));
                c = Util.alignZero(secondV.lengthSquared() - radius * radius);
            }

        }

        double discriminant = Util.alignZero(b * b - 4 * a * c);
        if (discriminant <= 0) return null;
        else {
            double t1 = Util.alignZero(((-b) + Math.sqrt(discriminant)) / (2 * a));
            double t2 = Util.alignZero(((-b) - Math.sqrt(discriminant)) / (2 * a));
            double t1D = Util.alignZero(t1 - maxDistance);
            double t2D = Util.alignZero(t2 - maxDistance);
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            if ((t1 <= 0 || t1D > 0) && (t2D > 0 || t2 <= 0)) return null;
            else if (t1 > 0 && t1D <= 0 && (t2 <= 0 || t2D > 0)) return List.of(new GeoPoint(this, p1));
            else if (t2 > 0 && t2D <= 0 && (t1 <= 0 || t1D > 0)) return List.of(new GeoPoint(this, p2));
            return List.of(new GeoPoint(this, p1), new GeoPoint(this, p2));
        }

    }


}

