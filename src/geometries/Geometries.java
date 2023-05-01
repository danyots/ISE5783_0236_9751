package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * The Geometries class represents a collection of intersectable objects.
 * It implements the Intersectable interface and provides methods to add new geometries to the collection
 * and find intersections of a given Ray with all the geometries in the collection.
 */
public class Geometries implements Intersectable {

    /**
     * A list that holds all the geometries in the collection.
     */
    List<Intersectable> geometries;

    /**
     * Constructs a new empty Geometries collection.
     */
    public Geometries() {
        geometries = new LinkedList();
    }

    /**
     * Constructs a new Geometries collection from an array of Intersectable objects.
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public Geometries(Intersectable... geometries) {
        this.geometries = List.of(geometries);
    }

    /**
     * Adds Intersectable objects to the collection.
     * @param geometries an array of Intersectable objects to add to the collection.
     */
    public void add(Intersectable... geometries) {
        this.geometries.addAll(List.of(geometries));
    }

    @Override
    public List<Point> findIntersections(Ray ray) {
        boolean isIntersect = false;
        for (Intersectable i : geometries) {
            if (i.findIntersections(ray) != null) {
                isIntersect = true;
            }
        }
        if (!isIntersect) {
            return null;
        }
        List<Point> intersects = new LinkedList();
        for (Intersectable i : geometries) {
            List<Point> geometryIntersects = i.findIntersections(ray);
            if (geometryIntersects != null) {
                intersects.addAll(geometryIntersects);
            }
        }
        return intersects;
    }
}
