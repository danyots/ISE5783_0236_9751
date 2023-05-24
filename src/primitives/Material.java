package primitives;

/**
 * Represents the material properties of a geometry, including diffuse and specular reflection coefficients
 * <p>
 * and the shininess factor for specular reflection.
 */
public class Material {
    public Double3 kD = Double3.ZERO; // Diffuse reflection coefficient
    public Double3 kS = Double3.ZERO; // Specular reflection coefficient
    public int nShininess = 0; // Shininess factor for specular reflection
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient as a Double3 value
     * @return The Material object with the updated diffuse reflection coefficient
     */
    public Material setKd(Double3 kD) {
        this.kD = kD;
        return this;
    }
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }
    public Material setKr(double kR) {
        this.kR = new Double3(kR);
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient of the material.
     *
     * @param kD The diffuse reflection coefficient as a double value
     * @return The Material object with the updated diffuse reflection coefficient
     */
    public Material setKd(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient as a Double3 value
     * @return The Material object with the updated specular reflection coefficient
     */
    public Material setKs(Double3 kS) {
        this.kS = kS;
        return this;
    }

    /**
     * Sets the specular reflection coefficient of the material.
     *
     * @param kS The specular reflection coefficient as a double value
     * @return The Material object with the updated specular reflection coefficient
     */
    public Material setKs(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * Sets the shininess factor for specular reflection.
     *
     * @param nShininess The shininess factor as an integer value
     * @return The Material object with the updated shininess factor
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
