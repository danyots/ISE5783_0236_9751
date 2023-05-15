package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.gen5.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

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
        assertDoesNotThrow(() -> sphr.getNormal(side));
        // generate the test result
        Vector result = sphr.getNormal(side);
        // ensure |result| = 1
        assertEquals(1.0, result.length(),  "Polygon's normal is not a unit vector");
        //is the excepted normal
        assertEquals(new Vector(0, 0, 1), result, "the normal is incorrect");
    }

    /**
     * Test method for {@link geometries.Sphere#findGeoIntersectionsHelper(primitives.Ray)}.
     */
    @Test
    public void testfindGeoIntersectionsHelper() {
        Sphere sphere = new Sphere(new Point(1, 0, 0), 1);
        // ============ Equivalence Partitions Tests ==============
        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 0), new Vector(1, 1, 0))),
                "Ray's line out of sphere");
        // TC02: Ray starts before and crosses the sphere (2 points)
        Point p1 = new Point(0.0651530771650466, 0.355051025721682, 0);
        Point p2 = new Point(1.53484692283495, 0.844948974278318, 0);
        List<Intersectable.GeoPoint> result = sphere.findGeoIntersectionsHelper(new Ray(new Point(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(), "Wrong number of points");
        if (result.get(0).point.getX() > result.get(1).point.getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals(List.of(new GeoPoint(sphere,p1), new GeoPoint(sphere,p2)), result, "Ray crosses sphere");
        // TC03: Ray starts inside the sphere (1 point)
        Point p3 = new Point(1, 1, 0);
        List<GeoPoint> result2 = sphere.findGeoIntersectionsHelper(new Ray(new Point(1.5, 0, 0),
                new Vector(-0.5, 1, 0)));
        assertEquals(1, result2.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,p3)), result2, "Ray crosses sphere from inside");
        // TC04: Ray starts after the sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(4.5, 3, 0),
                        new Vector(1, 1, 0))),
                "Ray starts after the sphere");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        Point p4 = new Point(1, 1, 0);
        List<GeoPoint> result4 = sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0),
                new Vector(-1, 1, 0)));
        assertEquals(1, result4.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,p4)), result4, "Ray starts at sphere and goes inside");
        // TC12: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0),
                        new Vector(1, 1, 0))),
                "Ray starts at sphere and goes outside");
        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        Point p5 = new Point(2, 0, 0);
        Point p6 = new Point(0, 0, 0);
        List<GeoPoint> result6 = sphere.findGeoIntersectionsHelper(new Ray(new Point(3, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(2, result6.size(), "Wrong number of points");
        if (result6.get(0).point.getX() > result6.get(1).point.getX())
            result6 = List.of(result6.get(1), result6.get(0));
        assertEquals(List.of(new GeoPoint(sphere,p6),new GeoPoint(sphere, p5)), result6, "Ray starts before the sphere through the center");
        // TC14: Ray starts at sphere and goes inside (1 points)
        Point p7 = new Point(0, 0, 0);
        List<GeoPoint> result7 = sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(1, result7.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,p7)), result7, "Ray starts at sphere and goes inside through the center");
        // TC15: Ray starts inside (1 points)
        Point p8 = new Point(0, 0, 0);
        List<GeoPoint> result8 = sphere.findGeoIntersectionsHelper(new Ray(new Point(1.5, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(1, result8.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,p8)), result8, "Ray starts inside through the center");
        // TC16: Ray starts at the center (1 points)
        Point p9 = new Point(0, 0, 0);
        List<GeoPoint> result9 = sphere.findGeoIntersectionsHelper(new Ray(new Point(1, 0, 0),
                new Vector(-1, 0, 0)));
        assertEquals(1, result9.size(), "Wrong number of points");
        assertEquals(List.of(new GeoPoint(sphere,p9)), result9, "Ray starts at the center through the center");
        // TC17: Ray starts at sphere and goes outside (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0),
                        new Vector(1, 0, 0))),
                "starts at sphere and goes outside through the center");
        // TC18: Ray starts after sphere (0 points)
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(3, 0, 0),
                        new Vector(1, 0, 0))),
                "Ray starts after sphere through the center");
        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(2, -1, 0),
                        new Vector(0, 1, 0))),
                "Ray starts before the tangent point");
        // TC20: Ray starts at the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 0, 0),
                        new Vector(0, 1, 0))),
                "Ray starts at the tangent point");
        // TC21: Ray starts after the tangent point
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(2, 1, 0),
                        new Vector(0, 1, 0))),
                "Ray starts after the tangent point");
        // **** Group: Special cases
        // TC22: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        assertNull(sphere.findGeoIntersectionsHelper(new Ray(new Point(3, 0, 0),
                        new Vector(0, 1, 0))),
                "Ray's line is outside, ray is orthogonal to ray start to sphere's center line");
    }
}