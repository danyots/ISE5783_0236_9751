package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Tube class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class TubeTest {

    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p0 = new Point(0, 0, 0);
        Point p1 = new Point(1, 0, 0);
        Vector v = p1.subtract(p0);
        double radius = 1;
        Ray axis = new Ray(p0, v);
        // TC01: There is a simple single test here - a point and an axis
        Tube tube = new Tube(axis, radius);
        // ensure there are no exceptions
        Point side = new Point(2, 0, 1);
        assertDoesNotThrow(() -> tube.getNormal(side), "");
        // generate the test result
        Vector result = tube.getNormal(side);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(0,0,1),result,"the normal is incorrect");
        // =============== Boundary Values Tests ==================
        //TC10: normal orthogonal to the axis
        Point orth = new Point(0, 0, 1);
        // generate the test result
        Vector result2 = tube.getNormal(orth);
        //is the excepted normal
        assertEquals(new Vector(0,0,1),result2,"the normal is incorrect");
    }
}