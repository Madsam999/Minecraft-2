package GameEngine.maths;

import java.util.Objects;

public class Vector3f {

    private float x, y, z;

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Vector3f add(Vector3f v, Vector3f w) {
        Vector3f result = new Vector3f(v.getX() + w.getX(), v.getY() + w.getY(), v.getZ() + w.getZ());
        return result;
    }

    public static Vector3f substract(Vector3f v, Vector3f w) {
        Vector3f result = new Vector3f(v.getX() - w.getX(), v.getY() - w.getY(), v.getZ() - w.getZ());
        return result;
    }

    public static Vector3f multiply(Vector3f v, Vector3f w) {
        Vector3f result = new Vector3f(v.getX() * w.getX(), v.getY() * w.getY(), v.getZ() * w.getZ());
        return result;
    }

    public static Vector3f divide(Vector3f v, Vector3f w) {
        Vector3f result = new Vector3f(v.getX() / w.getX(), v.getY() / w.getY(), v.getZ() / w.getZ());
        return result;
    }

    public static float length(Vector3f v) {
        float result = (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY() + v.getZ()*v.getZ());
        return  result;
    }

    public static Vector3f normalize(Vector3f v) {
        float length = Vector3f.length(v);
        return new Vector3f(v.getX()/length, v.getY()/length, v.getZ()/length);
    }

    public static float dot(Vector3f v, Vector3f w) {
        return v.getX() * w.getX() + v.getY() * w.getY() + v.getZ() * w.getZ();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector3f vector3f = (Vector3f) o;
        if(Float.floatToIntBits(x) != Float.floatToIntBits(vector3f.x)) {
            return false;
        }
        if(Float.floatToIntBits(y) != Float.floatToIntBits(vector3f.y)) {
            return false;
        }
        if(Float.floatToIntBits(z) != Float.floatToIntBits(vector3f.z)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(x);
        result = prime * result + Float.floatToIntBits(y);
        result = prime * result + Float.floatToIntBits(z);
        return result;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public void set(float x, float y, float z) {
    	this.x = x;
    	this.y = y;
    	this.z = z;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setZ(float z) {
        this.z = z;
    }
}
