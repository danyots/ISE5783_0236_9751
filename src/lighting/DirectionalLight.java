package lighting;

import primitives.Color;
import primitives.Vector;

public class DirectionalLight extends Light implements LightSource{
    private Vector direction;

    public DirectionalLight(Color color,Vector direction){
        super(color);
        this.direction=direction;
    }

}
