package org.example;

import GameEngine.graphics.*;
import GameEngine.io.Input;
import GameEngine.io.Window;
import GameEngine.maths.Vector2f;
import GameEngine.maths.Vector3f;
import GameEngine.objects.Camera;
import GameEngine.objects.GameObject;
import GameEngine.utils.MusicPlayer;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.test.spaceinvaders.Game;

public class Main implements Runnable{

    /*
    This project is following a Youtube tutorial on how to make a simple game using LWJGL.
    Credit: https://www.youtube.com/watch?v=QtUYQ92anG4&t=46s @ CodingAP
     */

    public Thread game;
    public Window window;
    public final int WIDTH = 1280, HEIGHT = 760;
    public Shader shader;
    public Mesh mesh = new Mesh(new Vertex[] {
            //Back face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 0.0f)),

            //Front face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Right face
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Left face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Top face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),

            //Bottom face
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector2f(0.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector2f(0.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector2f(1.0f, 1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector2f(1.0f, 0.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    }, new Material("/textures/acacia_planks.png"));

    public GameObject gameObject = new GameObject(new Vector3f(0,0,0),new Vector3f(0,0,0),new Vector3f(16,1,1), mesh);

    public GameObject[] objects = new GameObject[8 * 8 * 8];
    public Renderer renderer;

    public Camera camera = new Camera(new Vector3f(0,0,1), new Vector3f(0,0,0));


    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public void run() {
        init();

        while(!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if(Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());
            if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseLock(true);
        }
        close();
    }

    public void update() {
        window.update();
        camera.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("Mouse clicked at: (" + Input.getScrollX() + "," + Input.getScrollY() + ")");
        }
    }

    public void render() {

        for(GameObject object : objects) {
            renderer.renderMesh(object, camera);
        }

        window.swapBuffers();
    }

    public void init() {
        MusicPlayer.playMusic("/music/assets_minecraft_sounds_music_game_minecraft.wav");
        float y = 0.0f;
        int z = 0;
        for(int i = 0; i < objects.length; i++) {
            if(i % 64 == 0) y += 1.0f;
            if(i % 64 == 0) z = 0;
            objects[i] = new GameObject(new Vector3f(i % 8, y, (z/8)), new Vector3f(0,0,0), new Vector3f(1,1,1), mesh);
            z += 1;
            System.out.println(objects[i].getPosition().getX() + " | " + objects[i].getPosition().getY() + " | " + objects[i].getPosition().getZ());
        }

        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        window = new Window(WIDTH, HEIGHT, "Minecraft 2");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(1.0f, 0.0f, 1.0f);
        window.create();
        mesh.create();
        shader.create();
    }

    private void close() {
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }




    public static void main(String[] args) {
        new Main().start();
    }
}