package primitives;

/**
 * The Ray class represents a ray in 3D space. A ray is defined by a starting point and a direction.
 */
public class Ray {

    final private Point p0;

    final private Vector dir;

    /**
     * Constructs a new Ray object with the given starting point and direction.
     *
     * @param p The starting point of the ray.
     * @param v The direction of the ray.
     */
    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the direction of the ray.
     *
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Ray) {
            Ray other = (Ray) obj;
            return this.p0.equals(other.p0) && this.dir.equals(other.dir) ;
        }
        return false;
    }


        @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0.toString() +
                ", dir=" + dir.toString() +
                '}';
    }
}
