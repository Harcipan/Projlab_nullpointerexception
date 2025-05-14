package util;

public class Vec2 {
    private final float x;
    private final float y;

    public Vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }

    public Vec2 subtract(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }

    public Vec2 scale(float scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }

    public float dotProduct(Vec2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vec2 normalize() {
        float length = (float) Math.sqrt(this.x * this.x + this.y * this.y);
        if (length == 0) {
            throw new ArithmeticException("Cannot normalize a zero-length vector");
        }
        return new Vec2(this.x / length, this.y / length);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vec2 vec2 = (Vec2) obj;
        return Float.compare(vec2.x, x) == 0 && Float.compare(vec2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return 31 * Float.hashCode(x) + Float.hashCode(y);
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
