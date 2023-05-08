package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

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
        double yI = Util.alignZero(-1 * Util.alignZero(i - Util.alignZero(Util.alignZero(nY - 1) / 2)) * rY);
        double xJ = Util.alignZero(Util.alignZero(j - Util.alignZero(Util.alignZero(nX - 1) / 2)) * rX);
        Point pIJ = pC;
        if (xJ != 0) pIJ = pIJ.add(vRight.scale(xJ));
        if (yI != 0) pIJ = pIJ.add(vUp.scale(yI));
        pIJ.add(vTo.scale(distance));
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
    }

}
