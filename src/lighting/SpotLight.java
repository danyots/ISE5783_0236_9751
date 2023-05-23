package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.alignZero;

/**
 * This class represents a spotlight in a scene.
 * <p>
 * A spotlight is a type of point light that emits light in a specific direction within a narrow beam.
 */
public class SpotLight extends PointLight {
    protected double narrowBeam = 1;
    private final Vector direction;

    /**
     * Constructs a SpotLight object with the specified color, position, and direction.
     *
     * @param color     The color of the light
     * @param position  The position of the light
     * @param direction The direction of the light beam
     */
    public SpotLight(Color color, Point position, Vector direction) {
        super(color, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point p) {
        double cos = alignZero(direction.dotProduct(getL(p)));
        return cos > 0 ? super.getIntensity(p).scale(Math.pow(cos, narrowBeam)) : Color.BLACK;
    }

    /**
     * Sets the narrowness of the light beam.
     *
     * @param narrowness The narrowness of the light beam
     * @return This SpotLight object with the updated narrowness value
     */
    public SpotLight setNarrowBeam(double narrowness) {
        this.narrowBeam = narrowness;
        return this;
    }
}
