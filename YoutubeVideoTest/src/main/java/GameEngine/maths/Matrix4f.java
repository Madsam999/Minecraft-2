package GameEngine.maths;

import java.util.Arrays;

public class Matrix4f {
    public static final int SIZE = 4;
    private float[] elements = new float[SIZE * SIZE];

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                result.set(i, j, 0);
            }
        }

        result.set(0, 0, 1);
        result.set(1, 1, 1);
        result.set(2, 2, 1);
        result.set(3, 3, 1);

        return result;
    }

    public static Matrix4f translate(Vector3f translate) {
        Matrix4f result = Matrix4f.identity();

        result.set(3, 0, translate.getX());
        result.set(3, 1, translate.getY());
        result.set(3, 2, translate.getZ());

        return result;
    }

    public static Matrix4f projection(float aspectRatio, float fov, float near, float far) {
        Matrix4f result = Matrix4f.identity();

        float tan = (float) Math.tan(Math.toRadians(fov/2));
        float range = far - near;

        result.set(0,0, 1.0f/(aspectRatio * tan));
        result.set(1,1,1.0f / tan);
        result.set(2,2,-((far + near)/range));
        result.set(3,2,-2*far*near/range);
        result.set(2,3,-1);
        result.set(3,3,0);

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return  false;
        Matrix4f matrix4f = (Matrix4f) o;
        if(!Arrays.equals(elements, matrix4f.elements)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime*result + Arrays.hashCode(elements);
        return result;
    }

    public static Matrix4f view(Vector3f position, Vector3f rotation) {
        Matrix4f result = Matrix4f.identity();

        Vector3f negative = new Vector3f(-position.getX(), -position.getY(), - position.getZ());
        Matrix4f translationMatrix = Matrix4f.translate(negative);
        Matrix4f rotationXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1.0f,0,0));
        Matrix4f rotationYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0,1.0f,0));
        Matrix4f rotationZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0,0,1.0f));

        Matrix4f rotationMatrix = mutliply(rotationZMatrix, mutliply(rotationYMatrix, rotationXMatrix));

        result = mutliply(translationMatrix, rotationMatrix);

        return result;
    }

    public static Matrix4f rotate(float angle, Vector3f axis) {
        Matrix4f result = Matrix4f.identity();

        float cos = (float) Math.cos(Math.toRadians(angle));
        float sin = (float) Math.sin(Math.toRadians(angle));

        float C = 1 - cos;

        result.set(0, 0, cos + axis.getX() * axis.getX() * C);
        result.set(0, 1, axis.getY() * axis.getX() * C - axis.getZ()*sin);
        result.set(0, 2, axis.getX() * axis.getZ() * C + axis.getY() * sin);
        result.set(1, 0, axis.getY() * axis.getX() * C + axis.getZ() * sin);
        result.set(1, 1, cos + axis.getY() * axis.getY() * C);
        result.set(1, 2, axis.getY() * axis.getZ() * C - axis.getX() * sin);
        result.set(2, 0, axis.getX() * axis.getZ() * C - axis.getY() * sin);
        result.set(2, 1, axis.getZ() * axis.getY() * C + axis.getX() * sin);
        result.set(2, 2, cos + axis.getZ() * axis.getZ() * C);

        return result;
    }

    public static Matrix4f scale(Vector3f scaleFactor) {
        Matrix4f result = Matrix4f.identity();

        result.set(0, 0, scaleFactor.getX());
        result.set(1, 1, scaleFactor.getY());
        result.set(2, 2, scaleFactor.getZ());

        return result;
    }

    public static Matrix4f mutliply(Matrix4f A, Matrix4f B) {
        Matrix4f result = Matrix4f.identity();

        for(int i = 0; i < SIZE; i++) {
            for(int j = 0; j < SIZE; j++) {
                result.set(i, j, A.get(i,0) * B.get(0,j) +
                                        A.get(i,1) * B.get(1,j) +
                                        A.get(i,2) * B.get(2,j) +
                                        A.get(i,3) * B.get(3,j));
            }
        }

        return result;
    }

    // Test function
    public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f result = Matrix4f.identity();

        Matrix4f translationMatrix = Matrix4f.translate(position);
        Matrix4f rotationXMatrix = Matrix4f.rotate(rotation.getX(), new Vector3f(1.0f,0,0));
        Matrix4f rotationYMatrix = Matrix4f.rotate(rotation.getY(), new Vector3f(0,1.0f,0));
        Matrix4f rotationZMatrix = Matrix4f.rotate(rotation.getZ(), new Vector3f(0,0,1.0f));
        Matrix4f scaleMatrix = Matrix4f.scale(scale);

        Matrix4f rotationMatrix = mutliply(rotationXMatrix, mutliply(rotationYMatrix, rotationZMatrix));

        result = mutliply(translationMatrix, mutliply(rotationMatrix, scaleMatrix));

        return result;
    }

    public float get(int x, int y) {
        return elements[y * SIZE + x];
    }

    public void set(int x, int y, float value) {
        elements[y * SIZE + x] = value;
    }

    public float[] getAll() {
        return elements;
    }
}
