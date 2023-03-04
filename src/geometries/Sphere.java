package geometries;

import primitives.Point;
import primitives.Vector;

public class Sphere extends RadialGeometry{
    final private Point center;

    public Sphere(Point point, double r){
        super(r);
        center=point;
    }

    public Point getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + center +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
