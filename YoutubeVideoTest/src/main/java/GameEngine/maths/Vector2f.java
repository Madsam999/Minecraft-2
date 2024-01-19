package GameEngine.maths;

public class Vector2f {
    private float x,y;

    public Vector2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2f add(Vector2f v, Vector2f w) {
        Vector2f result = new Vector2f(v.getX() + w.getX(), v.getY() + w.getY());
        return result;
    }

    public static Vector2f substract(Vector2f v, Vector2f w) {
        Vector2f result = new Vector2f(v.getX() - w.getX(), v.getY() - w.getY());
        return result;
    }

    public static Vector2f multiply(Vector2f v, Vector2f w) {
        Vector2f result = new Vector2f(v.getX() * w.getX(), v.getY() * w.getY());
        return result;
    }

    public static Vector2f divide(Vector2f v, Vector2f w) {
        Vector2f result = new Vector2f(v.getX() / w.getX(), v.getY() / w.getY());
        return result;
    }

    public static float length(Vector2f v) {
        float result = (float) Math.sqrt(v.getX() * v.getX() + v.getY() * v.getY());
        return  result;
    }

    public static Vector2f normalize(Vector2f v) {
        float length = Vector2f.length(v);
        return new Vector2f(v.getX()/length, v.getY()/length);
    }

    public static float dot(Vector2f v, Vector2f w) {
        return v.getX() * w.getX() + v.getY() * w.getY();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2f vector2f = (Vector2f) o;
        if(Float.floatToIntBits(x) != Float.floatToIntBits(vector2f.x)) {
            return false;
        }
        if(Float.floatToIntBits(y) != Float.floatToIntBits(vector2f.y)) {
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
        return result;
    }

    public float getY() {
        return y;
    }

    public float getX() {
        return x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }
}
