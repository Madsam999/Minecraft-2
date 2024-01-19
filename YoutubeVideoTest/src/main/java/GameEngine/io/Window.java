package GameEngine.io;

import GameEngine.maths.Matrix4f;
import GameEngine.maths.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {

    private int width, height;

    private String title;

    private long window;

    public int frames;
    public long time;

    public Input input;
    private Vector3f background = new Vector3f(0.0f, 0.0f, 0.0f);
    private GLFWWindowSizeCallback sizeCallBack;
    private boolean isResized;
    private boolean isFullscreen;
    private int[] windowPosX = new int[1], windowPosY = new int[1];
    private Matrix4f projection;

    public Window(int width, int height, String title) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.projection = Matrix4f.projection((float) width/(float) height, 70.0f, 0.1f, 1000.0f);
    }

    public void create() {
        if(!GLFW.glfwInit()) {
            System.out.println("Failed to initialize GLFW!");
            return;
        }

        input = new Input();
        window = GLFW.glfwCreateWindow(width, height, title, isFullscreen ? GLFW.glfwGetPrimaryMonitor() : 0, 0);

        GLFW.glfwMakeContextCurrent(window);
        GL.createCapabilities();

        if(window == 0) {
            System.out.println("Failed to create GLFW window!");
            return;
        }

        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallBack());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallBack());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallBack());
        GL11.glEnable(GL11.GL_DEPTH_TEST);

        createCallBacks();

        GLFWVidMode videoMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        windowPosX[0] = (videoMode.width() - width) / 2;
        windowPosY[0] = (videoMode.height() - height) / 2;

        GLFW.glfwSetWindowPos(window, windowPosX[0], windowPosY[0]);

        GLFW.glfwSwapInterval(1);

        time = System.currentTimeMillis();
    }

    private void createCallBacks() {
        sizeCallBack = new GLFWWindowSizeCallback() {
            public void invoke(long window, int w, int h) {
                width = w;
                height = h;
                isResized = true;
            }
        };
        GLFW.glfwSetKeyCallback(window, input.getKeyboardCallBack());
        GLFW.glfwSetCursorPosCallback(window, input.getMouseMoveCallBack());
        GLFW.glfwSetMouseButtonCallback(window, input.getMouseButtonsCallBack());
        GLFW.glfwSetScrollCallback(window, input.getScrollCallback());
        GLFW.glfwSetWindowSizeCallback(window, sizeCallBack);
    }

    public void update() {
        if(isResized) {
            GL11.glViewport(0,0,width,height);
            isResized = false;
        }
        GL11.glClearColor(background.getX(), background.getY(), background.getZ(), 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

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
        sizeCallBack.free();
        GLFW.glfwWindowShouldClose(window);
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public void setBackgroundColor(float r, float g, float b) {
        background.set(r, g, b);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public long getWindow() {
        return window;
    }

    public boolean isFullscreen() {
        return isFullscreen;
    }

    public void setFullscreen(boolean fullscreen) {
        isFullscreen = fullscreen;
        isResized = true;
        if(isFullscreen) {
            GLFW.glfwGetWindowPos(window,windowPosX, windowPosY);
            GLFW.glfwSetWindowMonitor(window, GLFW.glfwGetPrimaryMonitor(), 0, 0, width, height, 0);
        }
        else {
            GLFW.glfwSetWindowMonitor(window, 0, windowPosX[0], windowPosY[0], width, height, 0);
        }

    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }
}
