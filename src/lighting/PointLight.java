package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

/**

 This class represents a point light source in a scene.
 */
public class PointLight extends Light implements LightSource {
    private Point position;
    private double kC = 1, kL = 0, kQ = 0;

    /**

     Constructs a PointLight object with the specified color and position.
     @param color The color of the light
     @param position The position of the light
     */
    public PointLight(Color color, Point position) {
        super(color);
        this.position = position;
    }
    /**

     Sets the constant attenuation factor of the light.
     @param kC The constant attenuation factor
     @return This PointLight object with the updated constant attenuation factor
     */
    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }
    /**

     Sets the linear attenuation factor of the light.
     @param kL The linear attenuation factor
     @return This PointLight object with the updated linear attenuation factor
     */
    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }
    /**

     Sets the quadratic attenuation factor of the light.
     @param kQ The quadratic attenuation factor
     @return This PointLight object with the updated quadratic attenuation factor
     */
    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    @Override
    public Color getIntensity(Point p) {
        double d = p.distance(position);
        return getIntensity().reduce(Util.alignZero(kC + kL * d + kQ * d * d));
    }

    @Override
    public Vector getL(Point p) {
        return p.subtract(position).normalize();
    }
}
