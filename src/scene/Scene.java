package scene;

import geometries.*;
import lighting.AmbientLight;
import lighting.LightSource;
import org.xml.sax.SAXException;
import primitives.Color;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * The Scene class represents a 3D scene with its properties and geometries.
 */
@XmlRootElement(name = "scene")
public class Scene {
    public final String name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();
    public List<LightSource> lights=new LinkedList<>();
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

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }
}
