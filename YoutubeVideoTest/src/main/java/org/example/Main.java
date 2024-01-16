package org.example;

import GameEngine.graphics.Mesh;
import GameEngine.graphics.Renderer;
import GameEngine.graphics.Vertex;
import GameEngine.io.Input;
import GameEngine.io.Window;
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


    public Mesh mesh = new Mesh(new Vertex[] {
        new Vertex( new Vector3f(-0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, 0.5f, 0.0f)),
        new Vertex(new Vector3f(0.5f, -0.5f, 0.0f)),
        new Vertex(new Vector3f(-0.5f, -0.5f, 0.0f))
    }, new int[] {
        /*
        The triangle the renderer will try to draw. With this configuration, it'll draw
        the triangle composed of vertice 0, 1 and 2 and a second triangle with vertice 0, 3 and 2.
         */
        0, 1, 2,
        0, 3, 2
    });

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
        window.destroy();
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
        renderer = new Renderer();
        window = new Window(WIDTH, HEIGHT, "Minecraft 2");
        window.setBackgroundColor(1.0f, 0.0f, 1.0f);
        window.create();
        mesh.create();
    }




    public static void main(String[] args) {
        new Main().start();
    }
}