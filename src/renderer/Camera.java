package renderer;

import primitives.*;

import java.util.List;
import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a camera in a 3D space.
 * The camera is defined by its position and orientation.
 */
public class Camera {
    private final Point p0;
    private Vector vUp;
    private final Vector vTo;
    private Vector vRight;
    private double width;
    private double height;
    private double distance;
    private ImageWriter imageWriter;
    private RayTracerBase rayTracer;

    /**
     * This class represents a camera in a 3D space.
     * The camera is defined by its position and orientation.
     * @param p
     * @param vTo
     * @param vUp
     */
    public Camera(Point p, Vector vTo, Vector vUp) {
        if (!isZero(vTo.dotProduct(vUp)))
            throw new IllegalArgumentException("direction vectors must be orthogonal");
        p0 = p;
        vRight = vTo.crossProduct(vUp).normalize();
        this.vUp = vUp.normalize();
        this.vTo = vTo.normalize();
    }

    public Camera(Point location, Point target) {
        p0 = location;
        vTo = target.subtract(location).normalize();
        try {
            vRight = vTo.crossProduct(Vector.Y).normalize();
        }catch(IllegalArgumentException ignore) {
            vRight = Vector.Z;
        }
        this.vUp = vRight.crossProduct(vTo);
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
        double yI = -Util.alignZero(i - Util.alignZero(nY - 1) / 2) * Util.alignZero(rY);
        double xJ = Util.alignZero(j - Util.alignZero(nX - 1) / 2) * Util.alignZero(rX);
        Point pIJ = pC;
        if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ));
        if (!isZero(yI)) pIJ = pIJ.add(vUp.scale(yI));
        Vector vIJ = pIJ.subtract(p0);
        return new Ray(p0, vIJ);
    }

    /**
     * Prints a grid pattern on the image with the specified interval and color.
     * The grid pattern is created by writing pixels with the specified color on every i-th row and j-th column,
     * where i and j are multiples of the given interval.
     *
     * @param interval The interval between grid lines.
     * @param color    The color of the grid lines.
     * @throws MissingResourceException if the imageWriter is not initialized.
     */
    public void printGrid(int interval, Color color) throws MissingResourceException {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is not initialized", "Camera", "imageWriter");

        for (int i = 0; i < imageWriter.getNx(); i++) {
            for (int j = 0; j < imageWriter.getNy(); j++) {
                if (i % interval == 0 || j % interval == 0)
                    imageWriter.writePixel(i, j, color);
            }
        }

        writeToImage();
    }


    /**
     * Writes the image to the output file using the initialized imageWriter.
     * Throws a MissingResourceException if the imageWriter is not initialized.
     *
     * @throws MissingResourceException if the imageWriter is not initialized.
     */
    public void writeToImage() throws MissingResourceException {
        if (imageWriter == null)
            throw new MissingResourceException("imageWriter is not initialized", "Camera", "imageWriter");

        imageWriter.writeToImage();
    }

    /**
     * Sets the imageWriter for the camera.
     *
     * @param imageWriter The imageWriter to be set.
     * @return The camera object itself for method chaining.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Sets the rayTracer for the camera.
     *
     * @param rayTracer The rayTracer to be set.
     * @return The camera object itself for method chaining.
     */
    public Camera setRayTracer(RayTracerBase rayTracer) {
        this.rayTracer = rayTracer;
        return this;
    }

    /**
     * Renders the image by casting rays for each pixel in the imageWriter's dimensions
     * and writing the resulting color to the image.
     * Checks if all required fields are initialized before rendering.
     *
     * @throws MissingResourceException if any required field is uninitialized.
     */
    public void renderImage() throws MissingResourceException {
        List<Object> lst = List.of(p0, vTo, vRight, vUp, width, height, distance, imageWriter, rayTracer);
        for (Object obj : lst) {
            if (obj == null) {
                throw new MissingResourceException("field is uninitialized", "Camera", obj.toString());
            }
        }

        int numY = imageWriter.getNy();
        int numX = imageWriter.getNx();

        for (int i = 0; i < numY; i++) {
            for (int j = 0; j < numX; j++) {
                imageWriter.writePixel(j, i, castRay(numX, numY, i, j));
            }
        }

        writeToImage();
    }

    private Color castRay(int Nx, int Ny, int i, int j) {
        Ray ray = constructRay(Nx, Ny, j, i);
        return rayTracer.traceRay(ray);
    }

    /**
     * Rotates the camera to the right by the specified angle.
     *
     * @param angle The angle of rotation in degrees.
     * @return A new Camera object rotated to the right.
     */
    public Camera rotateRight(double angle) {
        double theta = -Math.toRadians(angle);
        return new Camera(p0, vTo, rotate(theta))
                .setVPSize(width, height)
                .setVPDistance(distance)
                .setImageWriter(imageWriter)
                .setRayTracer(rayTracer);
    }


    /**
     * Rotates the camera to the left by the specified angle.
     *
     * @param angle The angle of rotation in degrees.
     * @return A new Camera object rotated to the left.
     */

    public Camera rotateLeft(double angle) {
        double theta = Math.toRadians(angle);
        return new Camera(p0, vTo, rotate(theta)).setVPSize(width, height).setVPDistance(distance).setImageWriter(imageWriter).setRayTracer(rayTracer);
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
        Vector vupPrime = null;
        if (!isZero(sinAngle) && !isZero(cosAngle)) vupPrime = vUp.scale(cosAngle).add(axis.scale(sinAngle));
        else {
            if (isZero(sinAngle)) vupPrime = vUp.scale(cosAngle);
            if (isZero(cosAngle)) vupPrime = axis.scale(sinAngle);
        }
        return vupPrime.normalize();
    }


}
