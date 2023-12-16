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
    public double calculateTotalWeight(List<Integer> path) {
        double totalWeight = 0.0;

        for (int i = 0; i < path.size() - 1; i++) {
            int currentVertex = path.get(i);
            int nextVertex = path.get(i + 1);

            totalWeight += distanceMatrix.get(currentVertex).get(nextVertex);
        }

        return totalWeight;
    }
    public List<List<Integer>> findShortestPaths(Integer[] startVertices, Integer[] goalVertices) {
        List<List<Integer>> shortestPaths = new ArrayList<>();

        for (int i = 0; i < Math.min(startVertices.length, goalVertices.length); i++) {
            int startVertex = startVertices[i];
            int goalVertex = goalVertices[i];

            List<Integer> shortestPath = findShortestPath(startVertex, goalVertex);
            shortestPaths.add(shortestPath);
        }

        return shortestPaths;
    }

}
