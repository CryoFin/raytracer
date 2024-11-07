package raytracingoneweekend;
import java.io.IOException;

import raytracingoneweekend.camera.Camera;
import raytracingoneweekend.hittables.HittableList;
import raytracingoneweekend.hittables.Sphere;
import raytracingoneweekend.materials.Dieletric;
import raytracingoneweekend.materials.Lambertian;
import raytracingoneweekend.materials.Material;
import raytracingoneweekend.materials.Metal;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.vec3.Vec3;

class RayTracer {

    // Materials
    Material groundMaterial;
    Material materialOne;
    Material materialTwo;
    Material materialThree;

    // World
    HittableList world;

    Camera cam;

    public static void main(String[] args) throws IOException {
        RayTracer rt = new RayTracer();

        // long start = System.currentTimeMillis();

        rt.configureCamera();
        rt.createMaterials();
        rt.addWorldObjects();

        rt.cam.render(rt.world);

        // long finish = System.currentTimeMillis();
        // long timeElapsed = finish - start;

        // System.out.println(timeElapsed);
    }

    void configureCamera() {
        cam = new Camera();
        cam.aspectRatio     = 16.0 / 9.0;
        cam.imageWidth      = 255;
        cam.samplesPerPixel = 1000;
        cam.maxDepth        = 50;

        cam.vFOV            = 20;
        cam.lookFrom        = new Vec3( 13, 2, 3);
        cam.lookAt          = new Vec3( 0, 0,  0);
        cam.vectorUp        = new Vec3( 0, 1,  0);

        cam.defocusAngle    = 0.6;
        cam.focusDistance   = 10.0;
    }

    void createMaterials() {
        groundMaterial = new Lambertian(new Color(0.5, 0.5, 0.5));
        materialOne    = new Dieletric(1.5);
        materialTwo    = new Lambertian(new Color(0.4, 0.2, 0.1));
        materialThree  = new Metal(new Color(0.7, 0.6, 0.5), 0.0);
    }

    void addWorldObjects() {
        world = new HittableList();

        // for (int i = -11; i < 11; i++) {
        //     for (int j = -11; j < 11; j++) {
        //         double chooseMaterial = Math.random();
        //         Vec3 center = new Vec3(i + 0.9 * Math.random(), 0.2, j + 0.9 * Math.random());

        //         if (Vec3.sub(center, new Vec3(4, 0.2, 0)).len() > 0.9) {
        //             Material sphereMaterial;

        //             if (chooseMaterial < 0.8) {
        //                 Color albedo = Color.random().scale(Color.random());
        //                 sphereMaterial = new Lambertian(albedo);
        //                 world.add(new Sphere(center, 0.2, sphereMaterial));
        //             } else if (chooseMaterial < 0.95) {
        //                 Color albedo = Color.random(0.5, 1);
        //                 double fuzz = Math.random() * 0.5;
        //                 sphereMaterial = new Metal(albedo, fuzz);
        //                 world.add(new Sphere(center, 0.2, sphereMaterial));
        //             } else {
        //                 sphereMaterial = new Dieletric(1.5);
        //                 world.add(new Sphere(center, 0.2, sphereMaterial));
        //             }
        //         }
        //     }
        // }

        world.add(new Sphere(new Vec3(0,  -1000, 0), 1000, groundMaterial));
        world.add(new Sphere(new Vec3(0, 1,    0), 1.0, materialOne));
        world.add(new Sphere(new Vec3( -4, 1,    0), 1.0, materialTwo));
        world.add(new Sphere(new Vec3(4, 1,    0), 1.0, materialThree));

        System.out.println("World Objects Created");
    }

}