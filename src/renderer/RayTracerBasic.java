package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * This class represents a basic ray tracer that extends the RayTracerBase class.
 * It provides an implementation for the traceRay method to trace rays in a scene and calculate the color of the intersection points.
 */
public class RayTracerBasic extends RayTracerBase {
    private static final Double3 INITIAL_K = Double3.ONE;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;

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
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.background : calcColor(closestPoint, ray);
    }


    /**
     * Calculates the color at a given point in the scene.
     *
     * @param intersection The point for which to calculate the color.
     * @return The color at the given point.
     */
    private Color calcColor(GeoPoint intersection, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = intersection.geometry.getNormal(intersection.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) return scene.background;
        Color color = calcLocalEffects(intersection, n, v, nv, k);
        return level == 1 ? color : color.add(calcGlobalEffects(intersection, n, v, level, k));
    }

    /**
     * Calculates the color at a given GeoPoint by considering the global effects and adding the ambient light intensity.
     *
     * @param geoPoint The GeoPoint to calculate the color for.
     * @param ray      The Ray associated with the calculation.
     * @return The calculated Color at the GeoPoint.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        return calcColor(geoPoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }
    /**
     * Calculates the color of global effects for a given GeoPoint.
     *
     * @param gp      The GeoPoint at which to calculate the global effects.
     * @param n       The surface normal at the GeoPoint.
     * @param v       The viewing direction.
     * @param level   The recursion level for ray tracing.
     * @param k       The coefficient values.
     * @return The calculated color of the global effects.
     */
    private Color calcGlobalEffects(GeoPoint gp, Vector n, Vector v, int level, Double3 k) {
        Material material = gp.geometry.getMaterial();

        return calcGlossyMattColor(constructReflectedRay(gp, v, n), n, level, k, material, material.kR).add(
                calcGlossyMattColor(constructRefractedRay(gp, v, n), n, level, k, material, material.kT));
    }

    /**
     * Calculates the color of glossy and matte effects for a given optic ray.
     *
     * @param optic    The optic ray to calculate the effects for.
     * @param n        The surface normal at the point of intersection.
     * @param level    The recursion level for ray tracing.
     * @param k        The coefficient values.
     * @param material The material of the object.
     * @param kx       The coefficient values for the optic ray.
     * @return The calculated color of the glossy and matte effects.
     */
    private Color calcGlossyMattColor(Ray optic, Vector n, int level, Double3 k, Material material, Double3 kx) {
        Color color = Color.BLACK;
        List<Ray> rayBeam = optic.calculateBeam(material.blackBoard);
        for (Ray ray : rayBeam) {
            if (optic.getDir().dotProduct(n) * ray.getDir().dotProduct(n) > 0) {
                color = color.add(calcGlobalEffect(ray, level, k, kx));
            }
        }
        int density = material.blackBoard.getDensityBeam();
        return isZero(material.blackBoard.getWidth()) || material.blackBoard.getDensityBeam() <= 1 ? color : color.reduce(density * density);
    }

    /**
     * Constructs a reflected ray based on a GeoPoint, incident vector, and surface normal.
     *
     * @param gp The GeoPoint of reflection.
     * @param v  The incident vector.
     * @param n  The surface normal.
     * @return The constructed reflected Ray.
     */
    private Ray constructReflectedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v.subtract(n.scale(2 * Util.alignZero(v.dotProduct(n)))), n);
    }

    /**
     * Constructs a refracted ray based on a GeoPoint, incident vector, and surface normal.
     *
     * @param gp The GeoPoint of refraction.
     * @param v  The incident vector.
     * @param n  The surface normal.
     * @return The constructed refracted Ray.
     */
    private Ray constructRefractedRay(GeoPoint gp, Vector v, Vector n) {
        return new Ray(gp.point, v, n);
    }

    /**
     * Calculates the global effect (reflection or refraction) for a given Ray.
     *
     * @param ray   The Ray to calculate the global effect for.
     * @param level The current recursion level.
     * @param k     The accumulated transparency coefficient.
     * @param kx    The transparency coefficient of the material.
     * @return The calculated Color representing the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir())) ? Color.BLACK //
                : calcColor(gp, ray, level - 1, kkx).scale(kx);
    }

    /**
     * Finds the closest intersection point between a given Ray and the geometries in the scene.
     *
     * @param ray The Ray to find intersections with.
     * @return The closest GeoPoint representing the closest intersection, or null if no intersection is found.
     */

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }

    /**
     * Calculates the local effects of light on a given intersection point.
     * This includes diffuse and specular reflections.
     *
     * @param gp The geometric intersection point
     * @param n  The surface normal
     * @param v  The view direction
     * @param nv The dot product between the surface normal and the light direction
     * @return The color after considering the local effects of light
     */
    private Color calcLocalEffects(GeoPoint gp, Vector n, Vector v, double nv, Double3 k) {
        Color color = gp.geometry.getEmission();
        Material material = gp.geometry.getMaterial();
        for (LightSource lightSource : scene.lights) {
            Vector l = lightSource.getL(gp.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sign(nv)
                Double3 ktr = transparency(gp, lightSource, l, n);
                if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                    Color iL = lightSource.getIntensity(gp.point).scale(ktr);
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

    /**
     * Checks if a point in space is unshaded by determining if there is direct visibility between the point and a light source.
     *
     * @param gp    The GeoPoint representing the point in space.
     * @param light The LightSource to check visibility with.
     * @param l     The vector representing the direction from the point to the light source.
     * @param n     The normal vector at the point.
     * @param nl    The dot product between the normal vector and the direction vector to the light source.
     * @return {@code true} if the point is unshaded, {@code false} otherwise.
     */
    @SuppressWarnings("unused")
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        if (gp.geometry.getMaterial().kT != Double3.ZERO) return true;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection, n);
        return scene.geometries.findGeoIntersections(lightRay, light.getDistance(gp.point)) == null;
    }

    /**
     * Calculates the transparency of a point in space with respect to a light source.
     *
     * @param geoPoint The GeoPoint representing the point in space.
     * @param ls       The LightSource to calculate transparency for.
     * @param l        The vector representing the direction from the point to the light source.
     * @param n        The normal vector at the point.
     * @return The transparency as a Double3 representing RGB values.
     */
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection, n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay, ls.getDistance(geoPoint.point));
        Double3 ktr = Double3.ONE;
        if (intersections == null) return Double3.ONE;
        for (GeoPoint gp : intersections) {
            ktr = ktr.product(gp.geometry.getMaterial().kT);
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) return Double3.ZERO;
        }
        return ktr;
    }

}
