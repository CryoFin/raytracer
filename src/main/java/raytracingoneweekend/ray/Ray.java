package raytracingoneweekend.ray;
import raytracingoneweekend.vec3.Vec3;

public class Ray {
    
    Vec3 origin, direction;

    public Ray() {}

    public Ray(Vec3 origin, Vec3 direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3 getOrigin() {
        return origin;
    }

    public Vec3 getDirection() {
        return direction;
    }

    public Vec3 at(double t) {
        return Vec3.add(origin, direction.newScale(t));
    }

    public Ray copy(Ray r) {
        this.origin = r.origin;
        this.direction = r.direction;
        return this;
    }
}