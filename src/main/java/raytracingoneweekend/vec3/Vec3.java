package raytracingoneweekend.vec3;

public class Vec3 {

    public double x, y, z;

    public Vec3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vec3(double d) {
        x = d;
        y = d;
        z = d;
    }

    public Vec3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3(Vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
    }

    public Vec3 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Vec3 add(Vec3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public static Vec3 add(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Vec3(x1 + x2, y1 + y2, z1 + z2);
    }

    public static Vec3 add(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
    }

    public Vec3 newAdd(double x, double y, double z) {
        return new Vec3(this.x + x, this.y + y, this.z + z);
    }

    public Vec3 newAdd(Vec3 v) {
        return new Vec3(x + v.x, y + v.y, z + v.z);
    }

    public Vec3 addScaled(double x, double y, double z, double scalar) {
        this.x += x * scalar;
        this.y += y * scalar;
        this.z += z * scalar;
        return this;
    }

    public Vec3 addScaled(Vec3 v, double scalar) {
        x += v.x * scalar;
        y += v.y * scalar;
        z += v.z * scalar;
        return this;
    }

    public Vec3 newAddScaled(double x, double y, double z, double scalar) {
        return new Vec3(this.x + x * scalar, this.y + y * scalar, this.z + z * scalar);
    }

    public Vec3 newAddScaled(Vec3 v, double scalar) {
        return new Vec3(x + v.x * scalar, y + v.y * scalar, z + v.z * scalar);
    }

    public Vec3 copy() {
        return new Vec3(this);
    }

    public Vec3 copyOf(Vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public Vec3 cross(Vec3 v) {
        x = y * v.z - v.y * z;
        y = z * v.x - v.z * x;
        z = x * v.y - v.x - y;
        return this;
    }

    public Vec3 cross(Vec3 v1, Vec3 v2) {
        x = v1.y * v2.z - v2.y * v1.z;
        y = v1.z * v2.x - v2.z * v1.x;
        z = v1.x * v2.y - v2.x - v1.y;
        return this;
    }

    public Vec3 newCross(Vec3 v) {
        return new Vec3(y * v.z - v.y * z, z * v.x - v.z * x, x * v.y - v.x - y);
    }

    public static Vec3 newCross(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.y * v2.z - v2.y * v1.z, v1.z * v2.x - v2.z * v1.x, v1.x * v2.y - v2.x * v1.y);
    }

    public double dot(double x, double y, double z) {
        return this.x * x + this.y * y + this.z * z;
    }

    public double dot(Vec3 v) {
        return x * v.x + y * v.y + z * v.z;
    }

    public static double dot(double x1, double y1, double z1, double x2, double y2, double z2) {
        return x1 * x2 + y1 * y2 + z1 * z2;
    }

    public static double dot(Vec3 v1, Vec3 v2) {
        return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
    }

    public boolean equals(double x, double y, double z) {
        return this.x == x && this.y == y && this.z == z;
    }

    public boolean equals(Vec3 v) {
        return x == v.x && y == v.y && z == v.y;
    }

    public static Vec3 getNew() {
        return new Vec3();
    }

    public boolean isZero() {
        return (x == 0) && (y == 0) && (z == 0);
    }

    public boolean isNearZero() {
        return (Math.abs(x) < 0.00000001) && (Math.abs(y) < 0.00000001) && (Math.abs(z) < 0.00000001);
    }

    public double len() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public double lenSq() {
        double len = Math.sqrt(x * x + y * y + z * z);
        return len * len;
    }

    public double lenCb() {
        double len = Math.sqrt(x * x + y * y + z * z);
        return len * len * len;
    }

    public Vec3 neg() {
        x = -x;
        y = -y;
        z = -z;
        return this;
    }

    public Vec3 newNeg() {
        return new Vec3(-x, -y, -z);
    }

    public Vec3 scale(double scalar) {
        x *= scalar;
        y *= scalar;
        z *= scalar;
        return this;
    }

    public Vec3 scale(Vec3 v) {
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    public Vec3 newScale(double scalar) {
        return new Vec3(x * scalar, y * scalar, z * scalar);
    }

    public Vec3 newScale(Vec3 v) {
        return new Vec3(x * v.x, y * v.y, z * v.z);
    }

    public Vec3 set(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    public Vec3 set(Vec3 v) {
        x = v.x;
        y = v.y;
        z = v.z;
        return this;
    }

    public Vec3 setZero() {
        x = 0;
        y = 0;
        z = 0;
        return this;
    }

    public Vec3 sub(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;
        return this;
    }

    public Vec3 sub(Vec3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public static Vec3 sub(double x1, double y1, double z1, double x2, double y2, double z2) {
        return new Vec3(x1 - x2, y1 - y2, z1 - z2);
    }

    public static Vec3 sub(Vec3 v1, Vec3 v2) {
        return new Vec3(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }

    public Vec3 newSub(double x, double y, double z) {
        return new Vec3(this.x - x, this.y - y, this.z - z);
    }

    public Vec3 newSub(Vec3 v) {
        return new Vec3(x - v.x, y - v.y, z - v.z);
    }

    public Vec3 unit() {
        double len = Math.sqrt(x * x + y * y + z * z);
        x /= len;
        y /= len;
        z /= len;
        return this;
    }

    public Vec3 newUnit() {
        double len = Math.sqrt(x * x + y * y + z * z);
        return new Vec3(x / len, y / len, z / len);
    }

    public static Vec3 reflect(Vec3 vector, Vec3 normal) {
        return vector.newSub(normal.newScale(Vec3.dot(vector, normal) * 2));
    }

    public static Vec3 refract(Vec3 uv, Vec3 normal, double etaiOverEtat) {
        double cosTheta = Math.min(dot(uv.newNeg(), normal), 1.0);
        Vec3 rayOutPerpendicular = Vec3.add(uv, normal.newScale(cosTheta)).scale(etaiOverEtat);
        Vec3 rayOutParallel = normal.newScale(-Math.sqrt(Math.abs(1.0 - rayOutPerpendicular.lenSq())));
        return Vec3.add(rayOutParallel, rayOutPerpendicular);
    }

    public static Vec3 random() {
        return new Vec3(Math.random(), Math.random(), Math.random());
    }

    public static Vec3 random(double min, double max) {
        return new Vec3(Math.random() * (max - min) + min, Math.random() * (max - min) + min, Math.random() * (max - min) + min);
    }

    public static Vec3 randomInUnitSphere() {
        while (true) { 
            Vec3 testVector = random(-1, 1);
            if (testVector.lenSq() < 1) return testVector;
        }
    }

    public static Vec3 randomUnitvector() {
        while (true) { 
            Vec3 testVector = random(-1, 1);
            if (testVector.lenSq() < 1) return testVector.unit();
        }
    }

    public static Vec3 randomOnHemisphere(Vec3 normal) {
        Vec3 vector = new Vec3();
        boolean foundVector = false;
        do { 
            vector = random(-1, 1);
            if (vector.lenSq() < 1) {
                vector.unit();
                foundVector = true;
            }
        } while (!foundVector);
        if (dot(vector, normal) > 0.0) return vector;
        else return vector.neg();
    }

    public static Vec3 randomInUnitDisk() {
        while (true) {
            Vec3 testVector = new Vec3(Math.random() * 2 - 1, Math.random() * 2 - 1, 0);
            if (testVector.lenSq() < 1) return testVector;
        }
    }
}