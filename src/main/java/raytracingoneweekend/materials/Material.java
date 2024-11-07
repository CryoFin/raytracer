package raytracingoneweekend.materials;
import raytracingoneweekend.vec3.Color;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.hitrecord.HitRecord;

public abstract class Material {
    public Material() {};

    public boolean scatter(Ray r, HitRecord rec, Color attentuation, Ray scattered) {
        return false;
    }
}