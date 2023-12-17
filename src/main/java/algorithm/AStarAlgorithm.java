package algorithm;

import input.aVertex;

import java.util.*;

public class AStarAlgorithm {
    private Vector<Vector<Double>> distanceMatrix;

    public AStarAlgorithm(Vector<Vector<Double>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
    public List<Integer> findShortestPath(int start, int goal) {
        PriorityQueue<aVertex> openSet = new PriorityQueue<>(Comparator.comparingDouble(v -> v.getF()));
        Set<Integer> closedSet = new HashSet<>();
        Map<Integer, Integer> cameFrom = new HashMap<>();
        Map<Integer, Double> gScore = new HashMap<>();

        openSet.add(new aVertex(start, 0, calculateHeuristic(start, goal)));
        gScore.put(start, 0.0);

        while (!openSet.isEmpty()) {
            aVertex current = openSet.poll();

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
                        double heuristic = calculateHeuristic(neighbor, goal);
                        openSet.add(new aVertex(neighbor, tentativeGScore, heuristic));
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

    private double calculateHeuristic(int current, int goal) {
        // This is a simple heuristic; you may use a more sophisticated one based on your problem
        return distanceMatrix.get(current).get(goal);
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
