package primitives;

public class Ray {
    final private Point p0;
    final private Vector dir;

    public Ray(Point p, Vector v) {
        p0 = p;
        dir = v.normalize();
    }

    public Point getP0() {
        return p0;
    }

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
