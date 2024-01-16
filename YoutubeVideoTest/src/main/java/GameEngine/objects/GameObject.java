package GameEngine.objects;

import GameEngine.graphics.Mesh;
import GameEngine.maths.Vector3f;

public class GameObject {
    private Vector3f position, rotation, scale;
    private Mesh mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }

    public void update() {
        position.add(0.005f, 0,0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public Mesh getMesh() {
        return mesh;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }
}
