package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Util;
import primitives.Vector;

public class PointLight extends Light implements LightSource{
    private Point position;
    private double kC=1,kL=0,kQ=0;

    public PointLight(Color color, Point position){
        super(color);
        this.position=position;
    }

    public PointLight setKc(double kC) {
        this.kC = kC;
        return this;
    }

    public PointLight setKl(double kL) {
        this.kL = kL;
        return this;
    }

    public PointLight setKq(double kQ) {
        this.kQ = kQ;
        return this;
    }
    @Override
    public Color getIntensity(Point p){
        double d = p.distance(position);
        return getIntensity().reduce(Util.alignZero(kC+kL*d+kQ*d*d));
    }
    @Override
    public Vector getL(Point p){
        return p.subtract(position).normalize();
    }

}
