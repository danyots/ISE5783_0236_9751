package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Sphere class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class SphereTest {


    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {

        // ============ Equivalence Partitions Tests ==============
        Point center = new Point(0, 0, 0);
        Point side = new Point(0, 0, 1);
        double radius = 1;
        // TC01: There is a simple single test here - a point and a radius
        Sphere sphr = new Sphere(center, radius);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> sphr.getNormal(side), "");
        // generate the test result
        Vector result = sphr.getNormal(side);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(0,0,1),result,"the normal is incorrect");
    }

}