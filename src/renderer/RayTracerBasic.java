package renderer;

import geometries.Intersectable;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

/**
 * This class represents a basic ray tracer that extends the RayTracerBase class.
 * It provides an implementation for the traceRay method to trace rays in a scene and calculate the color of the intersection points.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a RayTracerBasic object with the given scene.
     *
     * @param scene The scene to be rendered.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     * Traces a ray in the scene and calculates the color of the intersection point.
     * If no intersection is found, returns the background color of the scene.
     *
     * @param ray The ray to be traced.
     * @return The color of the intersection point.
     */
    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersectionsHelper(ray);
        if (points == null)
            return scene.background;
        GeoPoint closest = ray.findClosestGeoPoint(points);
        return calcColor(closest);
    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param point The point for which to calculate the color.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint point) {
        return point.geometry.getEmission().add(scene.ambientLight.getIntensity());

    }
}
