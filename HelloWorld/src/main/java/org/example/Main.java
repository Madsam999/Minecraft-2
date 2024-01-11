package org.example;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class Main {

    private static long window;

    public static void main(String[] args) {
        System.out.println("Hello world!");

        if(!GLFW.glfwInit()) {
            throw new IllegalStateException("Failed to initialize GLFW!");
        }

        // Configure GLFW
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        window = GLFW.glfwCreateWindow(1920, 1080, "Minecraft 2", 0, 0);

        if (window == 0) {
            GLFW.glfwTerminate();
            throw new RuntimeException("Failed to create the GLFW window");
        }

        // Center the window
        GLFWVidMode vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());
        GLFW.glfwSetWindowPos(window, (vidMode.width() - 800) / 2, (vidMode.height() - 600) / 2);

        // Make the OpenGL context current
        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        // Enable v-sync
        GLFW.glfwSwapInterval(1);

        // Make the window visible
        GLFW.glfwShowWindow(window);

        Random random = new Random();

        // Generate a random float between 0 (inclusive) and 1 (exclusive)
        float red = random.nextFloat();
        float green = random.nextFloat();
        float blue = random.nextFloat();

        // Render here
        GL11.glClearColor(red, green, blue, 0.0f);

        // Main game loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            // Poll for events
            GLFW.glfwPollEvents();

            handleInput();

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            // Swap the buffers
            GLFW.glfwSwapBuffers(window);
        }

        // Terminate GLFW
        GLFW.glfwTerminate();
    }

    private static void handleInput() {
        if (GLFW.glfwGetKey(window, GLFW.GLFW_KEY_SPACE) == GLFW.GLFW_PRESS) {
            System.out.println("Space was pressed!");

            Random random = new Random();

            // Generate a random float between 0 (inclusive) and 1 (exclusive)
            float red = random.nextFloat();
            float green = random.nextFloat();
            float blue = random.nextFloat();

            // Render here
            GL11.glClearColor(red, green, blue, 0.0f);
        }
    }
}