package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    public static final AmbientLight NONE= new AmbientLight(Color.BLACK,Double3.ZERO);
    private Color intensity;
    public AmbientLight(Color iA,Double3 kA){
        intensity=iA.scale(kA);
    }
    public AmbientLight(Color iA,double kA){
        intensity=iA.scale(kA);
    }

    public Color getIntensity() {
        return intensity;
    }

}
