package raytracingoneweekend.hitrecord;
import raytracingoneweekend.materials.Material;
import raytracingoneweekend.ray.Ray;
import raytracingoneweekend.vec3.Vec3;

public class HitRecord {
    
    public Vec3 p, normal;
    public Material material;
    public double t;

    boolean frontFace;

    public boolean isFrontFace() {
        return frontFace;
    }

    public void setFaceNormal(Ray r, Vec3 outwardNormal) {
        frontFace = Vec3.dot(r.getDirection(), outwardNormal) < 0;
        normal = frontFace ? outwardNormal : outwardNormal.newNeg();
    }

    public HitRecord copy() {
        HitRecord copy = new HitRecord();
        copy.p = this.p;
        copy.normal = this.normal;
        copy.t = this.t;
        copy.frontFace = this.frontFace;
        copy.material = this.material;
        return copy;
    }

    public HitRecord copyOf(HitRecord rec) {
        this.p = rec.p;
        this.normal = rec.normal;
        this.t = rec.t;
        this.frontFace = rec.frontFace;
        this.material = rec.material;
        return this;
    }
}