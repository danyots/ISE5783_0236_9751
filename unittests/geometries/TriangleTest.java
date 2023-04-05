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
class TriangleTest {

    /** Test method for {@link geometries.Triangle#getNormal(primitives.Point)}. */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point p1=new Point(0, 0, 1);
        Point p2 = new Point(1, 0, 0);
        Point p3 = new Point(0, 1, 0);
        // TC01: There is a simple single test here - using 3 points
        Triangle tri = new Triangle(p1,p2,p3);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(p1), "");
        // generate the test result
        Vector result = tri.getNormal(p1);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Triangle's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        Vector v1 =p1.subtract(p2);
        Vector v2 =p1.subtract(p3);
        assertTrue(isZero(result.dotProduct(v1)),"the normal is not orthogonal to a vector in the Triangle");
        assertTrue(isZero(result.dotProduct(v2)),"the normal is not orthogonal to a vector in the Triangle");
    }
}