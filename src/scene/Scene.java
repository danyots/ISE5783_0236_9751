package scene;

import geometries.*;
import lighting.AmbientLight;
import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "scene")
public class Scene {
    public String name;
    public Color background;
    public AmbientLight ambientLight = AmbientLight.NONE;
    public Geometries geometries = new Geometries();

    public Scene(String name) {
        this.name = name;
    }

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }

    public Scene deserialize(String fileName) throws ParserConfigurationException, IOException, SAXException {
        String path = System.getProperty("user.dir") + "/" + fileName + ".xml";
        BufferedReader reader1 = new BufferedReader(new FileReader(path));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = reader1.readLine()) != null) {
            stringBuilder.append(line);
            stringBuilder.append(System.lineSeparator());
        }
        reader1.close();
        String xml = stringBuilder.toString();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new StringReader(xml)));

        Element sceneElement = document.getDocumentElement();

        String backgroundColorString = sceneElement.getAttribute("background-color");
        String[] backgroundColorComponents = backgroundColorString.split(" ");
        int red = Integer.parseInt(backgroundColorComponents[0]);
        int green = Integer.parseInt(backgroundColorComponents[1]);
        int blue = Integer.parseInt(backgroundColorComponents[2]);
        Color fileBackgroundColor = new Color(red, green, blue);

        AmbientLight fileAmbientLight = AmbientLight.NONE;
        NodeList ambientLightList = sceneElement.getElementsByTagName("ambient-light");
        if (ambientLightList.getLength() > 0) {
            Element ambientLightElement = (Element) ambientLightList.item(0);
            String ambientLightColorString = ambientLightElement.getAttribute("color");
            String[] ambientLightColorComponents = ambientLightColorString.split(" ");
            int ambientLightRed = Integer.parseInt(ambientLightColorComponents[0]);
            int ambientLightGreen = Integer.parseInt(ambientLightColorComponents[1]);
            int ambientLightBlue = Integer.parseInt(ambientLightColorComponents[2]);
            fileAmbientLight = new AmbientLight(new Color(ambientLightRed, ambientLightGreen, ambientLightBlue), 1);
        }

        Geometries fileGeometries = new Geometries();
        NodeList geometryList = sceneElement.getElementsByTagName("geometries");
        if (geometryList.getLength() > 0) {
            Element geometriesElement = (Element) geometryList.item(0);
            NodeList shapeList = geometriesElement.getChildNodes();
            for (int i = 0; i < shapeList.getLength(); i++) {
                Node shapeNode = shapeList.item(i);
                if (shapeNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element shapeElement = (Element) shapeNode;
                    switch (shapeElement.getNodeName()) {
                        case "sphere":
                            String sphereCenterString = shapeElement.getAttribute("center");
                            Point center = readPoint(sphereCenterString.split(" "));
                            double sphereRadius = Double.parseDouble(shapeElement.getAttribute("radius"));
                            fileGeometries.add(new Sphere(center, sphereRadius));
                            break;
                        case "triangle":
                            String triangleP0String = shapeElement.getAttribute("p0");
                            Point p0 = readPoint(triangleP0String.split(" "));
                            String triangleP1String = shapeElement.getAttribute("p1");
                            Point p1 = readPoint(triangleP1String.split(" "));
                            String triangleP2String = shapeElement.getAttribute("p2");
                            Point p2 = readPoint(triangleP2String.split(" "));
                            fileGeometries.add(new Triangle(p0, p1, p2));
                            break;
                        case "polygon":
                            List<Point> points = new ArrayList<>();
                            NamedNodeMap attributes = shapeElement.getAttributes();
                            for (int counter = 0; counter < attributes.getLength(); counter++) {
                                Node attribute = attributes.item(counter);
                                String attributeName = attribute.getNodeName();
                                if (attributeName.startsWith("p")) {
                                    Point point = readPoint(attribute.getNodeValue().split(" "));
                                    points.add(point);
                                }
                            }
                            fileGeometries.add(new Polygon(points.toArray(new Point[0])));
                            break;
                        case "plane":
                            String planeP0String = shapeElement.getAttribute("p0");
                            p0 = readPoint(planeP0String.split(" "));
                            String planeP1String = shapeElement.getAttribute("p1");
                            p1 = readPoint(planeP1String.split(" "));
                            String planeP2String = shapeElement.getAttribute("p2");
                            p2 = readPoint(planeP2String.split(" "));
                            fileGeometries.add(new Plane(p0, p1, p2));
                            break;
                        case "tube":
                            // Get the radius attribute
                            double radius = Double.parseDouble(shapeElement.getAttribute("radius"));
                            Element rayElement = (Element) shapeElement.getElementsByTagName("ray").item(0);
                            String[] pointCoords = rayElement.getAttribute("point").split(" ");
                            Point point = new Point(Double.parseDouble(pointCoords[0]), Double.parseDouble(pointCoords[1]), Double.parseDouble(pointCoords[2]));
                            String[] vectorCoords = rayElement.getAttribute("vector").split(" ");
                            Vector vector = new Vector(Double.parseDouble(vectorCoords[0]), Double.parseDouble(vectorCoords[1]), Double.parseDouble(vectorCoords[2]));
                            fileGeometries.add(new Tube(new Ray(point, vector),radius));
                            break;
                        case "cylinder":
                            radius = Double.parseDouble(shapeElement.getAttribute("radius"));
                            double height = Double.parseDouble(shapeElement.getAttribute("height"));
                            rayElement = (Element) shapeElement.getElementsByTagName("ray").item(0);
                            pointCoords = rayElement.getAttribute("point").split(" ");
                            point = new Point(Double.parseDouble(pointCoords[0]), Double.parseDouble(pointCoords[1]), Double.parseDouble(pointCoords[2]));
                            vectorCoords = rayElement.getAttribute("vector").split(" ");
                            vector = new Vector(Double.parseDouble(vectorCoords[0]), Double.parseDouble(vectorCoords[1]), Double.parseDouble(vectorCoords[2]));
                            fileGeometries.add(new Cylinder(height,new Ray(point, vector),radius));
                            break;


                    }
                }
            }
        }
        return new Scene("XML Test scene").setBackground(fileBackgroundColor).setAmbientLight(fileAmbientLight).setGeometries(fileGeometries);
    }

    private Point readPoint(String[] pointComponent) {
        return new Point(Double.parseDouble(pointComponent[0]),
                Double.parseDouble(pointComponent[1]),
                Double.parseDouble(pointComponent[2]));
    }
}
