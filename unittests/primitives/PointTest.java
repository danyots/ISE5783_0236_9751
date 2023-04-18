package primitives;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Point class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */

public class PointTest {

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    public void testSubtract() {

        Point p1 = new Point(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: subtract 2 different point
        Point p2 = new Point(-2, -4, -6);
        assertEquals(p1.subtract(p2), new Vector(3, 6, 9), "ERROR: Point - Point does not work correctly");
        // =============== Boundary Values Tests ==================
        Point p3 = new Point(1, 2, 3);
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p3), "ERROR: Point - itself vector, does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Point#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: add a vector to a different coordinated point.
        Point p1 = new Point(1, 2, 3);
        Vector v1 = new Vector(-3, -3, -3);
        assertEquals(p1.add(v1), new Point(-2, -1, 0), "ERROR: Point + Vector does not work correctly");
    }

    /**
     * Test method for {@link primitives.Point#distanceSquared ()}.
     */

    @Test
    public void testDistanceSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: distanceSquared  of 2 different points
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(5, 4, 7);
        assertEquals(36, p1.distanceSquared(p2), 0.00001, "ERROR: distanceSquared() wrong  result squared length");
    }

    /**
     * Test method for {@link primitives.Point#distance(primitives.Point)}.
     */
    @Test
    public void testDistance() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: distance between 2 different points.
        Point p1 = new Point(1, 2, 3);
        Point p2 = new Point(5, 4, 7);
        assertEquals(6,p1.distance(p2), 0.00001, "ERROR: distance() wrong  result length");
    }
}