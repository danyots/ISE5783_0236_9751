package geometries;

/**
 * The abstract class RadialGeometry represents a geometry object with a radial size.
 */
public abstract class RadialGeometry implements Geometry {
    /**
     * The radius of the radial geometry object.
     */
    final protected double radius;


    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param r The radius of the new RadialGeometry object.
     */
    public RadialGeometry(double r) {
        radius = r;
    }
}
