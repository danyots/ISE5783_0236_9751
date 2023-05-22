package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**

 This class represents a directional light source in a scene.
 */
public class DirectionalLight extends Light implements LightSource {
    private Vector direction;

    /**

     Constructs a DirectionalLight object with the specified color and direction.
     @param color The color of the light
     @param direction The direction of the light
     */
    public DirectionalLight(Color color, Vector direction) {
        super(color);
        this.direction = direction;
    }
    /**

     Gets the direction of the light.
     @return The Vector representing the direction of the light
     */
    public Vector getDirection() {
        return direction;
    }
    @Override
    public Color getIntensity(Point p) {
        return super.getIntensity();
    }

    @Override
    public Vector getL(Point p) {
        return direction.normalize();
    }
}
