package lighting;

import primitives.Color;

/**
 * This abstract class represents a light source in a scene.
 */
abstract class Light {
    protected final Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity The intensity of the light
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Gets the intensity of the light.
     *
     * @return The Color representing the intensity of the light
     */
    public Color getIntensity() {
        return intensity;
    }
}
