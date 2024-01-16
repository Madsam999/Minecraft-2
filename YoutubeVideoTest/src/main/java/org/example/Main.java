package org.example;

import GameEngine.graphics.*;
import GameEngine.io.Input;
import GameEngine.io.Window;
import GameEngine.maths.Vector2f;
import GameEngine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;

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
        new Vertex( new Vector3f(-0.5f, 0.5f, 0.0f), new Vector3f(1.0f, 0.0f, 0.0f), new Vector2f(0.0f,0.0f)),
        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f), new Vector3f(0.0f, 1.0f, 0.0f), new Vector2f(0.0f,1.0f)),
        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f), new Vector3f(0.0f, 0.0f, 1.0f), new Vector2f(1.0f,1.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f), new Vector3f(1.0f, 1.0f, 0.0f), new Vector2f(1.0f,0.0f))
    }, new int[] {
        /*
        The triangle the renderer will try to draw. With this configuration, it'll draw
        the triangle composed of vertice 0, 1 and 2 and a second triangle with vertice 0, 3 and 2.
         */
        0, 1, 2,
        0, 3, 2
    }, new Material("/textures/acacia_planks.png"));
    public Renderer renderer;


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
        }
        close();
    }

    public void update() {
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("Mouse clicked at: (" + Input.getScrollX() + "," + Input.getScrollY() + ")");
        }
    }

    public void render() {
        renderer.renderMesh(mesh);
        window.swapBuffers();
    }

    public void init() {
        shader = new Shader("/shaders/mainVertex.glsl", "/shaders/mainFragment.glsl");
        renderer = new Renderer(shader);
        window = new Window(WIDTH, HEIGHT, "Minecraft 2");
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