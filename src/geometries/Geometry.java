package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The abstract class Geometry represents a geometry object.
 */

public abstract class Geometry extends Intersectable {
    private Color emission = Color.BLACK;
    private Material material = new Material();

    /**
     * Gets the material of the geometry.
     *
     * @return The material of the geometry
     */
    public Material getMaterial() {
        return material;
    }

    /**
     * Sets the material of the geometry.
     *
     * @param material The material to set
     * @return The modified geometry object
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }

    /**
     * Gets the emission color of the geometry.
     *
     * @return The emission color of the geometry
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Sets the emission color of the geometry.
     *
     * @param emission The emission color to set
     * @return The modified geometry object
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    /**
     * Computes the normal vector at the specified point on the surface of the geometry object.
     *
     * @param point The point on the surface of the object
     * @return The normal vector at the specified point
     */
    public abstract Vector getNormal(Point point);
}
