package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * This is an abstract base class for a ray tracer.
 * It provides basic functionality for tracing rays in a scene.
 */
public abstract class RayTracerBase {

    /**
     * The scene to be rendered.
     */
    protected final Scene scene;

    /**
     * Constructs a RayTracerBase object with the given scene.
     *
     * @param scene The scene to be rendered.
     */
    public RayTracerBase(Scene scene) {
        if(scene.isAABB)scene.geometries.setBoxes();
        this.scene = scene;
    }

    /**
     * Traces a ray in the scene and returns the color of the intersection point.
     *
     * @param ray The ray to be traced.
     * @return The color of the intersection point.
     */
    public abstract Color traceRay(Ray ray);
}
