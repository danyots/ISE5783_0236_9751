package renderer;

import geometries.Intersectable;
import lighting.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable.GeoPoint;

import java.util.List;

import static primitives.Util.alignZero;

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
        return calcColor(closest,ray);
    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param point The point for which to calculate the color.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint point, Ray ray) {
        return scene.ambientLight.getIntensity().add(calcLocalEffects(point, ray));

    }
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir ();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color iL = lightSource.getIntensity(gp.point);
                color = color.add(iL.scale(calcDiffusive(material, nl)).add(iL.scale(calcSpecular(material, n, l, nl, v))));
            }
        }
        return color;
    }
    private Double3 calcDiffusive(Material mat, double nl){
        return mat.kD.scale(Math.abs(nl));
    }
    private Double3 calcSpecular(Material mat, Vector n, Vector l, double nl,Vector v){
        Vector r = l.subtract(n.scale(2*nl));
        double max = -1*(r.dotProduct(v));
        if(max>0)return mat.kS.scale(Math.pow(max,mat.nShininess));
        return new Double3(0,0,0);
    }

}
