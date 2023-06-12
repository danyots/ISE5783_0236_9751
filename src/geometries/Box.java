package geometries;

import primitives.Ray;

public class Box {
    private double minX;
    private double minY;
    private double minZ;
    private double maxX;
    private double maxY;
    private double maxZ;

    public Box(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;
    }
    public boolean intersects(Ray ray) {
        double tMin = Double.NEGATIVE_INFINITY;
        double tMax = Double.POSITIVE_INFINITY;

        // Calculate minimum and maximum t-values for each axis
        double tXMin = (minX - ray.getP0().getX()) / ray.getDir().getX();
        double tXMax = (maxX - ray.getP0().getX()) / ray.getDir().getX();
        double tYMin = (minY - ray.getP0().getY()) / ray.getDir().getY();
        double tYMax = (maxY - ray.getP0().getY()) / ray.getDir().getY();
        double tZMin = (minZ - ray.getP0().getZ()) / ray.getDir().getZ();
        double tZMax = (maxZ - ray.getP0().getZ()) / ray.getDir().getZ();

        // Determine the largest minimum t-value and smallest maximum t-value
        tMin = Math.max(tMin, Math.max(Math.max(Math.min(tXMin, tXMax), Math.min(tYMin, tYMax)), Math.min(tZMin, tZMax)));
        tMax = Math.min(tMax, Math.min(Math.min(Math.max(tXMin, tXMax), Math.max(tYMin, tYMax)), Math.max(tZMin, tZMax)));

        // Check if there is an intersection
        return tMin <= tMax;
    }

}
