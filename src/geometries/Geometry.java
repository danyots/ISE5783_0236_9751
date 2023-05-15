package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The abstract class Geometry represents a geometry object.
 */

public abstract class  Geometry extends Intersectable {
    protected Color emission=Color.BLACK;
    private Material material = new Material();

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Color getEmission() {
        return emission;
    }

    /**
     * Computes the normal vector at the specified point on the surface of the geometry object.
     *
     * @param point The point on the surface of the object
     * @return The normal vector at the specified point
     */
    public abstract Vector getNormal(Point point);
}
