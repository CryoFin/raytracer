package raytracingoneweekend.interval;

public class Interval {
    
    public double min, max;

    public static final Interval EMPTY = new Interval(Double.MAX_VALUE, Double.MIN_VALUE);
    public static final Interval UNIVERSE = new Interval(Double.MIN_VALUE, Double.MAX_VALUE);

    public Interval() {
        min = Double.MAX_VALUE;
        max = Double.MIN_VALUE;
    }

    public Interval(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double size() {
        return max - min;
    }

    public boolean contains(double x) {
        return min <= x && x <= max;
    }

    public boolean surrounds(double x) {
        return min < x && x < max;
    }

    public double clamp(double x) {
        if (x < min) return min;
        if (x > max) return max;
        return x;
    }
}