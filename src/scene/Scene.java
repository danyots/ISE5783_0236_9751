package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.LightSource;
import primitives.Color;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene with its properties and geometries.
 */
@XmlRootElement(name = "scene")
public class Scene {


    public static boolean isAABB=false;
    /**
     * The name of the scene.
     */
    public final String name;
    /**
     * The background color of the scene.
     */
    public Color background = Color.BLACK;
    /**
     * The ambient light in the scene.
     */
    public AmbientLight ambientLight = AmbientLight.NONE;
    /**
     * The geometries present in the scene.
     */
    public Geometries geometries = new Geometries();
    /**
     * The list of light sources in the scene.
     */
    public List<LightSource> lights = new LinkedList<>();
    /**
     * The XML file associated with the scene.
     */
    @SuppressWarnings("unused")
    public XmlFile xmlFile;

    /**
     * Constructs a new Scene object with the specified name.
     *
     * @param name The name of the scene.
     */
    public Scene(String name) {
        this.name = name;
    }

    /**
     * Sets the background color of the scene.
     *
     * @param background The background color to set.
     * @return The updated Scene object.
     */
    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }
    public  Scene setIsAABB(boolean isAABB) {
        Scene.isAABB = isAABB;
        return  this;
    }

    /**
     * Sets the ambient light of the scene.
     *
     * @param ambientLight The ambient light to set.
     * @return The updated Scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    /**
     * Sets the geometries of the scene.
     *
     * @param geometries The geometries to set.
     * @return The updated Scene object.
     */

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    /**
     * Sets the list of light sources in the scene.
     *
     * @param lights The list of light sources
     * @return The scene object itself for method chaining
     */
    @SuppressWarnings("unused")
    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
