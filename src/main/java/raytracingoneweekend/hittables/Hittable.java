package raytracingoneweekend.hittables;
import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.interval.Interval;
import raytracingoneweekend.ray.Ray;

public abstract class Hittable {
    public Hittable() {}

    abstract public boolean hit(Ray r, Interval rayT, HitRecord rec);
}