package com.cgvsu.model;
import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;

import java.util.*;

public class Model {

    public ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
    public ArrayList<Vector2f> textureVertices = new ArrayList<Vector2f>();
    public ArrayList<Vector3f> normals = new ArrayList<Vector3f>();
    public ArrayList<Polygon> polygons = new ArrayList<Polygon>();

    public void addVertex(final Vector3f vertex) {
        this.vertices.add(vertex);
    }

    public void addTextureVertex(final Vector2f textureVertex) {
        this.textureVertices.add(textureVertex);
    }

    public void addNormal(final Vector3f normal) {
        this.normals.add(normal);
    }

    public void addPolygon(final Polygon polygon) {
        this.polygons.add(polygon);
    }

    public void setVertices(final ArrayList<Vector3f> vertices) {
        assert vertices.size() >= 3;
        this.vertices = vertices;
    }

    public void setTextureVertices(final ArrayList<Vector2f> textureVertices) {
        assert textureVertices.size() >= 3;
        this.textureVertices = textureVertices;
    }

    public void setNormals(final ArrayList<Vector3f> normals) {
        assert normals.size() >= 3;
        this.normals = normals;
    }

    public void setPolygons(final ArrayList<Polygon> polygons) {
        assert polygons.size() >= 3;
        this.polygons = polygons;
    }

    public ArrayList<Vector3f> getVertices() {
        return this.vertices;
    }

    public ArrayList<Vector2f> getTextureVertices() {
        return this.textureVertices;
    }

    public ArrayList<Vector3f> getNormals() {
        return this.normals;
    }

    public ArrayList<Polygon> getPolygons() {
        return this.polygons;
    }

    public boolean equals(final Model other) {
        return (this.vertices.equals(other.vertices)) &&
                (this.textureVertices.equals(other.textureVertices)) &&
                (this.normals.equals(other.normals)) &&
                (this.polygons.equals(other.polygons));
    }
}
