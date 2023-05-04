package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Unit tests for geometries.Plane class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class PlaneTest {
    /**
     * Test method for {@link geometries.Plane#Plane(primitives.Point, primitives.Point, primitives.Point)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct plane with 3 different points
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
        // =============== Boundary Values Tests ==================

        // TC10:  2 identical points
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 1), new Point(0, 1, 0)),
                "Constructed a plane with 2 identical points");

        // TC11: the points on the same line
        assertThrows(IllegalArgumentException.class, //
                () -> new Plane(new Point(0, 0, 1), new Point(0, 0, 3), new Point(0, 0, -4)),
                "Constructed a plane with point on the same line");


    }

    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        // TC01: There is a simple single test here - using 3 points
        Plane plane = new Plane(p1, p2, p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(p1), "");
        // generate the test result
        Vector result = plane.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        Vector v1 = p1.subtract(p2);
        Vector v2 = p1.subtract(p3);
        assertEquals(0, result.dotProduct(v1), 0.00001, "the normal is not orthogonal to a vector in the plane");
        assertEquals(0, result.dotProduct(v2), 0.00001, "the normal is not orthogonal to a vector in the plane");
    }

    /**
     * Test method for {@link geometries.Sphere#findIntersections(primitives.Ray)}.
     */
    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 1, 0), new Point(2, 5, 0), new Point(1, 6, 0));

        // ============ Equivalence Partitions Tests ==============
        //**** Group: The Ray is neither orthogonal nor parallel to the plane
        // TC01: Ray intersects the plane
        Point p1 = new Point(2, 1, 0);
        List<Point> result = plane.findIntersections(new Ray(new Point(0, 0, -1),
                new Vector(2, 1, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(p1), result, "Ray crosses plane");
        // TC02: Ray does not intersect the plane
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, -1),
                        new Vector(-1, -1, -1))),
                "Ray does not intersect the plane");

        // =============== Boundary Values Tests ==================

        //**** Group: Ray is parallel to the plane
        // TC11: the ray included in the plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(2, 0, 0),
                        new Vector(-1, -1, 0))),
                "the ray included in the plane");
        // TC12: Ray not included in the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(0, 0, 9),
                        new Vector(-1, -1, 0))),
                "the ray is not included in the plane");
        // **** Group: Ray is orthogonal to the plane
        // TC13: ray starts before thr plane(1 points)
        Point p2 = new Point(1, 3, 0);
        List<Point> result2 = plane.findIntersections(new Ray(new Point(1, 3, 2),
                new Vector(0, 0, -1)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(p2), result2, "orthogonal Ray crosses plane");
        // TC14: ray starts in thr plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 5, 0),
                        new Vector(0, 0, 4))),
                "the orthogonal ray starts in thr plane");
        // TC15: ray starts after thr plane(0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 3),
                        new Vector(0, 0, 4))),
                "the orthogonal ray starts after thr plane");
        // **** Group: Ray is neither orthogonal nor parallel to the plane
        // TC16: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 3, 0),
                        new Vector(2, 5, 4))),
                "Ray is neither orthogonal nor parallel to and begins at the plane");
        // TC17: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane  (0 points)
        assertNull(plane.findIntersections(new Ray(new Point(1, 1, 0),
                        new Vector(2, 5, 4))),
                "Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane");


    }
}