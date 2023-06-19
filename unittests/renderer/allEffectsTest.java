package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

public class allEffectsTest {
    private Scene scene = new Scene("Test scene");

    @Test
    public void testAllAffects() {
        Camera camera = new Camera(new Point(-300, -600, 350), new Vector(0.3, 0.6, -0.45), new Vector(-2, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000).rotateLeft(90);


        //scene.setIsAABB(true);
        scene.geometries.add( //
                new Triangle(new Point(0, 0, -115), new Point(0, 75, -75),
                        new Point(-75, 0, -75)).setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKs(0.5).setShininess(60).setKr(0.2)),
                new Plane(new Point(0, 0, -115), new Point(10, 0, -115), new Point(0, -10, -115)).setMaterial(new Material().setKs(0.8).setKd(0.1).setShininess(60).setKr(0.5)).setEmission(new Color(20, 20, 20)),
                new Triangle(new Point(0, 0, -115), new Point(0, 75, -75),
                        new Point(75, 0, -75)).setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKs(0.5).setShininess(60).setKr(0.2)),
                new Triangle(new Point(0, 0, -115), new Point(-30, -120, -75),
                        new Point(75, 0, -75)).setEmission(new Color(20, 20, 20)).setMaterial(new Material().setKs(0.5).setShininess(60).setKr(0.2)),
                new Sphere(new Point(0, 0, -88), 20).setEmission(new Color(GRAY)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKr(0.95)),
                new Polygon(new Point(-59, -62, -94), new Point(-59, -38, -94), new Point(-41, -38, -94), new Point(-41, -62, -94)).setEmission(new Color(magenta)),
                new Sphere(new Point(-50, -50, -94), 15).setEmission(new Color(BLUE)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.75)),
                new Cylinder(15, new Ray(new Point(-50, -50, -90), new Vector(0, 0, 1)), 6).setEmission(new Color(darkGray)).setMaterial(new Material().setKs(0.5).setKd(0.5).setShininess(60).setKr(0.2)),
                new Tube(new Ray(new Point(-50, -100, -115), new Vector(1, 1, 0)), 6).setEmission(new Color(darkGray)).setMaterial(new Material().setKs(0.5).setKd(0.5).setShininess(60).setKr(0.2)),
                new Triangle(new Point(-59, -62, -94), new Point(-59, -38, -94), new Point(-55, -55, -107)).setEmission(new Color(magenta)).setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(30).setKt(0.9)),
                new Triangle(new Point(-59, -38, -94), new Point(-41, -38, -94), new Point(-55, -55, -107)).setEmission(new Color(magenta)).setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(30).setKt(0.9)),
                new Triangle(new Point(-41, -38, -94), new Point(-41, -62, -94), new Point(-55, -55, -107)).setEmission(new Color(magenta)).setMaterial(new Material().setKd(0.7).setKs(0.2).setShininess(30).setKt(0.9)),
                new Triangle(new Point(-41, -62, -94), new Point(-59, -62, -94), new Point(-55, -55, -107)).setEmission(new Color(magenta)).setMaterial(new Material().setKd(0.7).setKs(0.5).setShininess(30).setKt(0.9))
        );
        scene.lights.add( //
                new SpotLight(new Color(cyan), new Point(-100, -100, 900), new Vector(1, 1, -6)) //
                        .setKl(0.0000004).setKq(0.000000006));

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        camera.setImageWriter(new ImageWriter("allAffects", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        Camera c = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -0.5), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000).rotateLeft(90);
        c.setImageWriter(new ImageWriter("allAffectsAngle1", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        Camera c1 = new Camera(new Point(-600, 600, 300), new Vector(0.6, -0.6, -0.415), new Vector(1, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000).rotateLeft(90);
        c1.setImageWriter(new ImageWriter("allAffectsAngle2", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        c1 = c1.rotateLeft(45);
        c1.setImageWriter(new ImageWriter("allAffectsAngle3", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        c1 = new Camera(new Point(400, 400, 200), new Vector(-0.4, -0.4, -0.265), new Vector(-1, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000).rotateRight(90);
        c1.setImageWriter(new ImageWriter("allAffectsAngle4", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
        c1 = new Camera(new Point(-450, 900, 300), new Vector(0.45, -0.9, -0.415), new Vector(2, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000).rotateLeft(90);
        c1.setImageWriter(new ImageWriter("allAffectsAngle5", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage(); //
        camera.writeToImage();
    }

}
