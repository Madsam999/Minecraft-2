package org.example;

import GameEngine.io.Input;
import GameEngine.io.Window;
import org.lwjgl.glfw.GLFW;

public class Main implements Runnable{

    /*
    This project is following a Youtube tutorial on how to make a simple game using LWJGL.
    Credit: https://www.youtube.com/watch?v=QtUYQ92anG4&t=46s @ CodingAP
     */

    public Thread game;

    public Window window;
    public final int WIDTH = 1280, HEIGHT = 760;

    public void start() {
        game = new Thread(this,"game");
        game.start();
    }

    public void run() {
        init();

        while(!window.shouldClose()) {
            update();
            render();

            if(Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
                return;
            }

        }
        window.destroy();
    }

    public void update() {
        window.update();
        if(Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("Mouse clicked at: (" + Input.getMouseX() + "," + Input.getMouseY() + ")");
        }
    }

    public void render() {
        window.swapBuffers();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Minecraft 2");
        window.create();
    }




    public static void main(String[] args) {
        new Main().start();
    }
}