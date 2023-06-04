package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import primitives.*;
import static primitives.Util.isZero;

public class Blackboard {
    protected Point pC;
    public List<Ray> beamRays=new LinkedList<>();
    protected Vector vUp;
    protected Vector vRight;
    protected double width=0;

    public int getDensityBeam() {
        return densityBeam;
    }

    private int densityBeam=9;
    private Ray main;

    public Blackboard(double kB){
        width=kB;
    }
    public Ray getMain(){
        return main;
    }

    /**
     * Constructs a camera with the specified position, target, and up vectors.
     * The direction vectors must be orthogonal.
     *
     * @param p   The position of the camera
     * @param vTo The direction vector pointing towards the target
     * @param vUp The direction vector pointing upwards
     * @throws IllegalArgumentException if the direction vectors are not orthogonal
     */
    public Blackboard setRays(Ray ray) {
        main=ray;
        Vector vRay=main.getDir();
        Point pRay=main.getP0();
        pC = pRay.add(vRay.scale(3));
        if(vRay.equals(new Vector(0,0,1))||vRay.equals(new Vector(0,0,-1))) vUp=new Vector(0,1,0);
        else {
            vUp = new Vector(-vRay.getY(), vRay.getX(), 0).normalize();
        }
        this.vRight=vRay.crossProduct(vUp);
        this.beamRays=constructRays(pRay);
        return this;
    }



    /**
     * Returns the position of the camera.
     *
     * @return The position of the camera
     */
    public Point getPC() {
        return pC;
    }

    /**
     * Returns the up vector of the camera.
     *
     * @return The up vector of the camera
     */
    public Vector getvUp() {
        return vUp;
    }

    /**
     * Returns the direction vector of the camera.
     *
     * @return The direction vector of the camera
     */

    /**
     * Returns the right vector of the camera.
     *
     * @return The right vector of the camera
     */
    public Vector getvRight() {
        return vRight;
    }

    /**
     * Returns the width of the viewport.
     *
     * @return The width of the viewport
     */
    public double getWidth() {
        return width;
    }
    /**
     * Returns the distance between the camera and the viewport.
     *
     * @return The distance between the camera and the viewport
     */

    /**
     * Sets the distance between the camera and the viewport.
     *
     * @param distance The distance between the camera and the viewport
     * @return This camera object with the updated distance value
     */
    public List<Ray> constructRays(Point pRay) {
        List<Ray> rays = new LinkedList<>();
        if(width==0||densityBeam==0||densityBeam==1)return List.of(main);
        for(int i=0;i<densityBeam;i++) {
            for (int j = 0; j < densityBeam; j++) {
                double rY = Util.alignZero(width / densityBeam);
                double rX = Util.alignZero(width / densityBeam);
                double randomX= Util.alignZero(ThreadLocalRandom.current().nextDouble()*2*rX-rX);
                double randomY= Util.alignZero(ThreadLocalRandom.current().nextDouble()*2*rY-rY);
                double yI = Util.alignZero(-Util.alignZero(i - Util.alignZero(densityBeam - 1) / 2) * Util.alignZero(rY)+randomY);
                double xJ = Util.alignZero(Util.alignZero(j - Util.alignZero(densityBeam - 1) / 2) * Util.alignZero(rX)+randomX);
                Point pIJ = pC;
                if (!isZero(xJ)) pIJ = pIJ.add(vRight.scale(xJ));
                if (!isZero(yI)) pIJ = pIJ.add(vUp.scale(yI));
                Vector v=pIJ.subtract(pRay);
                if(pIJ.distance(pC)<width/2)
                    rays.add(new Ray(pRay,pIJ.subtract(pRay)));
            }
        }
        return rays;
    }

    public List<Ray> getBeamRays() {
        return beamRays;
    }

    public Blackboard setDensityBeam (int densityBeam){
        this.densityBeam = densityBeam;
        if(main!=null) this.beamRays=constructRays(main.getP0());
        return this;
    }
}
