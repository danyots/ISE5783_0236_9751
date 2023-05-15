package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNull;

/**
 * Unit tests for primitives.Ray class
 *
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class RayTest {

    /**
     * Test method for {@link primitives.Ray#getPoint(double)}.
     */
    @Test
    void getPoint() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        //TC01:t is positive
        Point result = ray.getPoint(1);
        assertEquals(new Point(1, 0, 1), result, "t is positive");
        //TC02:t is negative
        Point result1 = ray.getPoint(-1);
        assertEquals(new Point(-1, 0, 1), result1, "t is negative");
        // =============== Boundary Values Tests ==================
        //TC10:t is zero
        Point result2 = ray.getPoint(0);
        assertEquals(new Point(0, 0, 1), result2, "t is negative");
    }

    @Test
    void findClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 1, 0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: middle point is the closest
        List<Point> points = List.of(new Point(3, 3, 1), new Point(1, 1, 1), new Point(2, 2, 1));
        Point closest = ray.findClosestPoint(points);
        assertEquals(new Point(1, 1, 1), closest, "point is not the closest");
        // =============== Boundary Values Tests ==================
        //TC10: list of points is empty
        points = List.of();
        closest = ray.findClosestPoint(points);
        assertNull(closest, "list of points is empty");
        //TC11: closest point is the first in the list
        points = List.of(new Point(1, 1, 1), new Point(3, 3, 1), new Point(2, 2, 1));
        closest = ray.findClosestPoint(points);
        assertEquals(new Point(1, 1, 1), closest, "closest point is the first");
        //TC12: closest point is the last in the list
        points = List.of(new Point(3, 3, 1), new Point(2, 2, 1), new Point(1, 1, 1));
        closest = ray.findClosestPoint(points);
        assertEquals(new Point(1, 1, 1), closest, "closest point is the last in the list");
    }
}