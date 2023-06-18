package renderer;

import geometries.Cylinder;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Tube;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class glossyMatTest {
    private Scene scene = new Scene("Test scene");

    @Test
    public void glossyMuteTest() {
        Camera camera = new Camera(new Point(0, 0, 0), new Vector(0, 0, -1), new Vector(0, 1, 0)).setVPSize(200, 150).setVPDistance(500);
        //scene.setIsAABB(true);
        scene.geometries.add(
                //new Plane(new Point(0, -25, 0), new Point(10, -25, 7), new Point(-17, -25, 15)).setMaterial(new Material().setKs(0.2).setKd(0.2).setShininess(60)).setEmission(new Color(yellow)),
                new Polygon(new Point(-75, -25, -510), new Point(-75, 60, -510), new Point(-25, 60, -510), new Point(-25, -25, -510)).setEmission(new Color(darkGray)).setMaterial(new Material().setKt(1).setKs(0.2).setKd(0.2).setShininess(60).setKB(0.7).setDensity(9)),
                new Polygon(new Point(0, -25, -510), new Point(0, 60, -510), new Point(50, 60, -510), new Point(50, -25, -510)).setEmission(new Color(darkGray)).setMaterial(new Material().setKr(1).setKs(0.2).setKd(0.2).setShininess(60).setKB(0.4).setDensity(9)),
                new Sphere(new Point(-52, 0, -540), 10).setEmission(new Color(red)).setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(60)),
                new Sphere(new Point(25, 30, -440), 10).setEmission(new Color(blue)).setMaterial(new Material().setKd(0.7).setKs(0.6).setShininess(60).setKr(0.5)),
                new Tube(new Ray(new Point(0, 30, -700), new Vector(-1, 0, 0)), 20).setEmission(new Color(103, 69, 110)).setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(60)),
                new Cylinder(40, new Ray(new Point(37, -25, -430), new Vector(0, 1, 0)), 5).setEmission(new Color(103, 69, 110)).setMaterial(new Material().setKd(0.8).setKs(0.2).setShininess(60))
        );
        //scene.setBackground(new Color(yellow));
        scene.lights.add( //
                new DirectionalLight(new Color(cyan), new Vector(-1, 0, 0)));
        scene.setAmbientLight((new AmbientLight(new Color(255, 255, 255), 0.1)));
        camera.setImageWriter(new ImageWriter("glossyMat", 1000, 1000)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }
}
