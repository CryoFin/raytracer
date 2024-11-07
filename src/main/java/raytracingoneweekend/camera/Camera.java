package raytracingoneweekend.camera;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.hittables.Hittable;
import raytracingoneweekend.interval.Interval;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.vec3.Vec3;

public class Camera {

    // Information and StringBuilder to write image to .ppm file
    static final String FILENAME = "image.ppm";
    static final int BYTES = 10000000;
    static final StringBuilder MATRIX_STRINGBUILDER = new StringBuilder(BYTES);
    static String[][] grid;
    static String[] lines;
    
    // Image size and quality settings
    public double aspectRatio  = 1.0;
    public int imageWidth      = 100;
    public int samplesPerPixel = 10;
    public int maxDepth        = 10;

    // Camera view and blur configuration
    public double vFOV          = 90;
    public Vec3 lookFrom        = new Vec3(0, 0, 0);
    public Vec3 lookAt          = new Vec3(0, 0, -1);
    public Vec3 vectorUp        = new Vec3(0, 1, 0);
    public double defocusAngle  = 0;
    public double focusDistance = 10;

    // Private variables used to configure camera
    int imageHeight;
    Vec3 center = new Vec3();
    Vec3 pixelDeltaU;
    Vec3 pixelDeltaV;
    Vec3 upperLeftPixelLocation;
    double pixelSamplesScale;
    Vec3 defocusDiskU;
    Vec3 defocusDiskV;

    public void render(Hittable world) throws IOException {
        initialize();

        // long STstart = System.currentTimeMillis();
        createImage(world);
        // long STfinish = System.currentTimeMillis();
        // long STtimeElapsed = STfinish - STstart;
        // System.out.println("Single Threading: " + STtimeElapsed);

        // long MT1start = System.currentTimeMillis();
        // createImageMultithreaded(world);
        // long MT1finish = System.currentTimeMillis();
        // long MT1timeElapsed = MT1finish - MT1start;
        // System.out.println("MultiThreading One: " + MT1timeElapsed);

        // long MT2start = System.currentTimeMillis();
        // createImageMultiThreadedTwo(world);
        // long MT2finish = System.currentTimeMillis();
        // long MT2timeElapsed = MT2finish - MT2start;
        // System.out.println("MultiThreading Two: " + MT2timeElapsed);

        writeImage(FILENAME);
    }

    void initialize() {
        imageHeight = Math.max(1, (int) ((double) imageWidth / aspectRatio));

        pixelSamplesScale = (double) 1 / samplesPerPixel;

        center.copyOf(lookFrom);

        double theta = Math.toRadians(vFOV);
        double h = Math.tan((double) theta / 2);
        double viewportHeight = 2 * h * focusDistance;
        double viewportWidth = viewportHeight * ((double) imageWidth / imageHeight);

        Vec3 w = Vec3.sub(lookFrom, lookAt).unit();
        Vec3 u = Vec3.newCross(vectorUp, w).unit();
        Vec3 v = Vec3.newCross(w, u);

        Vec3 viewportU = u.newScale(viewportWidth);
        Vec3 viewportV = v.newNeg().scale(viewportHeight);

        pixelDeltaU = viewportU.newScale((double) 1 / imageWidth);
        pixelDeltaV = viewportV.newScale((double) 1 / imageHeight);

        Vec3 viewportUpperLeft = center.newSub(w.newScale(focusDistance)).sub(viewportU.newScale(0.5)).sub(viewportV.newScale(0.5));
        upperLeftPixelLocation = viewportUpperLeft.newAdd(Vec3.add(pixelDeltaU, pixelDeltaV).scale(0.5));

        double defocusRadius = focusDistance * Math.tan(Math.toRadians((double) defocusAngle / 2));
        defocusDiskU = u.newScale(defocusRadius);
        defocusDiskV = v.newScale(defocusRadius);

        grid = new String[imageHeight][imageWidth];
        lines = new String[imageHeight];
    }

    // Crates "MATRIX_STRINGBUILDER" string to be written to .ppm file
    void createImage(Hittable world) {
        MATRIX_STRINGBUILDER.append("P3\n").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");

        for (int currentHeight = 0; currentHeight < imageHeight; currentHeight++) {

            System.out.println("\rScanlines remaining: " + (imageHeight - currentHeight) + " ");
            
            for (int currentWidth = 0; currentWidth < imageWidth; currentWidth++) {

                Color pixelColor = new Color();

                for (int sample = 0; sample < samplesPerPixel; sample++) {
                    Ray r = getRay(currentWidth, currentHeight);
                    pixelColor.add(rayColor(r, maxDepth, world));
                }

                MATRIX_STRINGBUILDER.append(Color.writeColor(pixelColor.scale(pixelSamplesScale)));
            }

            MATRIX_STRINGBUILDER.append("\n");
        }

        System.out.println("\rDone.\n");
    }

    void createImageMultithreaded(Hittable world) {

        final int NUM_THREADS = 4;
        ExecutorService exec = Executors.newFixedThreadPool(NUM_THREADS);

        MATRIX_STRINGBUILDER.append("P3\n").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");

        for (int currentHeight = 0; currentHeight < imageHeight; currentHeight++) {

            System.out.println("\rScanlines remaining: " + (imageHeight - currentHeight) + " ");
            
            for (int currentWidth = 0; currentWidth < imageWidth; currentWidth++) {
                final int cw = currentWidth;
                final int ch = currentHeight;
                exec.execute(() -> {
                    calculatePixel(world, cw, ch);
                });
            }
        }

        // System.out.println("Waiting for termination of threads");

        exec.shutdown();
        try {
            if (!exec.awaitTermination(60 * 60, TimeUnit.SECONDS)) {
                exec.shutdownNow();
            }
        } catch (InterruptedException ex) {
            exec.shutdownNow();
            Thread.currentThread().interrupt();
        }

        exec.shutdownNow();

        // System.out.println("Creating String");

        for (String[] arr : grid) {
            for (String s : arr) {
                MATRIX_STRINGBUILDER.append(s);
            }
            MATRIX_STRINGBUILDER.append("\n");
        }

        System.out.println("\rDone.\n");
    }

    void createImageMultiThreadedTwo(Hittable world) {
        final int NUM_THREADS = 4;
        ExecutorService exec = Executors.newFixedThreadPool(NUM_THREADS);

        MATRIX_STRINGBUILDER.append("P3\n").append(imageWidth).append(" ").append(imageHeight).append("\n255\n");

        for (int currentHeight = 0; currentHeight < imageHeight; currentHeight++) {

            System.out.println("\rScanlines remaining: " + (imageHeight - currentHeight) + " ");
            
            final int ch = currentHeight;
            exec.execute(() -> {
                calculateRow(world, ch);
            });
        }

        // System.out.println("Waiting for termination of threads");

        exec.shutdown();
        try {
            if (!exec.awaitTermination(60 * 60, TimeUnit.SECONDS)) {
                exec.shutdownNow();
            }
        } catch (InterruptedException ex) {
            exec.shutdownNow();
            Thread.currentThread().interrupt();
        }

        exec.shutdownNow();

        // System.out.println("Creating String");

        for (String s : lines) {
            MATRIX_STRINGBUILDER.append(s).append("\n");
        }

        System.out.println("\rDone.\n");
    }

    void calculatePixel(Hittable world, int currentWidth, int currentHeight) {
        Color pixelColor = new Color();

        for (int sample = 0; sample < samplesPerPixel; sample++) {
            Ray r = getRay(currentWidth, currentHeight);
            pixelColor.add(rayColor(r, maxDepth, world));
        }

        grid[currentHeight][currentWidth] = Color.writeColor(pixelColor.scale(pixelSamplesScale));
    }

    void calculateRow(Hittable world, int currentHeight) {

        StringBuilder multiThreadStringBuilder = new StringBuilder(10000000);

        for (int currentWidth = 0; currentWidth < imageWidth; currentWidth++) {

            Color pixelColor = new Color();

            for (int sample = 0; sample < samplesPerPixel; sample++) {
                Ray r = getRay(currentWidth, currentHeight);
                pixelColor.add(rayColor(r, maxDepth, world));
            }

            multiThreadStringBuilder.append(Color.writeColor(pixelColor.scale(pixelSamplesScale)));
        }

        lines[currentHeight] = multiThreadStringBuilder.toString();
    }

    Ray getRay(int x, int y) {
        Vec3 offset = sampleSquare();
        Vec3 pixelSample = upperLeftPixelLocation.newAdd(Vec3.add(pixelDeltaU.newScale(x + offset.x), pixelDeltaV.newScale(y + offset.y)));

        Vec3 rayOrigin = defocusAngle <= 0 ? center : defocusDiskSample();
        Vec3 rayDirection = pixelSample.newSub(rayOrigin);

        return new Ray(rayOrigin, rayDirection);
    }

    Vec3 sampleSquare() {
        return new Vec3(Math.random() - 0.5, Math.random() - 0.5, 0);
    }

    Vec3 defocusDiskSample() {
        Vec3 p = Vec3.randomInUnitDisk();
        return center.newAdd(Vec3.add(defocusDiskU.newScale(p.x), defocusDiskV.newScale(p.y)));
    }

    Color rayColor(Ray r, int depth, Hittable world) {

        if (depth <= 0) return new Color(0);

        HitRecord rec = new HitRecord();

        if (world.hit(r, new Interval(0.00000001, Double.MAX_VALUE), rec)) {
            Ray scattered = new Ray();
            Color attentuation = new Color();
            if (rec.material.scatter(r, rec, attentuation, scattered)) return (rayColor(scattered, depth - 1, world)).scale(attentuation);
            return new Color(0);
        }

        Vec3 unitDirection = r.getDirection().newUnit();
        double a = 0.5 * (unitDirection.y + 1.0);
        return Color.add(new Color(1.0, 1.0, 1.0).scale(1.0 - a), new Color(0.5, 0.7, 1.0).scale(a));
    }

     // Writes "MATRIX_STRINGBUILDER" string to .ppm file
    void writeImage(String _fileName) {
        try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(_fileName)), BYTES)) {
            out.write(MATRIX_STRINGBUILDER.toString());
            out.close();
        } catch (IOException ioexception) {
        }
    }
}