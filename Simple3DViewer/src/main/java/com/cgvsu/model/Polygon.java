package com.cgvsu.model;

import java.util.ArrayList;

public class Polygon {

    private ArrayList<Integer> vertexIndices;
    private ArrayList<Integer> textureVertexIndices;
    private ArrayList<Integer> normalIndices;

    public Polygon() {
        this.vertexIndices = new ArrayList<>();
        this.textureVertexIndices = new ArrayList<>();
        this.normalIndices = new ArrayList<>();
    }

    public void addVertexIndex(final int vertexIndex) {
        this.vertexIndices.add(vertexIndex);
    }

    public void addTextureVertexIndex(final int textureVertexIndex) {
        this.textureVertexIndices.add(textureVertexIndex);
    }

    public void addNormalIndex(final int normalIndex) {
        this.normalIndices.add(normalIndex);
    }

    public void setVertexIndices(final ArrayList<Integer> vertexIndices) {
        assert vertexIndices.size() >= 3;
        this.vertexIndices = vertexIndices;
    }

    public void setTextureVertexIndices(final ArrayList<Integer> textureVertexIndices) {
        assert textureVertexIndices.size() >= 3;
        this.textureVertexIndices = textureVertexIndices;
    }

    public void setNormalIndices(final ArrayList<Integer> normalIndices) {
        assert normalIndices.size() >= 3;
        this.normalIndices = normalIndices;
    }

    public ArrayList<Integer> getVertexIndices() {
        return vertexIndices;
    }

    public ArrayList<Integer> getTextureVertexIndices() {
        return textureVertexIndices;
    }

    public ArrayList<Integer> getNormalIndices() {
        return normalIndices;
    }

    public boolean equals(final Polygon other) {
        return (this.vertexIndices.equals(other.vertexIndices)) &&
                (this.textureVertexIndices.equals(other.textureVertexIndices)) &&
                (this.normalIndices.equals(other.normalIndices));
    }
}
