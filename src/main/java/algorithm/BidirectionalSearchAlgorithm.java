package algorithm;
import java.util.*;
public class BidirectionalSearchAlgorithm {
    private Vector<Vector<Double>> distanceMatrix;

    public BidirectionalSearchAlgorithm(Vector<Vector<Double>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public List<Integer> findShortestPathBidirectional(int start, int goal) {
        Set<Integer> forwardVisited = new HashSet<>();
        Set<Integer> backwardVisited = new HashSet<>();
        Map<Integer, Integer> forwardCameFrom = new HashMap<>();
        Map<Integer, Integer> backwardCameFrom = new HashMap<>();
        Queue<Integer> forwardQueue = new LinkedList<>();
        Queue<Integer> backwardQueue = new LinkedList<>();

        forwardQueue.add(start);
        backwardQueue.add(goal);
        forwardVisited.add(start);
        backwardVisited.add(goal);

        while (!forwardQueue.isEmpty() && !backwardQueue.isEmpty()) {
            // Expand forward search
            int forwardCurrent = forwardQueue.poll();
            for (int neighbor : getNeighbors(forwardCurrent, forwardVisited)) {
                forwardQueue.add(neighbor);
                forwardVisited.add(neighbor);
                forwardCameFrom.put(neighbor, forwardCurrent);

                // Check for intersection
                if (backwardVisited.contains(neighbor)) {
                    return reconstructBidirectionalPath(forwardCameFrom, backwardCameFrom, neighbor);
                }
            }

            // Expand backward search
            int backwardCurrent = backwardQueue.poll();
            for (int neighbor : getNeighbors(backwardCurrent, backwardVisited)) {
                backwardQueue.add(neighbor);
                backwardVisited.add(neighbor);
                backwardCameFrom.put(neighbor, backwardCurrent);

                // Check for intersection
                if (forwardVisited.contains(neighbor)) {
                    return reconstructBidirectionalPath(forwardCameFrom, backwardCameFrom, neighbor);
                }
            }
        }

        return null; // No path found
    }

    private List<Integer> getNeighbors(int vertex, Set<Integer> visited) {
        List<Integer> neighbors = new ArrayList<>();
        for (int i = 0; i < distanceMatrix.size(); i++) {
            if (distanceMatrix.get(vertex).get(i) > 0 && !visited.contains(i)) {
                neighbors.add(i);
            }
        }
        return neighbors;
    }

    private List<Integer> reconstructBidirectionalPath(Map<Integer, Integer> forwardCameFrom, Map<Integer, Integer> backwardCameFrom, int intersection) {
        List<Integer> path = new ArrayList<>();

        // Reconstruct path from start to intersection
        Integer current = intersection;
        while (forwardCameFrom.containsKey(current)) {
            path.add(current);
            current = forwardCameFrom.get(current);
        }

        // Reconstruct path from goal to intersection
        Collections.reverse(path);
        current = backwardCameFrom.get(intersection);
        while (current != null) {
            path.add(current);
            current = backwardCameFrom.get(current);
        }

        return path;
    }
}