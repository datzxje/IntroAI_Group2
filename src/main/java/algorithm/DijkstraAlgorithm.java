package algorithm;

import java.util.*;

public class DijkstraAlgorithm {
    private Vector<Vector<Double>> distanceMatrix;

    public DijkstraAlgorithm(Vector<Vector<Double>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> findShortestPath(int start, int goal) {
        PriorityQueue<DijkstraVertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(v -> v.getDistance()));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Map<Integer, Double> gScore = new HashMap<>();

        openSet.add(new DijkstraVertex(start, 0));
        gScore.put(start, 0.0);

        while (!openSet.isEmpty()) {
            DijkstraVertex current = openSet.poll();

            if (current.getId() == goal) {
                return reconstructPath(cameFrom, goal);
            }

            closedSet.add(current.getId());

            for (int neighbor = 0; neighbor < distanceMatrix.size(); neighbor++) {
                double cost = distanceMatrix.get(current.getId()).get(neighbor);
                if (cost > 0 && !closedSet.contains(neighbor)) {
                    double tentativeGScore = gScore.get(current.getId()) + cost;

                    if (!gScore.containsKey(neighbor) || tentativeGScore < gScore.get(neighbor)) {
                        gScore.put(neighbor, tentativeGScore);
                        openSet.add(new DijkstraVertex(neighbor, tentativeGScore));
                        cameFrom.put(neighbor, current.getId());
                    }
                }
            }
        }

        return null; // No path found
    }

    private List<Integer> reconstructPath(Map<Integer, Integer> cameFrom, int current) {
        List<Integer> path = new ArrayList<>();
        path.add(current);

        while (cameFrom.containsKey(current)) {
            current = cameFrom.get(current);
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