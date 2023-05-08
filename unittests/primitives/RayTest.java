package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.gen5.api.Assertions.assertEquals;

class RayTest {

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
}