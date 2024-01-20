package GameEngine.graphics;

import GameEngine.maths.Vector2f;
import GameEngine.maths.Vector3f;

public class Vertex {
    private Vector3f position, colour;
    private Vector2f textureCoords;

    public Vertex(Vector3f position, Vector2f textureCoords) {
        this.position = position;
        this.textureCoords = textureCoords;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector2f getTextureCoords() {
        return textureCoords;
    }
}
