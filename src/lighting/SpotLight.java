package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

public class SpotLight extends PointLight{
    private Vector direction;
    public SpotLight(Color color, Point position, Vector direction){
        super(color,position);
        this.direction=direction;
    }
}
