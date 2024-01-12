package GameEngine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {

    private int width, height;

    private String title;

    private long window;

    public int frames;
    public long time;

    public Input input;

    public Window(int width, int height, String title) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.input = new Input();
    }

    public void create() {
        if(!GLFW.glfwInit()) {
            System.out.println("Failed to initialize GLFW!");
            return;
        }

        window = GLFW.glfwCreateWindow(width, height, title, 0, 0);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        if(window == 0) {
            System.out.println("Failed to create GLFW window!");
            return;
        }

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallBack());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallBack());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallBack());

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        GLFW.glfwSetWindowPos(window, (videoMode.width() - width) / 2, (videoMode.height() - height) / 2);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }

    public void update() {
        GLFW.glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time + 1000) {
            time = System.currentTimeMillis();
            GLFW.glfwSetWindowTitle(window, title + " | FPS: " + frames);
            frames = 0;
        }
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(window);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(window);
    }

    public void destroy() {
        input.destroy();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
