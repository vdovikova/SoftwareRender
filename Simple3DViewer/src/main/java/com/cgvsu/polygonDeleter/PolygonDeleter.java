package com.cgvsu.polygonDeleter;

import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Iterator;

public class PolygonDeleter {

    public static Model deletePolygon(Model model, int polygonIndex) {
        Polygon polygon = model.polygons.get(polygonIndex);
        ArrayList<Integer> vertices = (ArrayList<Integer>) polygon.getVertexIndices();

        model.polygons.remove(polygonIndex);

        ArrayList<Integer> freeVertices = findFreeVertices(model, (ArrayList<Integer>) polygon.getVertexIndices());

        if (!freeVertices.isEmpty()) {
            if (needToDelFreeVertices(freeVertices)) {
                deleteFreeVertices(model, freeVertices);
            }
        }

        return model;
    }

    protected static ArrayList<Integer> findFreeVertices(Model model, ArrayList<Integer> vertexIndices) {
        for (Polygon polygon : model.polygons) {
            for (Integer polygonVertex : polygon.getVertexIndices()) {
                Iterator<Integer> vertexIterator = vertexIndices.iterator();
                while (vertexIterator.hasNext()) {
                    Integer vertex = vertexIterator.next();
                    if (vertex.equals(polygonVertex)) {
                        vertexIterator.remove();
                        continue;
                    }
                }
            }
        }
        return vertexIndices;
    }

    protected static void deleteFreeVertices(Model model, ArrayList<Integer> freeVerticesIndices) {
        ArrayList<Vector3f> freeVertices = new ArrayList<>();
        for (Integer vertexIndex : freeVerticesIndices) {
            freeVertices.add(model.vertices.get(vertexIndex));
        }

        model.vertices.removeAll(freeVertices);

        freeVerticesIndices.sort(Integer::compareTo);

        for (Polygon polygon: model.polygons) {
            ArrayList<Integer> vertexIndices = (ArrayList<Integer>) polygon.getVertexIndices();
            for (int i = 0; i < vertexIndices.size(); i++) {
                int vertexIndex = vertexIndices.get(i);
                for (int j = freeVerticesIndices.size() - 1; j >= 0; j--) {
                    if (vertexIndex < freeVerticesIndices.get(j)) {
                        continue;
                    }
                    if (vertexIndex >= freeVerticesIndices.get(j)) {
                        vertexIndex--;
                        vertexIndices.set(i, vertexIndex);
                    }
                }
            }
            polygon.setVertexIndices(vertexIndices);
        }
    }

    //Здесь можно реализовать опрос юзера о необходимости удаления получившихся свободных вершин
    private static boolean needToDelFreeVertices(ArrayList<Integer> freeVertices) {
        return true;
    }
}
