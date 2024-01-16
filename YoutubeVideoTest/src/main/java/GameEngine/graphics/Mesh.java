package GameEngine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class Mesh {
    private Vertex[] vertices;
    // Order in which to draw the vertices
    private int[] indices;
    private int vertexArrayObject, positionBufferObject, indicesBufferObject;

    public Mesh(Vertex[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void create() {
        vertexArrayObject = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vertexArrayObject);
        /*
        All the buffers we add will be binded to the vertexArrayObject.
         */

        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] positionData = new float[vertices.length * 3];

        /*
        Stores all the (x,y,z) coordinates in an array so we can send it to the buffer so that GPU can do stuff.
         */
        for(int i = 0; i < vertices.length; i++) {
            positionData[i * 3] = vertices[i].getPosition().getX();
            positionData[i * 3 + 1] = vertices[i].getPosition().getY();
            positionData[i * 3 + 2] = vertices[i].getPosition().getZ();
        }

        positionBuffer.put(positionData).flip(); // For some reason OpenGl like the data flipped.

        positionBufferObject = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferObject);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, positionBuffer, GL15.GL_STATIC_DRAW);

        /*
        For when we implement shaders.
         */

        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0); // Tells the GPU how to read the data.

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Unbinds the buffer.

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        indicesBufferObject = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferObject);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); // Unbinds the buffer.
    }

    public int getIBO() {
        return indicesBufferObject;
    }

    public int getPBO() {
        return positionBufferObject;
    }

    public int getVAO() {
        return vertexArrayObject;
    }

    public int[] getIndices() {
        return indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}
