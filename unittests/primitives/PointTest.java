package primitives;



import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import primitives.Util.*;
class PointTest {

    @Test
    public void subtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Test if substruct result is right
        Point p1=new Point(1,2,3);
        Point p2=new Point(4,5,6);
        assertEquals(p1.subtract(p2), new Vector(-3,-3,-3), "the result vector is wrong");
        // =============== Boundary Values Tests ==================

        Point p3=new Point(1,2,3);
        assertThrows(IllegalArgumentException.class, () -> p1.subtract(p3), "substruct() for the same points does not throw an exception");
    }

    @Test
    public void add() {
// ============ Equivalence Partitions Tests ==============
        // TC01: Test if substruct result is right
        Point p1=new Point(1,2,3);
        Vector v1=new Vector(-3,-3,-3);
        assertEquals(p1.add(v1), new Point(-2,-1,0) , "the result point is wrong");
        // =============== Boundary Values Tests ==================

        Vector v2=new Vector(-1,-2,-3);
        assertEquals( p1.subtract(v2),new Point(0,0,0) );
    }

    @Test
    public void distanceSquared() {

    }

    @Test
    void distance() {
    }
}