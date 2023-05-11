package renderer;

import primitives.*;

import java.util.List;
import java.util.MissingResourceException;

/**
 * The Camera class represents a camera in a 3D space.
 * The camera is defined by its position and orientation.
 */
public class Camera {
    private final Point p0;
    private final Vector vUp;
    private final Vector vTo;
    private final Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;


    /**
     * This class represents a camera in a 3D space.
     * The camera is defined by its position and orientation.
     */
    public Camera(Point p, Vector _vTo, Vector _vUp) {
        if (!Util.isZero(_vTo.dotProduct(_vUp)))
            throw new IllegalArgumentException("direction vectors must be orthogonal");
        p0 = p;
        vRight = _vTo.crossProduct(_vUp).normalize();
        vUp = _vUp.normalize();
        vTo = _vTo.normalize();
    }

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

    /**
     * Sets the viewport size of the camera.
     *
     * @param width  The width of the viewport
     * @param height The height of the viewport
     * @return This camera object with the updated viewport size
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Sets the distance between the camera and the viewport.
     *
     * @param distance The distance between the camera and the viewport
     * @return This camera object with the updated distance value
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }

    /**
     * Constructs a new ray from the camera's position and orientation, passing through the specified pixel in the viewport.
     *
     * @param nX The number of pixels in the x-axis of the viewport
     * @param nY The number of pixels in the y-axis of the viewport
     * @param j  The x-coordinate of the pixel in the viewport
     * @param i  The y-coordinate of the pixel in the viewport
     * @return A new Ray object representing the ray passing through the specified pixel in the viewport
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        double rY = Util.alignZero(height / nY);
        double rX = Util.alignZero(height / nX);
        Point pC = p0.add(vTo.scale(distance));
        double yI = Util.alignZero(-Util.alignZero(i - Util.alignZero(Util.alignZero(nY - 1) / 2)) * rY);
        double xJ = Util.alignZero(Util.alignZero(j - Util.alignZero(Util.alignZero(nX - 1) / 2)) * rX);
        Point pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
    }
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }
    public void  renderImage(){
        List<Object> lst = List.of(p0, vTo, vRight, vUp, width, height, distance, imageWriter, rayTracer);
        for(Object obj:lst){
            if(obj==null) {
                throw new MissingResourceException("feild is uninitialized","Camera",obj.toString());
            }
        }
        throw new UnsupportedOperationException("this method is not supported");
    }

    public Camera rotateRight(double angle) {
        double theta = -Math.toRadians(angle);
        return new Camera(p0, vTo, rotate(theta)).setVPSize(width, height).setVPDistance(distance);
    }

    public Camera rotateLeft(double angle) {
        double theta = Math.toRadians(angle);
        return new Camera(p0, vTo, rotate(theta)).setVPSize(width, height).setVPDistance(distance);
    }
    /**
     * Rotates the camera's vRight and vUp vectors by the given angle around the vTo vector.
     *
     * @param angle The angle (in degrees) to rotate the vectors by.
     */
    /**
     * Rotates vRight and vUp by a given angle when vRight, vUp, and vTo are orthogonal each other
     * @param angle The angle to rotate vRight and vUp by, in degrees
     */
    /**
     * Rotates vRight.
     *
     * @param angle The angle to rotate by, in degrees.
     */

    public Vector rotate(double angle) {
        Vector axis = vTo.crossProduct(vUp).normalize();
        double cosAngle = Util.alignZero(Math.cos(angle));
        double sinAngle = Util.alignZero(Math.sin(angle));
        Vector vupPrime=null;
        if(!Util.isZero(sinAngle)&&!Util.isZero(cosAngle))vupPrime = vUp.scale(cosAngle).add(axis.scale(sinAngle));
        else{
            if (Util.isZero(sinAngle)) vupPrime = vUp.scale(cosAngle);
            if (Util.isZero(cosAngle)) vupPrime = axis.scale(sinAngle);
        }
        return vupPrime.normalize();
    }


}
