package input;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

public class Graph {
    private List<Vertex> vertexList;
    private Vector<Vector<Integer>> adjacencyList;
    private Vector<Vector<Double>> distanceMatrix;
    public Graph() {
        vertexList = new ArrayList<>();
        adjacencyList = new Vector<>(Constants.ARRAY_SIZE);
        for (int i = 0; i < Constants.ARRAY_SIZE; i++) {
            adjacencyList.add(new Vector<>());
        }
        String filePathForAdjacencyList = "D:\\MapApplication\\src\\main\\resources\\app\\adjacencList.txt";
        readAdjacencyListFromFile(filePathForAdjacencyList);
        String filePathfromCoordinatesVertex = "D:\\MapApplication\\src\\main\\resources\\app\\Vertices.txt";
        insertVertexFromFile(filePathfromCoordinatesVertex, vertexList);
        updateDistanceMatrix();
    }

    public List<Vertex> getVertexList() {
        return vertexList;
    }

    public void setVertexList(List<Vertex> vertexList) {
        this.vertexList = vertexList;
    }

    public Vector<Vector<Integer>> getAdjacencyList() {
        return adjacencyList;
    }

    public void setAdjacencyList(Vector<Vector<Integer>> adjacencyList) {
        this.adjacencyList = adjacencyList;
    }

    public Vector<Vector<Double>> getDistanceMatrix() {
        return distanceMatrix;
    }

    public void setDistanceMatrix(Vector<Vector<Double>> distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    private void readAdjacencyListFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);

                if (tokenizer.hasMoreTokens()) {
                    int vertex = Integer.parseInt(tokenizer.nextToken());
                    while (tokenizer.hasMoreTokens()) {
                        int neighbor = Integer.parseInt(tokenizer.nextToken());
                        checkEdge(vertex, neighbor);
                        adjacencyList.get(vertex).add(neighbor);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void insertVertexFromFile(String filePath, List<Vertex> verList) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int id = 0;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");
                if (parts.length == 2) {
                    double xCordinate = Double.parseDouble(parts[0]);
                    double yCordinate = Double.parseDouble(parts[1]);
                    Vertex vertex = new Vertex(id, xCordinate, yCordinate);
                    verList.add(vertex);
                }
                id++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDistanceMatrix() {
        distanceMatrix = new Vector<>(Constants.ARRAY_SIZE);

        for (int i = 0; i < Constants.ARRAY_SIZE; i++) {
            Vector<Double> row = new Vector<>(Constants.ARRAY_SIZE);

            for (int j = 0; j < Constants.ARRAY_SIZE; j++) {
                row.add((i == j) ? 0.0 : -1.0);
            }

            distanceMatrix.add(row);
        }

        for (int i = 0; i < adjacencyList.size(); i++) {
            for (int neighbor : adjacencyList.get(i)) {
                Vertex vertex1 = new Vertex(i,vertexList.get(i).getX(), vertexList.get(i).getY());
                Vertex vertex2 = new Vertex(i,vertexList.get(neighbor).getX(), vertexList.get(neighbor).getY());

                if (i != neighbor) {
                    distanceMatrix.get(i).set(neighbor, Vertex.Distance(vertex1, vertex2));
                }
            }
        }
    }

    private void checkEdge(int vertex, int neighbor) {
        if (vertex >= neighbor) {
            for (int i = 0; i < adjacencyList.get(neighbor).size(); i++) {
                if (adjacencyList.get(neighbor).get(i) == vertex) {
                    return;
                }
                if (i == adjacencyList.get(neighbor).size() - 1) {
                    System.out.println("ERROR:" + (vertex) + " " + (neighbor));
                }
            }
        }
    }
}

class Constants {
    public static final int ARRAY_SIZE = 301;
}
