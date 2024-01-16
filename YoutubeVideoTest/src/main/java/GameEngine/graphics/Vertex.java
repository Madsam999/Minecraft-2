package GameEngine.graphics;

import GameEngine.maths.Vector3f;

public class Vertex {
    private Vector3f position, colour;

    public Vertex(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColour() {
        return colour;
    }
}
