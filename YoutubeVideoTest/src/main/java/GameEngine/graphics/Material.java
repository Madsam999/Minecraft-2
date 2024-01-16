package GameEngine.graphics;

import GameEngine.utils.FileUtils;
import org.example.Main;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjglx.Sys;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.IOException;

public class Material {
    private String path;
    private Texture texture;
    private float width, height;
    private int textureID;
    public Material(String path) {
        this.path = path;
    }

    public void create() {
        try {
            texture = TextureLoader.getTexture(path.split("[.]")[1], Main.class.getResourceAsStream(path), GL11.GL_NEAREST);
            System.out.println(texture.getTextureRef());
            width = texture.getWidth();
            height = texture.getHeight();
            textureID = texture.getTextureID();
        }
        catch (IOException e) {
            System.out.println("Can't find texture at " + path);
        }
    }

    public void destroy() {
        GL13.glDeleteTextures(textureID);
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public int getTextureID() {
        return textureID;
    }
}
