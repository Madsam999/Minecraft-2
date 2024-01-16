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
    private int vertexArrayObject, positionBufferObject, indicesBufferObject, colourBufferObject;

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

        positionBufferObject = storeData(positionBuffer, 0, 3);

        FloatBuffer colourBuffer = MemoryUtil.memAllocFloat(vertices.length * 3);
        float[] colourData = new float[vertices.length * 3];

        /*
        Stores all the (x,y,z) coordinates in an array so we can send it to the buffer so that GPU can do stuff.
         */
        for(int i = 0; i < vertices.length; i++) {
            colourData[i * 3] = vertices[i].getColour().getX();
            colourData[i * 3 + 1] = vertices[i].getColour().getY();
            colourData[i * 3 + 2] = vertices[i].getColour().getZ();
        }

        colourBuffer.put(colourData).flip(); // For some reason OpenGl like the data flipped.

        colourBufferObject = storeData(colourBuffer, 1, 3);

        IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
        indicesBuffer.put(indices).flip();

        indicesBufferObject = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBufferObject);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0); // Unbinds the buffer.
    }

    private int storeData(FloatBuffer buffer, int index, int size) {
        int bufferID = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, bufferID);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0); // Tells the GPU how to read the data.
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); // Unbinds the buffer.
        return bufferID;
    }

    public void destroy() {
        GL15.glDeleteBuffers(positionBufferObject);
        GL15.glDeleteBuffers(colourBufferObject);
        GL15.glDeleteBuffers(indicesBufferObject);

        GL30.glDeleteVertexArrays(vertexArrayObject);
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

    public int getCBO() {
        return colourBufferObject;
    }

    public int[] getIndices() {
        return indices;
    }

    public Vertex[] getVertices() {
        return vertices;
    }
}
