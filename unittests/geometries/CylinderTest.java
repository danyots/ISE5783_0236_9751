package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * Unit tests for geometries.Cylinder class
 *
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
        Vector v = p1.subtract(p0);
        double radius = 1;
        double h = 2;
        Ray axis = new Ray(p0, v);
        Cylinder cy = new Cylinder(h, axis, radius);
        // ============ Equivalence Partitions Tests ==============
        // TC01: point on the round surface
        // ensure there are no exceptions
        Point side = new Point(1, 0, 1);
        assertDoesNotThrow(() -> cy.getNormal(side), "");
        // generate the test result
        Vector result = cy.getNormal(side);
        // ensure |result| = 1
        assertEquals(1.0, result.length(),  "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(0, 0, 1), result, "the normal is incorrect");


        // TC02: point on the first base.
        // ensure there are no exceptions
        Point base1 = new Point(0, 0, 0.5);
        assertDoesNotThrow(() -> cy.getNormal(base1), "");
        // generate the test result
        Vector result2 = cy.getNormal(base1);
        // ensure |result| = 1
        assertEquals(1.0, result2.length(),  "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(1, 0, 0), result2, "the normal is incorrect");

        // TC03: point on the second base.
        // ensure there are no exceptions
        Point base2 = new Point(2, 0, 0.5);
        assertDoesNotThrow(() -> cy.getNormal(base2), "");
        // generate the test result
        Vector result3 = cy.getNormal(base2);
        // ensure |result| = 1
        assertEquals(1.0, result3.length(), "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(1, 0, 0), result3, "the normal is incorrect");

        // =============== Boundary Values Tests ==================

        //TC11: point in the center of first base
        Point baseCenter1 = new Point(0, 0, 0);
        assertDoesNotThrow(() -> cy.getNormal(baseCenter1), "");
        // generate the test result
        Vector result4 = cy.getNormal(baseCenter1);
        // ensure |result| = 1
        assertEquals(1.0, result4.length(),  "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(1, 0, 0), result4, "the normal is incorrect");

        //TC12: point in the center of second base
        Point baseCenter2 = new Point(2, 0, 0);
        assertDoesNotThrow(() -> cy.getNormal(baseCenter2), "");
        // generate the test result
        Vector result5 = cy.getNormal(baseCenter2);
        // ensure |result| = 1
        assertEquals(1.0, result5.length(),  "Tube's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(1, 0, 0), result5, "the normal is incorrect");
    }


    /**
     * tests the constructor of Cylinder
     * Test method for {@link geometries.Cylinder#findIntersections(primitives.Ray)}.
     */
    @Test
    void findIntersections() {
        Ray ray = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
        Cylinder cylinder = new Cylinder(2, ray, 1);

        // ============ Equivalence Partitions Tests ==============

        //TC01 ray is outside and parallel to the cylinder's ray
        List<Point> result = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC02 ray starts inside and parallel to the cylinder's ray
        result = cylinder.findIntersections(new Ray(new Point(2.5, 0, 1), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 2)), result, "Bad intersection point");

        //TC03 ray starts outside and parallel to the cylinder's ray and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2.5, 0, -1), new Vector(0, 0, 1)));
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(2, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2.5, 0, 0), new Point(2.5, 0, 2)), result, "Bad intersection point");

        //TC04 ray starts from outside and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(-2, 0, 0), new Vector(1, 0, 0)));
        //assertEquals(2, result.size(), "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");
        assertNull(result, "Wrong number of points");

        //TC05 ray starts from inside and crosses the cylinder
        result = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        //TC06 ray starts from outside the cylinder and doesn't cross the cylinder
        result = cylinder.findIntersections(new Ray(new Point(5, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        // =============== Boundary Values Tests ==================

        //TC10 ray is on the surface of the cylinder (not bases)
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC11 ray is on the base of the cylinder and crosses 2 times
        result = cylinder.findIntersections(new Ray(new Point(-1, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");
        //assertEquals(List.of(new Point3D(1,0,0), new Point3D(3,0,0)), result, "Bad intersection points");

        //TC12 ray is in center of the cylinder
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(2, 0, 2)), result, "Bad intersection points");

        //TC13 ray is perpendicular to cylinder's ray and starts from outside the tube
        result = cylinder.findIntersections(new Ray(new Point(-2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new Point(1, 0, 0.5), new Point(3, 0, 0.5)), result, "Bad intersection points");

        //TC14 ray is perpendicular to cylinder's ray and starts from inside cylinder (not center)
        result = cylinder.findIntersections(new Ray(new Point(1.5, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        //TC15 ray is perpendicular to cylinder's ray and starts from the center of cylinder
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        //TC16 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to inside
        result = cylinder.findIntersections(new Ray(new Point(1, 0, 0.5), new Vector(1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 0.5)), result, "Bad intersection points");

        //TC17 ray is perpendicular to cylinder's ray and starts from the surface of cylinder to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        //TC18 ray starts from the surface to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        //TC20 ray starts from the center
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection point");

        //TC21 prolongation of ray crosses cylinder
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        //TC22 ray is on the surface starts at bottom's base
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC23 ray is on the surface starts on the surface
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 1), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC24 ray is on the surface starts at top's base
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 2), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");

        //TC25 ray starts from the surface to outside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 1, 1)));
        assertNull(result, "Wrong number of points");

        //TC26 ray starts from the surface to inside
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0.5), new Vector(-1, 0, 0)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(1, 0, 0.5)), result, "Bad intersection point");

        //TC27 ray starts from the center
        result = cylinder.findIntersections(new Ray(new Point(2, 0, 0), new Vector(1, 0, 1)));
        assertEquals(1, result.size(), "Wrong number of points");
        assertEquals(List.of(new Point(3, 0, 1)), result, "Bad intersection point");

        //TC28 prolongation of ray crosses cylinder
        result = cylinder.findIntersections(new Ray(new Point(3, 0, 0), new Vector(1, 0, 0)));
        assertNull(result, "Wrong number of points");

        //TC29 ray is on the surface starts before cylinder
        result = cylinder.findIntersections(new Ray(new Point(3, 0, -1), new Vector(0, 0, 1)));
        assertNull(result, "Wrong number of points");


    }
}