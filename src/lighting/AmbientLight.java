package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * Represents ambient light in a scene.
 */
public class AmbientLight {
    /**
     * A constant representing no ambient light, with an intensity of Color.BLACK and scaling factor of Double3.ZERO.
     */
    public static final AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    private Color intensity;

    /**
     * Constructs an AmbientLight object with the specified intensity and scaling factor.
     *
     * @param iA The intensity of the ambient light.
     * @param kA The scaling factor for the intensity.
     */
    public AmbientLight(Color iA, Double3 kA) {
        intensity = iA.scale(kA);
    }

    /**
     * Constructs an AmbientLight object with the specified intensity and scaling factor.
     *
     * @param iA The intensity of the ambient light.
     * @param kA The scaling factor for the intensity.
     */
    public AmbientLight(Color iA, double kA) {
        intensity = iA.scale(kA);
    }

    /**
     * Retrieves the intensity of the ambient light.
     *
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
