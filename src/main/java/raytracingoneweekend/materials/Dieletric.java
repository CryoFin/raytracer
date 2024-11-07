package raytracingoneweekend.materials;
import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.vec3.Vec3;

public class Dieletric extends Material {

    double refractionIndex;

    public Dieletric(double refractionIndex) {
        this.refractionIndex = refractionIndex;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attentuation, Ray scattered) {
        attentuation.copy(new Color(1.0));
        double ri = rec.isFrontFace() ? (1.0 / refractionIndex) : refractionIndex;

        Vec3 unitDirection = r.getDirection().newUnit();
        double cosTheta = Math.min(Vec3.dot(unitDirection.newNeg(), rec.normal), 1.0);
        double sinTheta = Math.sqrt(1.0 - cosTheta * cosTheta);

        boolean cannotRefract = ri * sinTheta > 1.0;
        Vec3 direction;

        if (cannotRefract || reflectance(cosTheta, ri) > Math.random()) direction = Vec3.reflect(unitDirection, rec.normal);
        else direction = Vec3.refract(unitDirection, rec.normal, ri);

        scattered.copy(new Ray(rec.p, direction));
        return true;
    }

    static double reflectance(double cosine, double refractionIndex) {
        double r0 = (1 - refractionIndex) / (1 + refractionIndex);
        r0 = r0 * r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }
}