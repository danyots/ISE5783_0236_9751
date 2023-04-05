package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
/**
 * Unit tests for primitives.Point class
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class PlaneTest {
    /** Test method for {@link geometries.Plane#Plane(primitives.Point,primitives.Point,primitives.Point)}. */
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
    /** Test method for {@link geometries.Plane#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        // TC01: There is a simple single test here - using 3 points
        Plane plane = new Plane(p1,p2,p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> plane.getNormal(p1), "");
        // generate the test result
        Vector result = plane.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
       Vector v1 =p1.subtract(p2);
        Vector v2 =p1.subtract(p3);
        assertTrue(isZero(result.dotProduct(v1)),"the normal is not orthogonal to a vector in the plane");
        assertTrue(isZero(result.dotProduct(v2)),"the normal is not orthogonal to a vector in the plane");
    }
}