package raytracingoneweekend.materials;
import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.vec3.Vec3;

public class Lambertian extends Material {
    
    Color albedo;
    
    public Lambertian(Color albedo) {
        this.albedo = albedo;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attentuation, Ray scattered) {
        Vec3 scatterDirection = Vec3.randomUnitvector().add(rec.normal);

        if (scatterDirection.isNearZero()) scatterDirection = rec.normal;

        scattered.copy(new Ray(rec.p, scatterDirection));
        attentuation.copy(albedo);
        return true;
    }
}