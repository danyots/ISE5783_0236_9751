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
        Color color = calcLocalEffects(intersection, ray,k);
        return  level == 1 ? color
                : color.add(calcGlobalEffects(intersection, ray, level, k));
    }
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K).add(scene.ambientLight.getIntensity());
    }
    private Color calcGlobalEffects(GeoPoint gp, Ray ray, int level, Double3 k) {
        Vector v = ray.getDir();
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        return calcGlobalEffect(constructReflectedRay(gp, v, n),level, k, material.kR).add(calcGlobalEffect(constructRefractedRay(gp, v, n),level, k, material.kT));}
    private Ray constructReflectedRay(GeoPoint gp,Vector v,Vector n){
        if(Util.isZero(v.dotProduct(n)))return null;

        return new Ray(gp.point,v.subtract(n.scale(2*Util.alignZero(v.dotProduct(n)))).normalize(),n);
    }
    private Ray constructRefractedRay(GeoPoint gp,Vector v,Vector n){
        return new Ray(gp.point,v.normalize(),n);
    }
    private Color calcGlobalEffect(Ray ray, int level, Double3 k, Double3 kx) {
        Double3 kkx = k.product(kx);
        if (kkx.lowerThan(MIN_CALC_COLOR_K)) return Color.BLACK;
        GeoPoint gp = findClosestIntersection(ray);
        if (gp == null) return scene.background.scale(kx);
        return Util.isZero(gp.geometry.getNormal(gp.point).dotProduct(ray.getDir()))? Color.BLACK : calcColor(gp, ray,level-1, kkx).scale(kx);
    }
    private GeoPoint findClosestIntersection(Ray ray){
        List <GeoPoint> points = scene.geometries.findGeoIntersections(ray);
        return ray.findClosestGeoPoint(points);
    }

    /**
     * Calculates the local effects of light on a given intersection point.
     * This includes diffuse and specular reflections.
     *
     * @param gp  The geometric intersection point
     * @param ray The ray that intersects the geometry
     * @return The color after considering the local effects of light
     */
    private Color calcLocalEffects(GeoPoint gp, Ray ray,Double3 k) {
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
                Double3 ktr = transparency( gp,lightSource,l, n);
                if (! ktr.product(k).lowerThan(MIN_CALC_COLOR_K)){
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
    private boolean unshaded(GeoPoint gp, LightSource light, Vector l, Vector n, double nl) {
        if(gp.geometry.getMaterial().kT !=Double3.ZERO)return true;
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(gp.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,light.getDistance(gp.point));
        return intersections==null;
    }
    private Double3 transparency(GeoPoint geoPoint, LightSource ls, Vector l, Vector n) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Ray lightRay = new Ray(geoPoint.point, lightDirection,n);
        List<GeoPoint> intersections = scene.geometries.findGeoIntersections(lightRay,ls.getDistance(geoPoint.point));
        Double3 ktr = Double3.ONE;
        if(intersections==null)  return Double3.ONE;
        for(GeoPoint gp : intersections){
            ktr=ktr.product(gp.geometry.getMaterial().kT);
        }
        return ktr;
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
