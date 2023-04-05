package primitives;

import geometries.Polygon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Unit tests for primitives.Vector class
 * @author Daniel Tsirkin, Getachwe Wenedemagen
 */
class VectorTest {


    /**
     * Test method for {@link primitives.Vector#Vector(Double,Double,Double)}.
     */
    @Test
    public void testConstructorPoints() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct vector
        try {
            new Vector(1,1,1);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct vector");
        }
        // =============== Boundary Values Tests ==================
        //TC00: zero vector
        assertThrows(IllegalArgumentException.class, () -> new Vector(0,0,0), "ERROR: constructed a zero vector");
    }

    /**
     * Test method for {@link primitives.Vector#Vector(Double3)}.
     */
    @Test
    public void testConstructorDouble3() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct vector
        try {
            Double3 d1= new Double3 (1,1,1);
            new Vector(d1);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct vector");
        }
        // =============== Boundary Values Tests ==================
        //TC00: zero vector
        Double3 d2= new Double3 (0,0,0);
        assertThrows(IllegalArgumentException.class, () -> new Vector(d2), "ERROR: constructed a zero vector");
    }
    /**
     * Test method for {@link primitives.Vector#add(primitives.Vector)}.
     */
    @Test
    public void testAdd() {

        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: 2 vectors that are not the same length and contrast positions
        Vector v2 = new Vector(-2, -4, -6);
        assertEquals(v1.add(v2), new Vector(-1, -2, -3), "ERROR: Vector + Vector does not work correctly");
        // =============== Boundary Values Tests ==================
        //TC10: 2 vectors that are the same length and contrast positions - result vector is vector 0
        assertThrows(IllegalArgumentException.class, () -> v1.add(v1.scale(-1.0)), "ERROR: Vector + -itself does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#scale(Double)} .
     */
    @Test
    public void testScale() {

        Vector v1=new Vector(2,4,6);
        // ============ Equivalence Partitions Tests ==============
        // TC01: multiple vector with a non-zero number
        assertEquals(v1.scale(0.5), new Vector(1,2,3), "ERROR: the scale() result vector is wrong");
        // =============== Boundary Values Tests ==================
        //TC10: multiple vector with a zero number
        assertThrows(IllegalArgumentException.class, () -> v1.scale(0.0), "ERROR: scale() for  a vector and a zero number does not throw an exception");
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)} .
     */
    @Test
    public void testDotProduct() {

        Vector v1 = new Vector(1, 2, 3);
        // ============ Equivalence Partitions Tests ==============
        // TC01: dotProduct of 2 not orthogonal vectors
        Vector v2 = new Vector(-2, -4, -6);
        assertTrue(isZero(v1.dotProduct(v2) + 28), "ERROR: dotProduct() wrong value");
        // =============== Boundary Values Tests ==================
        // TC010: dotProduct of 2  orthogonal vectors
        Vector v3 = new Vector(0, 3, -2);
        assertTrue(isZero(v1.dotProduct(v3)), "ERROR: dotProduct() for orthogonal vectors is not zero");
    }

    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)} .
     */
    @Test
    public void testCrossProduct() {

        Vector v1 = new Vector(1, 2, 3);
        Vector v3 = new Vector(0, 3, -2);
        // ============ Equivalence Partitions Tests ==============
        // TC01: dotProduct of 2 not parallel vectors
        Vector vr = v1.crossProduct(v3);
        assertTrue(isZero(vr.length() - v1.length() * v3.length()), "ERROR: crossProduct() wrong result length");
        //TC02: tests if the result vector is orthogonal to its operands
        assertTrue(isZero(vr.dotProduct(v1)) && isZero(vr.dotProduct(v3)),"ERROR: crossProduct() result is not orthogonal to its operands");
        // =============== Boundary Values Tests ==================
        // TC010: dotProduct of 2  parallel vectors
        Vector v2 = new Vector(-2, -4, -6);
        assertThrows(IllegalArgumentException.class, () -> v1.crossProduct(v2), "ERROR: crossProduct() for parallel vectors does not throw an exception");

    }

    /**
     * Test method for {@link Vector#lengthSquared()} .
     */
    @Test
    public void testLengthSquared() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: lengthSquared of a vector
        Vector v = new Vector(1, 2, 3);
        assertTrue(isZero(v.lengthSquared()- 14), "ERROR: lengthSquared() wrong value");
    }

    /**
     * Test method for {@link Vector#length()} .
     */
    @Test
    public void testLength() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: length of a vector
        Vector v=new Vector(0, 3, 4);
        assertTrue(isZero(v.length() - 5), "ERROR: length() wrong value");
    }

    /**
     * Test method for {@link Vector#normalize()}.
     */
    @Test
    public void testNormalize() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: is the normalized vector a unit one
        Vector v=new Vector(1,2,3);
        Vector normal=v.normalize();
        assertTrue(isZero(normal.length()-1), "ERROR: the normalized vector is not a unit vector");
        // TC02: is the normalized vector parallel to the original one
        assertThrows( IllegalArgumentException.class,()-> v.crossProduct(normal), "ERROR: the normalized vector is not parallel to the original one");
        // TC03: is the normalized vector the same direction as the original one
        assertTrue(v.dotProduct(normal) >= 0,"ERROR: the normalized vector is opposite to the original one");
    }


}