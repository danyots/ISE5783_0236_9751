package unittests.renderer;

import geometries.Sphere;
import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.List;

public class Camera {
    private Point p0;
    private Vector vUp;
    private Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;

    public Point getP0() {
        return p0;
    }

    public Vector getvUp() {
        return vUp;
    }

    public Vector getvTo() {
        return vTo;
    }

    public Vector getvRight() {
        return vRight;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getDistance() {
        return distance;
    }
    public Camera(Point p, Vector _vTo,Vector _vUp){
        if(!Util.isZero(_vTo.dotProduct(_vUp)))throw new IllegalArgumentException("direction vectors must be orthogonal");
        p0 = p;
        vRight=_vTo.crossProduct(_vUp).normalize();
        vUp=_vUp.normalize();
        vTo=_vTo.normalize();
    }
    public Camera setVPSize(double width, double height){
        this.width=width;
        this.height=height;
        return this;
    }
    public Camera setVPDistance(double distance) {
        this.distance=distance;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i){
        double rY = Util.alignZero(height/nY);
        double rX = Util.alignZero(height/nX);
        Point pC = p0.add(vTo.scale(distance));
        double yI = Util.alignZero(-1*Util.alignZero(i-Util.alignZero(Util.alignZero(nY-1)/2))*rY);
        double xJ = Util.alignZero(Util.alignZero(j-Util.alignZero(Util.alignZero(nX-1)/2))*rX);
        Point pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0,vIJ);
    }

}
