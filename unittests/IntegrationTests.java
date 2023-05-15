import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import renderer.Camera;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static org.junit.gen5.api.Assertions.assertEquals;

/**
 * Testing integration between Creating rays between the camera
 * And calculation of sections of a beam with geometric bodies
 *
 * @author Daniel and Getachew
 */
class IntegrationTests {
    /**
     * Calculates the number of intersections between a given Intersectable shape and a Ray originating from a specified Point p0
     *
     * @param shape The Intersectable shape to calculate intersections with
     * @param p0    The starting Point of the Ray
     * @return The total number of intersections between the shape and the Ray
     */
    private int numIntersections(Intersectable shape, Point p0) {
        int sum = 0;
        Camera camera = new Camera(p0, new Vector(0, 0, -1), new Vector(0, 1, 0));
        camera.setVPSize(3, 3).setVPDistance(1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Ray ray = camera.constructRay(3, 3, j, i);
                List<GeoPoint> result = shape.findGeoIntersectionsHelper(ray);
                if (result != null)
                    sum += result.size();
            }
        }
        return sum;
    }

    @Test
    void testIntegrationSphere() {
        // TC01: 2 intersection points with sphere
        Sphere sphere = new Sphere(new Point(0, 0, -3), 1);
        int sum = numIntersections(sphere, new Point(0, 0, 0));
        assertEquals(2, sum, "Wrong number of points  with sphere- must be 2");
        // TC02: 18 intersection points with sphere
        sphere = new Sphere(new Point(0, 0, -2.5), 2.5);
        sum = numIntersections(sphere, new Point(0, 0, 0.5));
        assertEquals(18, sum, "Wrong number of points  with sphere- must be 18");
        // TC03: 10 intersection points with sphere
        sphere = new Sphere(new Point(0, 0, -2), 2);
        sum = numIntersections(sphere, new Point(0, 0, 0.5));
        assertEquals(10, sum, "Wrong number of points  with sphere- must be 10");
        // TC04: 9 intersection points with sphere
        sphere = new Sphere(new Point(0, 0, 0), 4);
        sum = numIntersections(sphere, new Point(0, 0, 0.5));
        assertEquals(9, sum, "Wrong number of points  with sphere- must be 9");
        // TC05: no intersection points with sphere
        sphere = new Sphere(new Point(0, 0, 1), 0.5);
        sum = numIntersections(sphere, new Point(0, 0, -1));
        assertEquals(0, sum, "Wrong number of points  with sphere - must be 0");
    }

    @Test
    void testIntegrationPlane() {
        // TC01: 9 intersection points with plane - parallel to view plane
        Plane plane = new Plane(new Point(2, 4, -5), new Point(0, 4, -5), new Point(-3, 7, -5));
        int sum = numIntersections(plane, new Point(0, 0, 0));
        assertEquals(9, sum, "Wrong number of points  with plane - parallel to view plane- must be 9");
        // TC02: 9 intersection points with plane - not parallel to view plane
        plane = new Plane(new Point(1.5, 1.5, -1), new Point(-1.5, 1.5, -1), new Point(0, 0, -2));
        sum = numIntersections(plane, new Point(0, 0, 0));
        assertEquals(9, sum, "Wrong number of points  with plane-- not parallel to view plane- must be 9");
        // TC03: 6 intersection points with plane - not parallel to view plane
        plane = new Plane(new Point(1.5, 1.5, -1), new Point(-1.5, 1.5, -1), new Point(0, 0, -20));
        sum = numIntersections(plane, new Point(0, 0, 0));
        assertEquals(6, sum, "Wrong number of points with plane-- not parallel to view plane- must be 6");
    }

    @Test
    void testIntegrationTriangle() {
        // TC01: 1 intersection point with triangle
        Triangle tri = new Triangle(new Point(0, 1, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        int sum = numIntersections(tri, new Point(0, 0, 0));
        assertEquals(1, sum, "Wrong number of points with triangle- must be 1");
        // TC02: 2 intersection points with triangle
        tri = new Triangle(new Point(0, 20, -2), new Point(1, -1, -2), new Point(-1, -1, -2));
        sum = numIntersections(tri, new Point(0, 0, 0));
        assertEquals(2, sum, "Wrong number of points with triangle- must be 2");
    }
}

