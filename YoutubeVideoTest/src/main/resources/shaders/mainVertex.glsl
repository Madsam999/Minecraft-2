#version 460 core

in vec3 position;
in vec3 colour;
in vec2 textureCoords;

out vec3 passColour;
out vec2 passTextureCoord;

void main() {
    gl_Position = vec4(position, 1.0);
    passColour  = colour;
    passTextureCoord = textureCoords;
}