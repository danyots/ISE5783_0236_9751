package geometries;

import geometries.Intersectable.GeoPoint;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Triangle class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class TriangleTest {

    /**
     * Test method for {@link geometries.Triangle#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        // TC01: There is a simple single test here - using 3 points
        Triangle tri = new Triangle(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(p1), "");
        // generate the test result
        Vector result = tri.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        assertEquals(0, result.dotProduct(v1), 0.00001, "the normal is not orthogonal to a vector in the Triangle");
        assertEquals(0, result.dotProduct(v2), 0.00001, "the normal is not orthogonal to a vector in the Triangle");
    }

    /**
     * Test method for {@link geometries.Triangle#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Triangle tri = new Triangle(new Point(0, 2, 0), new Point(0, -1, 0), new Point(5, -1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection inside the triangle (1 points)
        Point p1 = new Point(1, 0, 0);
        List<Point> result = tri.findIntersections(new Ray(new Point(4, 3, 3),
                new Vector(-1, -1, -1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses triangle inside");
        // TC02: intersection outside the triangle in front of a side (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(2, 3, 3),
                        new Vector(-1, -1, -1))),
                "intersection outside the triangle in front of the side ");
        // TC03: intersection outside the triangle in front of a angle (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(2, 1, 3),
                        new Vector(-1, -1, -1))),
                "intersection outside the triangle in front of a angle");
        // =============== Boundary Values Tests ==================
        // TC11: intersection in an angle of the triangle  (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(3, 5, 3),
                        new Vector(-1, -1, -1))),
                "intersection in an angle of the triangle ");
        // TC12: intersection in a side of the triangle (0 points)
        assertNull(tri.findIntersections(new Ray(new Point(3, 4, 3),
                        new Vector(-1, -1, -1))),
                "intersection in a side of the triangle  ");
        // TC13: intersection with the continuation of a side(0 points)
        assertNull(tri.findIntersections(new Ray(new Point(3, 7, 3),
                        new Vector(-1, -1, -1))),
                "intersection with the continuation of a side");

    }

    @Test
    void testFindGeoIntersections() {
        Triangle tri = new Triangle(new Point(0, 2, 0), new Point(0, -1, 0), new Point(5, -1, 0));
        // ============ Equivalence Partitions Tests ==============
        // TC01: intersection inside the triangle (1 points)
        List<GeoPoint> result = tri.findGeoIntersections(new Ray(new Point(4, 3, 3),
                new Vector(-1, -1, -1)), 1);
        assertNull(result, "Wrong number of points");

    }
}