package raytracingoneweekend.vec3;
import raytracingoneweekend.interval.Interval;

public class Color extends Vec3 {
    public Color() {
        super();
    }

    public Color(double d) {
        super(d);
    }

    public Color(double x, double y, double z) {
        super(x, y, z);
    }

    public Color(Vec3 v) {
        super(v);
    }

    public Color(Color c) {
        super(c);
    }

    @Override
    public Color add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    @Override
    public Color add(Vec3 v) {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }

    public static Color add(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Color(x1 + x2, y1 + y2, z1 + z2);
    }

    public static Color add(Vec3 v1, Vec3 v2) {
        return new Color(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    @Override
    public Color newAdd(double x, double y, double z) {
        return new Color(this.x + x, this.y + y, this.z + z);
    }

    @Override
    public Color newAdd(Vec3 v) {
        return new Color(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    @Override
    public Color scale(double scalar) {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;
        return this;
    }

    @Override
    public Color scale(Vec3 v) {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }

    @Override
    public Color newScale(double scalar) {
        return new Color(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    @Override
    public Color newScale(Vec3 v) {
        return new Color(this.x * v.x, this.y * v.y, this.z * v.z);
    }

    @Override
    public Color set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    @Override
    public Color set(Vec3 v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }

    @Override
    public Color setZero() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
        return this;
    }

    @Override
    public Color sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    @Override
    public Color sub(Vec3 v) {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }

    @Override
    public Color newSub(double x, double y, double z) {
        return new Color(this.x - x, this.y - y, this.z - z);
    }

    @Override
    public Color newSub(Vec3 v) {
        return new Color(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public static Color random() {
        return new Color(Math.random(), Math.random(), Math.random());
    }

    public static Color random(double min, double max) {
        return new Color(Math.random() * (max - min) + min, Math.random() * (max - min) + min, Math.random() * (max - min) + min);
    }

    public Color copy(Color c) {
        this.x = c.x;
        this.y = c.y;
        this.z = c.z;
        return this;
    }

    public static String writeColor(Color pixelColor) {
        Interval intensity = new Interval(0.000, 0.999);

        return ((int) (256 * intensity.clamp(linearToGamma(pixelColor.x)))) + " " + 
               ((int) (256 * intensity.clamp(linearToGamma(pixelColor.y)))) + " " + 
               ((int) (256 * intensity.clamp(linearToGamma(pixelColor.z)))) + " ";
    }

    public static double linearToGamma(double linearColor) {
        if (linearColor > 0) return Math.sqrt(linearColor);
        return 0;
    }
}
