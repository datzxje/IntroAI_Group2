package algorithm;

import input.Graph;
import input.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class FindStartEndVertex {
    public static List<Vertex> findStartAndGoalPair(Graph graph, Vertex startPoint, Vertex endPoint) {
        PriorityQueue<VertexDistancePair> topResultofStartpoint = FindTopPotentialVertex(graph, startPoint);
        PriorityQueue<VertexDistancePair> topResultofEndpoint = FindTopPotentialVertex(graph, endPoint);

        double minDistance = Double.MAX_VALUE;
        List<Vertex> pairVertex = new ArrayList<>();
        AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(graph.getDistanceMatrix());
        // Iterate through the points from topResultofStartpoint
        while (!topResultofStartpoint.isEmpty()) {
            VertexDistancePair startPair = topResultofStartpoint.poll();

            // Iterate through the points from topResultofEndpoint
            while (!topResultofEndpoint.isEmpty()) {
                VertexDistancePair endPair = topResultofEndpoint.poll();

                // Compute the distance between the current start and end points
                double distance = Vertex.Distance(startPoint, graph.getVertexList().get(startPair.sequenceNumber))
                        + Vertex.Distance(endPoint, graph.getVertexList().get(endPair.sequenceNumber))
                        + aStarAlgorithm.computePathDistance(startPair.sequenceNumber, endPair.sequenceNumber);

                // Check if this pair has the smallest distance so far
                if (distance < minDistance) {
                    minDistance = distance;
                    pairVertex.add(graph.getVertexList().get(startPair.sequenceNumber));
                    pairVertex.add(graph.getVertexList().get(endPair.sequenceNumber));
                }
            }
        }

        return pairVertex;
    }

    private static PriorityQueue<VertexDistancePair> FindTopPotentialVertex(Graph graph, Vertex point) {
        PriorityQueue<VertexDistancePair> minHeap = new PriorityQueue<>(5,
                Comparator.comparingDouble(pair -> pair.distance));

        // Calculate distances and insert into the minHeap, considering road
        // intersections
        for (int i = 0; i < graph.getVertexList().size(); i++) {
            Vertex currentVertex = graph.getVertexList().get(i);
            if (currentVertex != null) {
                // Check if currentVertex is equal to either road2.startVertex or
                // road2.endVertex
                // Check if the road represented by currentVertex intersects with any road in
                // the graph
                boolean intersects = false;
                for (int j = 0; j < graph.getAdjacencyList().size(); j++) {
                    for (int k = 0; k < graph.getAdjacencyList().get(j).size(); k++) {
                        int adjacentVertexIndex = graph.getAdjacencyList().get(j).get(k);
                        Vertex adjacentVertex = graph.getVertexList().get(adjacentVertexIndex);
                        if (adjacentVertex != null && !currentVertex.equals(adjacentVertex)) {
                            MapRoad road1 = new MapRoad(currentVertex, point);
                            MapRoad road2 = new MapRoad(graph.getVertexList().get(j), adjacentVertex);
                            if (!currentVertex.equals(road2.getStartVertex())
                                    && !currentVertex.equals(road2.getEndVertex())) {
                                if (!point.equals(road2.getStartVertex())
                                        && !point.equals(road2.getEndVertex())) {
                                    if (road1.checkRoadIntersection(road1, road2)) {
                                        intersects = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    if (intersects) {
                        break;
                    }
                }

                // If there are no intersections, calculate distance and add to minHeap
                if (!intersects) {
                    double distance = Vertex.Distance(point, currentVertex);
                    minHeap.offer(new VertexDistancePair(i, currentVertex, distance));
                }
            }
        }

        return minHeap;
    }

    private static class VertexDistancePair {
        int sequenceNumber;
        Vertex vertex;
        double distance;

        VertexDistancePair(int sequenceNumber, Vertex vertex, double distance) {
            this.sequenceNumber = sequenceNumber;
            this.vertex = vertex;
            this.distance = distance;
        }
    }

}
