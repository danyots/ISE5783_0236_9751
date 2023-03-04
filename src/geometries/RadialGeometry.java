package geometries;

import primitives.Point;
import primitives.Vector;

public abstract class RadialGeometry  implements Geometry{
    final protected double radius;

    public RadialGeometry(double r){
        radius=r;
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}