package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertEquals;
import static org.junit.gen5.api.Assertions.assertNull;


/**
 * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}.
 */
class GeometriesTest {

    @Test
    public void testFindIntersections() {
        Plane plane = new Plane(new Point(1, 2, 0), new Point(1, 7, 0), new Point(-2, 13, 0));
        Sphere sphere = new Sphere(new Point(5, 0, 0), 1);
        Triangle tri = new Triangle(new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1));

        Geometries geo = new Geometries(plane, sphere, tri);
        // ============ Equivalence Partitions Tests ==============
        // **** Group: intersection with part of the geometries
        //TC01: plane and tri
        List<Point> result = geo.findIntersections(new Ray(new Point(0.25, 0.25, 4),
                new Vector(0, 0, -1)));
        assertEquals(2, result.size(), "plane and tri");
        //TC02: sphere and plane
        List<Point> result1 = geo.findIntersections(new Ray(new Point(5, 0, 5),
                new Vector(0, 0, -1)));
        assertEquals(3, result1.size(), "sphere and plane");
        //TC03: sphere and tri
        List<Point> result2 = geo.findIntersections(new Ray(new Point(8, 0.25, 0.25),
                new Vector(-1, 0, 0)));
        assertEquals(3, result2.size(), "sphere and tri");
        // =============== Boundary Values Tests ==================
        // TC11: empty collection
        Geometries geo1 = new Geometries();
        assertNull(geo1.findIntersections(new Ray(new Point(5, 0.25, 0.25),
                        new Vector(-1, 0, 0))),
                "empty collection");
        // TC12: all the geometries intersected
        List<Point> result3 = geo.findIntersections(new Ray(new Point(8, 0.25, 0.25),
                new Vector(-1, 0, -0.01)));
        assertEquals(4, result3.size(), "all the shapes");
        // TC13: no shape is intersected
        assertNull(geo.findIntersections(new Ray(new Point(8, 0.25, 0.25),
                        new Vector(0, -1, 0))),
                "no shape is intersected");
        // **** Group: only one shape is intersected
        //TC14:sphere
        List<Point> result4 = geo.findIntersections(new Ray(new Point(5, -4, 0.5),
                new Vector(0, 1, 0)));
        assertEquals(2, result4.size(), "sphere only");
        //TC15:plane
        List<Point> result5 = geo.findIntersections(new Ray(new Point(5, -4, 7),
                new Vector(0, 0, -1)));
        assertEquals(1, result5.size(), "plane only");
        //TC14:triangle
        List<Point> result6 = geo.findIntersections(new Ray(new Point(2, 0.25, 0.25),
                new Vector(-1, 0, 0)));
        assertEquals(1, result6.size(), "triangle only");

    }
}