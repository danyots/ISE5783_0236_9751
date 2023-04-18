package geometries;

import primitives.Point;
import primitives.Vector;

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
    public String toString() {
        return "Triangle{" + "vertices=" + vertices + ", plane=" + plane + "}";
    }
}

