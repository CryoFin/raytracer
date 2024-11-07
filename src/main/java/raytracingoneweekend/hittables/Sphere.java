package raytracingoneweekend.hittables;
import raytracingoneweekend.vec3.Vec3;
import raytracingoneweekend.materials.Material;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.interval.Interval;
import raytracingoneweekend.hitrecord.HitRecord;

public class Sphere extends Hittable {

    Vec3 center;
    double radius;
    Material material;


    public Sphere(Vec3 center, double radius, Material material) {
        this.center = center;
        this.radius = Math.max(0, radius);
        this.material = material;
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        Vec3 oc = center.newSub(r.getOrigin());
        double a = r.getDirection().lenSq();
        double h = Vec3.dot(r.getDirection(), oc);
        double c = oc.lenSq() - radius * radius;
        double discriminant = h * h - a * c;

        if (discriminant < 0) return false;
        
        double sqrtd = Math.sqrt(discriminant);

        double root = (h - sqrtd) / a;
        if (!rayT.surrounds(root)) {
            root = (h + sqrtd) / a;
            if (!rayT.surrounds(root)) return false;
        }      

        rec.t = root;
        rec.p = r.at(rec.t);
        Vec3 outwardNormal = Vec3.sub(rec.p, center).scale(1 / radius);
        rec.setFaceNormal(r, outwardNormal);
        rec.material = material;

        return true;
    }
}