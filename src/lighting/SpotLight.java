package lighting;

import primitives.*;

public class SpotLight extends PointLight{
    protected double narrowBeam=1;
    private Vector direction;
    public SpotLight(Color color, Point position, Vector direction){
        super(color,position);
        this.direction=direction;
    }
    public Color getIntensity(Point p){
        double cos = direction.normalize().dotProduct(getL(p));
        if(cos>0) return super.getIntensity(p).scale(Math.pow(cos,narrowBeam));
        return new Color(0,0,0);
    }
    public Vector getL(Point p){
        return super.getL(p).normalize();
    }
    public SpotLight setNarrowBeam(double narrowness){
        this.narrowBeam=narrowness;
        return this;
    }


}
