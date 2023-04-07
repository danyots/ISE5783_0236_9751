package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        Point p0 = new Point(0, 0, 0);
        Point p1 = new Point(1, 0, 0);
        Vector v= p1.subtract(p0);
        double radius =1;
        double h=2;
        Ray axis = new Ray(p0,v);
        Cylinder cy= new Cylinder(h,axis,radius);
        // ============ Equivalence Partitions Tests ==============
        // TC01: point on the round surface
        // ensure there are no exceptions
        Point side = new Point(1, 0, 1);
        assertDoesNotThrow(() -> cy.getNormal(side), "");
        // generate the test result
        Vector result = cy.getNormal(side);
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Tube's normal is not a unit vector");

        // TC02: point on the first base.
        // ensure there are no exceptions
        Point base1 = new Point(0, 0, 0.5);
        assertDoesNotThrow(() -> cy.getNormal(base1), "");
        // generate the test result
        Vector result2 = cy.getNormal(base1);
        // ensure |result| = 1
        assertEquals(1, result2.length(), 0.00000001, "Tube's normal is not a unit vector");

        // TC03: point on the second base.
        // ensure there are no exceptions
        Point base2 = new Point(2, 0, 0.5);
        assertDoesNotThrow(() -> cy.getNormal(base2), "");
        // generate the test result
        Vector result3 = cy.getNormal(base2);
        // ensure |result| = 1
        assertEquals(1, result3.length(), 0.00000001, "Tube's normal is not a unit vector");

        // =============== Boundary Values Tests ==================

        //TC11: point in the center of first base
        Point baseCenter1 = new Point(0, 0, 0);
        // ensure there is exception
        assertThrows(IllegalArgumentException.class,() -> cy.getNormal(baseCenter1), "ERROR: getNormal() for the center of first base does not throw an exception");

        //TC12: point in the center of second base
        Point baseCenter2 = new Point(2, 0, 0);
        // ensure there is exception
        assertThrows(IllegalArgumentException.class,() -> cy.getNormal(baseCenter2), "ERROR: getNormal() for the center of second base does not throw an exception");
    }
}