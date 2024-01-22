package GameEngine.objects;

import GameEngine.graphics.Renderer;

public class World {
    private GameObject[] objects;

    public World(int size) {
        this.objects = new GameObject[size * size * size];
    }

    public void update(Renderer renderer, Camera camera) {
        for (GameObject object : objects) {
            renderer.renderMesh(object,camera);
        }
    }

}
