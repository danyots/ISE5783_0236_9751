package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * This class represents a basic ray tracer that extends the RayTracerBase class.
 * It provides an implementation for the traceRay method to trace rays in a scene and calculate the color of the intersection points.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final double DELTA = 0.1;

    /**
     * Constructs a RayTracerBasic object with the given scene.
     *
     * @param scene The scene to be rendered.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }


    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersectionsHelper(ray,Double.POSITIVE_INFINITY);
        if (points == null)
            return scene.background;
        GeoPoint closest = ray.findClosestGeoPoint(points);
        return calcColor(closest, ray);
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

    /**
     * Calculates the local effects of light on a given intersection point.
     * This includes diffuse and specular reflections.
     *
     * @param gp  The geometric intersection point
     * @param ray The ray that intersects the geometry
     * @return The color after considering the local effects of light
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray) {
        Color color = gp.geometry.getEmission();
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return color;
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                if (unshaded(gp, lightSource, l, n, nl)) {
                    Color iL = lightSource.getIntensity(gp.point);
                    color = color.add(iL.scale(calcDiffuse(material, nl)).add(iL.scale(calcSpecular(material, n, l, nl, v))));
                }
            }
        }
        return color;
    }

    /**
     * Calculates the diffuse reflection component of a material at a given intersection point.
     *
     * @param material The material of the intersected geometry
     * @param nl       The dot product between the surface normal and the light direction
     * @return The color of the diffuse reflection
     */
    private Double3 calcDiffuse(Material material, double nl) {
        return material.kD.scale(nl < 0 ? -nl : nl);
    }

    /**
     * Calculates the specular reflection component of a material at a given intersection point.
     *
     * @param material The material of the intersected geometry
     * @param n        The surface normal
     * @param l        The light direction
     * @param nl       The dot product between the surface normal and the light direction
     * @param v        The view direction
     * @return The color of the specular reflection
     */
    private Double3 calcSpecular(Material material, Vector n, Vector l, double nl, Vector v) {
        Vector r = l.subtract(n.scale(2 * nl));
        double max = -r.dotProduct(v);
        return alignZero(max) > 0 ? material.kS.scale(Math.pow(max, material.nShininess)) : Double3.ZERO;
    }
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,light.getDistance(point));
        return intersections==null;
    }

    /*
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Vector epsVector = n.scale(nl < 0 ? DELTA : -DELTA);
        Point point = gp.point.add(epsVector);
        Ray lightRay = new Ray(point, lightDirection);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay);
        if (intersections == null) return true;
        for (GeoPoint geopoint : intersections) {
            if(light.getDistance(geopoint.point)>=point.distance(geopoint.point))return false;
        }
        return true;

    }
    */

}
