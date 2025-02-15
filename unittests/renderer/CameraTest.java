package renderer;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * Testing Camera Class
 *
 * @author Dan
 */
class CameraTest {
    static final Point ZERO_POINT = new Point(0, 0, 0);

    /**
     * Test method for
     * {@link Camera#constructRay(int, int, int, int)}.
     */
    @Test
    void testConstructRay() {
        Camera camera = new Camera(ZERO_POINT, new Vector(0, 0, -1), new Vector(0, -1, 0)).setVPDistance(10);
        String badRay = "Bad ray";

        // ============ Equivalence Partitions Tests ==============
        // EP01: 4X4 Inside (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -1, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 1), badRay);

        // =============== Boundary Values Tests ==================
        // BV01: 3X3 Center (1,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, 0, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 1), badRay);

        // BV02: 3X3 Center of Upper Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(0, -2, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 1, 0), badRay);

        // BV03: 3X3 Center of Left Side (1,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, 0, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 1), badRay);

        // BV04: 3X3 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(2, -2, -10)),
                camera.setVPSize(6, 6).constructRay(3, 3, 0, 0), badRay);

        // BV05: 4X4 Corner (0,0)
        assertEquals(new Ray(ZERO_POINT, new Vector(3, -3, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 0, 0), badRay);

        // BV06: 4X4 Side (0,1)
        assertEquals(new Ray(ZERO_POINT, new Vector(1, -3, -10)),
                camera.setVPSize(8, 8).constructRay(4, 4, 1, 0), badRay);

    }


    @Test
    void rotate() {
        Camera camera = new Camera(ZERO_POINT, new Vector(1, 0, -1), new Vector(0, 1, 0)).setVPDistance(10);
        //TC01: rotate 45 degrees to left
        Camera camera1 = new Camera(ZERO_POINT, new Vector(1, 0, -1), new Vector(0.5, 0.7071067811865476, 0.5)).setVPDistance(10);
        Camera roteted = camera.rotateLeft(45);
        assertEquals(camera1.getvUp(), roteted.getvUp(), "not good rotation");////TC02: rotate 45 degrees to right
        camera1 = new Camera(ZERO_POINT, new Vector(1, 0, -1), new Vector(-0.5, 0.7071067811865476, -0.5)).setVPDistance(10);
        roteted = camera.rotateRight(45);
        assertEquals(camera1.getvUp(), roteted.getvUp(), "not good rotation");
        //TC03: rotate 45 degrees to right with view plane
        camera = new Camera(ZERO_POINT, new Vector(1, 0, -1), new Vector(0, 1, 0)).setVPSize(20, 20).setVPDistance(10);
        camera1 = new Camera(ZERO_POINT, new Vector(1, 0, -1), new Vector(-0.5, 0.7071067811865476, -0.5)).setVPSize(20, 20).setVPDistance(10);
        roteted = camera.rotateRight(45);
        assertEquals(camera1.getvUp(), roteted.getvUp(), "not good rotation");
    }
}
