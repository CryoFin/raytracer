package raytracingoneweekend.materials;
import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.vec3.Vec3;

public class Metal extends Material {

    Color albedo;
    double fuzz;

    public Metal(Color albedo, double fuzz) {
        this.albedo = albedo;
        this.fuzz = fuzz;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attentuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(r.getDirection(), rec.normal);
        reflected.unit().add(Vec3.randomUnitvector().scale(fuzz));
        scattered.copy(new Ray(rec.p, reflected));
        attentuation.copy(albedo);
        return (Vec3.dot(scattered.getDirection(), rec.normal) > 0);
    }
}