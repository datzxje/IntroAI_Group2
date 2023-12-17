package algorithm;

import input.Vertex;
import javafx.util.Pair;

public class MapRoad {
    Pair<Vertex, Vertex> road; // first is the starting point of a road, second is the ending.

    MapRoad(Vertex startVertex, Vertex endVertex) {
        this.road = new Pair<>(startVertex, endVertex);
    }

    public boolean checkRoadIntersection(MapRoad road1, MapRoad road2) {
        return doIntersect(road1.getStartVertex(), road1.getEndVertex(), road2.getStartVertex(), road2.getEndVertex());
    }

    int orientation(Vertex p1, Vertex p2, Vertex q) {
        double x = (p2.getY() - p1.getY()) * (q.getX() - p2.getX()) - (q.getY() - p2.getY()) * (p2.getX() - p1.getX());
        if (x == 0.0)
            return 0;
        return x < 0 ? 1 : 2;
    }

    boolean onSegment(Vertex a, Vertex b, Vertex c) {
        if (c.getX() >= Math.min(a.getX(), b.getX()) && c.getX() <= Math.max(a.getX(), b.getX()) && c.getY() >= Math.min(a.getY(), b.getY())
                && c.getY() <= Math.max(a.getY(), b.getY()))
            return true;
        return false;
    }

    boolean doIntersect(Vertex p1, Vertex q1, Vertex p2, Vertex q2) {
        int a = orientation(p1, q1, p2);
        int b = orientation(p1, q1, q2);
        int c = orientation(p2, q2, p1);
        int d = orientation(p2, q2, q1);

        if (a == 0 && onSegment(p1, q1, p2))
            return true;
        if (b == 0 && onSegment(p1, q1, q2))
            return true;
        if (c == 0 && onSegment(p2, q2, p1))
            return true;
        if (d == 0 && onSegment(p2, q2, q1))
            return true;
        if (a != b && c != d)
            return true;
        return false;
    }

    public Vertex getStartVertex() {
        return road.getKey();
    }

    public Vertex getEndVertex() {
        return road.getValue();
    }

    public static double computeDotProduct(MapRoad road1, MapRoad road2) {
        // Get the vectors representing the two roads
        double[] vector1 = getVector(road1);
        double[] vector2 = getVector(road2);

        // Calculate the dot product
        double dotProduct = 0.0;
        for (int i = 0; i < vector1.length; i++) {
            dotProduct += vector1[i] * vector2[i];
        }

        return dotProduct;
    }

    private static double[] getVector(MapRoad road) {
        Vertex startVertex = road.getStartVertex();
        Vertex endVertex = road.getEndVertex();

        // Create a vector from the starting to ending vertices
        double[] vector = { endVertex.getX() - startVertex.getX(), endVertex.getY() - startVertex.getY()};

        return vector;
    }
}
