package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The `Sphere` class represents a three-dimensional sphere in space, which is defined by a center point and a radius.
 * It extends the `RadialGeometry` class and inherits its properties.
 */
public class Sphere extends RadialGeometry {
    final private Point center;

    /**
     * Constructs a new `Sphere` object with the specified center point and radius.
     *
     * @param center the center point of the sphere.
     * @param r      the radius of the sphere.
     */
    public Sphere(Point center, double r) {
        super(r);
        this.center = center;
        constructBox();
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */
    @SuppressWarnings("unused")
    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" + "center=" + center + ", radius=" + radius + "}";
    }

    @Override
    public Vector getNormal(Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public void constructBox() {
        double X=center.getX();
        double Y=center.getY();
        double Z=center.getZ();
        double minX =X  - radius;
        double minY = Y - radius;
        double minZ = Z - radius;
        double maxX = X + radius;
        double maxY = Y + radius;
        double maxZ = Z + radius;
        box=new Box(minX,minY,minZ,maxX,maxY,maxZ);
    }

    @Override
    public boolean isIntersectBox(Ray ray, double maxDistance) {
        return box.intersects(ray,maxDistance);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        if (center.equals(ray.getP0())) return List.of(new GeoPoint(this, ray.getPoint(radius)));

        Vector u = center.subtract(ray.getP0());
        double tm = alignZero(u.dotProduct(ray.getDir()));
        if (tm < 0) return null;

        double d2 = alignZero(u.dotProduct(u) - tm * tm);
        double th2 = radius2 - d2;
        if (alignZero(th2) <= 0) return null;
        double th = alignZero(Math.sqrt(th2));

        double t2 = alignZero(tm + th);
        if (t2 <= 0) return null;
        double t1 = alignZero(tm - th);

        double t1D = Util.alignZero(t1 - maxDistance);
        if (t1D > 0) return null;

        double t2D = Util.alignZero(t2 - maxDistance);
        if (t2D <= 0)
            return t1 <= 0 ? //
                    List.of(new GeoPoint(this, ray.getPoint(t2))) : //
                    List.of(new GeoPoint(this, ray.getPoint(t1)), new GeoPoint(this, ray.getPoint(t2)));

        return t1 <= 0 ? null : //
                List.of(new GeoPoint(this, ray.getPoint(t1)));
    }
}
