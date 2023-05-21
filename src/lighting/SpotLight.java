package lighting;

import primitives.*;

/**

 This class represents a spotlight in a scene.

 A spotlight is a type of point light that emits light in a specific direction within a narrow beam.
 */
public class SpotLight extends PointLight {
    protected double narrowBeam = 1;
    private Vector direction;

    /**

     Constructs a SpotLight object with the specified color, position, and direction.
     @param color The color of the light
     @param position The position of the light
     @param direction The direction of the light beam
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction;
    }
    @Override
    public Color getIntensity(Point p) {
        double cos = direction.normalize().dotProduct(getL(p));
        if (cos > 0) return super.getIntensity(p).scale(Math.pow(cos, narrowBeam));
        return new Color(0, 0, 0);
    }

    @Override
    public Vector getL(Point p) {
        return super.getL(p).normalize();
    }

    /**

     Sets the narrowness of the light beam.
     @param narrowness The narrowness of the light beam
     @return This SpotLight object with the updated narrowness value
     */
    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowBeam = narrowness;
        return this;
    }
}
