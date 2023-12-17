package algorithm;

import java.util.*;

public class DijkstraAlgorithm {
    private Vector<Vector<Double>> distanceMatrix;

    public DijkstraAlgorithm(Vector<Vector<Double>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> findShortestPath(int start, int goal) {
        PriorityQueue<DijkstraVertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(v -> v.getDistance()));
        Set<Integer> visitedSet = new HashSet<>();
        Map<Integer, Integer> predecessorMap = new HashMap<>();
        Map<Integer, Double> distanceMap = new HashMap<>();

        priorityQueue.add(new DijkstraVertex(start, 0));

        while (!priorityQueue.isEmpty()) {
            DijkstraVertex current = priorityQueue.poll();

            if (current.getId() == goal) {
                return reconstructPath(predecessorMap, goal);
            }

            visitedSet.add(current.getId());

            for (int neighbor = 0; neighbor < distanceMatrix.size(); neighbor++) {
                double cost = distanceMatrix.get(current.getId()).get(neighbor);
                if (cost > 0 && !visitedSet.contains(neighbor)) {
                    double tentativeDistance = distanceMap.getOrDefault(current.getId(), Double.POSITIVE_INFINITY) + cost;

                    if (!distanceMap.containsKey(neighbor) || tentativeDistance < distanceMap.get(neighbor)) {
                        distanceMap.put(neighbor, tentativeDistance);
                        priorityQueue.add(new DijkstraVertex(neighbor, tentativeDistance));
                        predecessorMap.put(neighbor, current.getId());
                    }
                }
            }
        }

        return null; // No path found
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> predecessorMap, int current) {
        List<Integer> path = new ArrayList<>();
        path.add(current);

        while (predecessorMap.containsKey(current)) {
            current = predecessorMap.get(current);
            path.add(current);
        }

        Collections.reverse(path);
        return path;
    }

    public double computePathDistance(int start, int goal) {
        List<Integer> path = findShortestPath(start, goal);
        if (path == null || path.size() < 2) {
            // Invalid path or path with less than 2 vertices
            return 0.0;
        }

        double totalDistance = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            int currentVertex = path.get(i);
            int nextVertex = path.get(i + 1);

            double edgeWeight = distanceMatrix.get(currentVertex).get(nextVertex);

            // Assuming a positive edge weight indicates a valid connection
            if (edgeWeight > 0) {
                totalDistance += edgeWeight;
            } else {
                // Handle invalid or disconnected vertices
                return 0.0;
            }
        }

        return totalDistance;
    }
}
class DijkstraVertex {
    private int id; // Địa chỉ đỉnh
    private double distance; // Khoảng cách từ đỉnh xuất phát đến đỉnh hiện tại

    public DijkstraVertex(int id, double distance) {
        this.id = id;
        this.distance = distance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
