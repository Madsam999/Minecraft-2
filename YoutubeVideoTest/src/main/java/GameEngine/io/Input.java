package GameEngine.io;

import org.lwjgl.glfw.*;

public class Input {

    private static boolean[] keys = new boolean[GLFW.GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];

    private static double mouseX, mouseY;
    private static double scrollX, scrollY;
    private GLFWKeyCallback keyboard;
    private GLFWCursorPosCallback mouse;
    private GLFWMouseButtonCallback mouseButtons;
    private GLFWScrollCallback scrollCallback;

    public Input() {
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scanCode, int action, int mods) {
                keys[key] = (action != GLFW.GLFW_RELEASE);
            }
        };

        mouse = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };

        mouseButtons = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };

        scrollCallback = new GLFWScrollCallback() {
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public static boolean isKeyDown(int key) {
        return keys[key];
    }

    public static boolean isButtonDown(int button) {
        return buttons[button];
    }


    public void destroy() {
        keyboard.free();
        mouse.free();
        mouseButtons.free();
        scrollCallback.free();
    }

    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }

    public GLFWKeyCallback getKeyboardCallBack() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouseButtonsCallBack() {
        return mouseButtons;
    }

    public GLFWCursorPosCallback getMouseMoveCallBack() {
        return mouse;
    }

    public GLFWScrollCallback getScrollCallback() {
        return scrollCallback;
    }

    public static double getScrollX() {
        return scrollX;
    }

    public static double getScrollY() {
        return scrollY;
    }
}
