package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

/**
 * The Blackboard class represents a virtual blackboard used in rendering.
 * It is responsible for managing the placement and properties of rays on the blackboard.
 */
public class Blackboard {

    /**
     * The center point of the blackboard.
     */
    protected Point pC;

    /**
     * The upward direction vector on the blackboard.
     */
    protected Vector vUp;

    /**
     * The right direction vector on the blackboard.
     */
    protected Vector vRight;

    /**
     * The width of the blackboard. Default value is 0.
     */
    protected double width = 0;

    /**
     * The density of rays in the beam. Default value is 9.
     */
    private int densityBeam = 9;

    /**
     * The distance between the blackboard and the rays.
     */
    private double distance = 3;

    /**
     * Constructs a blackboard with the specified width.
     *
     * @param kB The width of the blackboard.
     */
    public Blackboard(double kB) {
        width = kB;
    }

    /**
     * Gets the density of rays in the beam.
     *
     * @return The density of rays in the beam.
     */
    public int getDensityBeam() {
        return densityBeam;
    }

    /**
     * Sets the density of rays in the beam.
     *
     * @param densityBeam The density of rays in the beam.
     * @return The updated blackboard.
     */
    public Blackboard setDensityBeam(int densityBeam) {
        this.densityBeam = densityBeam;
        return this;
    }

    /**
     * Gets the width of the blackboard.
     *
     * @return The width of the blackboard.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Sets the width of the blackboard.
     *
     * @param width The width of the blackboard.
     * @return The updated blackboard.
     */
    public Blackboard setWidth(double width) {
        this.width = width;
        return this;
    }

    /**
     * Sets the rays for the given ray.
     *
     * @param ray The ray for which to set the rays.
     * @return The list of points representing the rays.
     */
    public List<Point> setRays(Ray ray) {
        Vector vRay = ray.getDir();
        Point pRay = ray.getP0();
        pC = pRay.add(vRay.scale(distance));
        if (vRay.equals(new Vector(0, 0, 1)) || vRay.equals(new Vector(0, 0, -1))) {
            vUp = new Vector(0, 1, 0);
        } else {
            vUp = new Vector(-vRay.getY(), vRay.getX(), 0).normalize();
        }
        this.vRight = vRay.crossProduct(vUp);
        return constructGrid(ray);
    }

    /**
     * Constructs a grid of points along the given ray.
     *
     * @param ray the ray to construct the grid along
     * @return a list of points forming the grid
     */
    private List<Point> constructGrid(Ray ray) {
        List<Point> points = new LinkedList<>();
        if (width == 0 || densityBeam == 0 || densityBeam == 1) {
            return List.of(ray.getP0().add(ray.getDir()));
        }
        double relative = Math.sqrt(1.27324);
        double rG = Util.alignZero(width / (densityBeam * relative));
        for (int i = 0; i < densityBeam * relative; i++) {
            for (int j = 0; j < densityBeam * relative; j++) {
                Random r = new Random();
                double randomX = Util.alignZero(r.nextDouble() * rG - (rG / 2));
                double randomY = Util.alignZero(r.nextDouble() * rG - (rG / 2));
                double yI = Util.alignZero(-Util.alignZero(i - Util.alignZero(densityBeam * relative - 1) / 2) * Util.alignZero(rG) + randomY);
                double xJ = Util.alignZero(Util.alignZero(j - Util.alignZero(densityBeam * relative - 1) / 2) * Util.alignZero(rG) + randomX);
                Point pIJ = pC;
                if (!isZero(xJ)) {
                    pIJ = pIJ.add(vRight.scale(xJ));
                }
                if (!isZero(yI)) {
                    pIJ = pIJ.add(vUp.scale(yI));
                }
                if (pIJ.distance(pC) < width / 2) {
                    points.add(pIJ);
                }
            }
        }
        return points;
    }

}
