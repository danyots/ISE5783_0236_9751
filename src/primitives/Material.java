package primitives;

import renderer.Blackboard;

/**
 * Represents the material properties of a geometry, including diffuse and specular reflection coefficients
 * <p>
 * and the shininess factor for specular reflection.
 */
public class Material {
    /**
     * Diffuse reflection coefficient.
     */
    public Double3 kD = Double3.ZERO;
    /**
     * Specular reflection coefficient.
     */
    public Double3 kS = Double3.ZERO;
    /**
     * Shininess factor for specular reflection.
     */
    public int nShininess = 0;
    /**
     * Transmission coefficient for refracted light.
     */
    public Double3 kT = Double3.ZERO;
    /**
     * Reflection coefficient for reflected light.
     */
    public Double3 kR = Double3.ZERO;
    /**
     * The blackboard used for material properties.
     */
    public Blackboard blackBoard = new Blackboard(0);

    /**
     * Sets the reflection coefficient for the blackboard material.
     *
     * @param kB The reflection coefficient value to set.
     * @return The Material object itself.
     */
    public Material setKB(double kB) {
        blackBoard.setWidth(kB);
        return this;
    }

    /**
     * Sets the density of the beam for the blackboard material.
     *
     * @param density The density value to set.
     * @return The Material object itself.
     */
    @SuppressWarnings("unused")
    public Material setDensity(int density) {
        blackBoard.setDensityBeam(density);
        return this;
    }

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

    /**
     * Sets the transmission coefficient (kT) of the material.
     *
     * @param kT The transmission coefficient as a Double3 representing RGB values.
     * @return The Material object with the updated transmission coefficient.
     */
    public Material setKt(Double3 kT) {
        this.kT = kT;
        return this;
    }

    /**
     * Sets the reflection coefficient (kR) of the material.
     *
     * @param kR The reflection coefficient as a Double3 representing RGB values.
     * @return The Material object with the updated reflection coefficient.
     */
    public Material setKr(Double3 kR) {
        this.kR = kR;
        return this;
    }

    /**
     * Sets the transmission coefficient (kT) of the material using a single value for all RGB channels.
     *
     * @param kT The transmission coefficient as a double value.
     * @return The Material object with the updated transmission coefficient.
     */
    public Material setKt(double kT) {
        this.kT = new Double3(kT);
        return this;
    }

    /**
     * Sets the reflection coefficient (kR) of the material using a single value for all RGB channels.
     *
     * @param kR The reflection coefficient as a double value.
     * @return The Material object with the updated reflection coefficient.
     */
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
