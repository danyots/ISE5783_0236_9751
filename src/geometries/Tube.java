package geometries;


import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry{
    final protected Ray axisRay;

    public Tube(Ray axisray, double r){
        super(r);
        axisRay=axisray;
    }

    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + axisRay +
                ", radius=" + radius +
                '}';
    }

    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
