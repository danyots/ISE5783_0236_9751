package geometries;

import primitives.Ray;
import scene.Scene;

import java.util.LinkedList;
import java.util.List;

/**
 * The Geometries class represents a collection of intersectable objects.
 * It implements the Intersectable interface and provides methods to add new geometries to the collection
 * and find intersections of a given Ray with all the geometries in the collection.
 */
public class Geometries extends Intersectable {

    /**
     * A list that holds all the geometries in the collection.
     */
    List<Intersectable> geometries = new LinkedList<>();

    /**
     * Constructs a new empty Geometries collection.
     */
    public Geometries() {
    }

    /**
     * Constructs a new Geometries collection from an array of Intersectable objects.
     *
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        add(geometries);
    }

    /**
     * Adds Intersectable objects to the collection.
     *
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> intersects = null;
        for (Intersectable i : geometries) {
            if(Scene.isAABB){
                if(!i.isIntersectBox(ray))continue;
            }
            List<GeoPoint> geometryIntersects = i.findGeoIntersections(ray, maxDistance);
            if (geometryIntersects != null) {
                if (intersects == null)
                    intersects = new LinkedList<>();

                intersects.addAll(geometryIntersects);
            }
        }
        return intersects;
    }

    @Override
    public boolean isIntersectBox(Ray ray) {
        boolean isIntersec = false;
        for(Intersectable g:geometries){
            isIntersec = isIntersec || g.isIntersectBox(ray);
        }
        return  isIntersec;
    }
}
