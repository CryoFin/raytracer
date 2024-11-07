package raytracingoneweekend.hittables;
import raytracingoneweekend.hitrecord.HitRecord;
import raytracingoneweekend.interval.Interval;
import java.util.ArrayList;
import raytracingoneweekend.ray.Ray;

public class HittableList extends Hittable {

    public ArrayList<Hittable> objects = new ArrayList<>();

    public HittableList() {};

    public HittableList(Hittable hittable) {
        objects.add(hittable);
    }

    public void clear() {
        objects.clear();
    }

    public void add(Hittable hittable) {
        objects.add(hittable);
    }

    @Override
    public boolean hit(Ray r, Interval rayT, HitRecord rec) {
        HitRecord tempRec = new HitRecord();
        boolean hitDetected = false;
        double closestHit = rayT.max;

        for (Hittable hittable : objects) {
            if (hittable.hit(r, new Interval(rayT.min, closestHit), tempRec)) {
                hitDetected = true;
                closestHit = tempRec.t;
                rec.copyOf(tempRec);
            }
        }

        return hitDetected;
    }
}
