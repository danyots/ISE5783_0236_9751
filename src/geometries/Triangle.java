package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * The Triangle class is a polygon with three vertices.
 * It extends the Polygon class and inherits its properties.
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle object with three vertices.
     *
     * @param p1 the first vertex of the triangle.
     * @param p2 the second vertex of the triangle.
     * @param p3 the third vertex of the triangle.
     */
    public Triangle(Point p1, Point p2, Point p3) {
        super(p1, p2, p3);
    }

    /**
     * Returns the normal to a point on the triangle.
     *
     * @param point is the point on the triangle
     */
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        List<GeoPoint> points = plane.findGeoIntersectionsHelper(ray,maxDistance);
        if (points == null) return null;

        Point p0 = ray.getP0();
        Vector dir = ray.getDir();
        Vector v1 = vertices.get(0).subtract(p0);
        Vector v2 = vertices.get(1).subtract(p0);
        Vector n1 = v1.crossProduct(v2).normalize();
        double t1 = alignZero(dir.dotProduct(n1));
        if (t1 == 0) return null;

        Vector v3 = vertices.get(2).subtract(p0);
        Vector n2 = v2.crossProduct(v3).normalize();
        double t2 = alignZero(dir.dotProduct(n2));
        if (t1 * t2 <= 0) return null;

        Vector n3 = v3.crossProduct(v1).normalize();
        double t3 = alignZero(dir.dotProduct(n3));
        if (t1 * t3 <= 0) return null;

        points.get(0).geometry = this;
        return points;
    }

    @Override
    public String toString() {
        return "Triangle{" + "vertices=" + vertices + ", plane=" + plane + "}";
    }
}

