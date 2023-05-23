package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * This interface represents a light source in a scene.
 **/
public interface LightSource {
    /*

    Gets the intensity of the light at a given point.
    @param p The point at which to calculate the intensity
    @return The Color representing the intensity of the light
    */
    public Color getIntensity(Point p);

    /**
     * Gets the direction of the light from a given point.
     *
     * @param p The point from which to calculate the direction
     * @return The Vector representing the direction of the light
     */
    public Vector getL(Point p);
    double getDistance(Point point);


}
