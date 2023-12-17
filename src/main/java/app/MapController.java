package app;

import algorithm.AStarAlgorithm;
import algorithm.FindStartEndVertex;
import input.Graph;
import input.Vertex;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static algorithm.FindStartEndVertex.findStartAndGoalPair;

public class MapController {
    private boolean isStartPositionSelected = false;
    @FXML
    public ImageView startHighlightIcon;
    @FXML
    public ImageView goalHighlightIcon;
    @FXML
    private Pane dotPane;
    @FXML
    private Circle dot;
    @FXML
    private ImageView mapView;
    @FXML
    private Button showRouteButton;
    @FXML
    private Button hideRouteButton;
    @FXML
    private ImageView startIcon;
    @FXML
    private ImageView goalIcon;
    @FXML
    private RadioButton startPositionRadioButton;
    @FXML
    private RadioButton goalPositionRadioButton;
    @FXML
    public Button deletePointButton;
    private double startIconX;
    private double startIconY;
    private double goalIconX;
    private double goalIconY;
    private Vertex startPoint;
    private Vertex endPoint;
    private Set<Integer> generatedIds = new HashSet<>();
    private Graph graph;
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @FXML
    private void handleMapClick(MouseEvent event) {
        if (isStartPositionSelected || goalPositionRadioButton.isSelected()) {
            double mouseX = event.getX();
            double mouseY = event.getY();

            if (isStartPositionSelected) {
                setIconPosition(startHighlightIcon, mouseX, mouseY);
                startIconX = mouseX;
                startIconY = mouseY;
                startPoint = new Vertex(generateRandomId(), mouseX, mouseY);
                hideRoute();
            } else {
                setIconPosition(goalHighlightIcon, mouseX, mouseY);
                goalIconX = mouseX;
                goalIconY = mouseY;
                endPoint = new Vertex(generateRandomId(), mouseX, mouseY);
                hideRoute();
            }
        }
    }

    private int generateRandomId() {
        int newId;
        do {
            newId = 301 + (int) (Math.random() * 1000);
        } while (generatedIds.contains(newId));

        generatedIds.add(newId);
        return newId;
    }

    private void setIconPosition(ImageView icon, double x, double y) {
        double offsetX = icon.getBoundsInLocal().getWidth() / 2;
        double offsetY = icon.getBoundsInLocal().getHeight() / 2;
        icon.setLayoutX(x - offsetX);
        icon.setLayoutY(y - offsetY);
        icon.setVisible(true);

        // Set fit height and width
        icon.setFitHeight(29);
        icon.setFitWidth(29);
    }

    private void hideIcons() {
        startHighlightIcon.setVisible(false);
        goalHighlightIcon.setVisible(false);
    }

    @FXML
    private void handleShowRoute(ActionEvent event) {
        hideRoute();

        if (graph != null) {
            AStarAlgorithm aStarAlgorithm = new AStarAlgorithm(graph.getDistanceMatrix());
            List<Vertex> resultPair = findStartAndGoalPair(graph, startPoint, endPoint);
            List<Integer> pathIndexList = aStarAlgorithm.findShortestPath(resultPair.get(0).getId(), resultPair.get(1).getId());
            List<Vertex> shortestPath = new ArrayList<>();
            for (int a: pathIndexList) {
                shortestPath.add(graph.getVertexList().get(a));
            }
            drawRouteOnMap(shortestPath);
        }
    }

    private void drawRouteOnMap(List<Vertex> shortestPath) {
        if (shortestPath != null && shortestPath.size() > 1) {
            // Draw lines connecting the vertices in the shortestPath
            for (int i = 0; i < shortestPath.size() - 1; i++) {
                Vertex startVertex = shortestPath.get(i);
                Vertex endVertex = shortestPath.get(i + 1);

                Line routeLine = new Line(startVertex.getX(), startVertex.getY(), endVertex.getX(), endVertex.getY());

                routeLine.setStyle("-fx-stroke: #ff0000; -fx-stroke-width: 5;");

                // Add the line to your dotPane or another appropriate container
                dotPane.getChildren().add(routeLine);
            }
        }
    }

    @FXML
    private void handleHideRoute(ActionEvent event) {
        // Remove the route line
        hideRoute();
    }

    private void hideRoute() {
        dotPane.getChildren().removeIf(node -> node instanceof Line);
    }

    @FXML
    private void initialize() {
        startPositionRadioButton.setOnAction(event -> {
            isStartPositionSelected = true;
            goalPositionRadioButton.setSelected(false);
        });

        goalPositionRadioButton.setOnAction(event -> {
            isStartPositionSelected = false;
            startPositionRadioButton.setSelected(false);
        });

        deletePointButton.setOnAction(event -> {
            hideIcons();
            hideRoute();
        });

        Graph graph = new Graph();

        // Set the Graph instance for MapController
        setGraph(graph);

        showRouteButton.setOnAction(this::handleShowRoute);
        hideRouteButton.setOnAction(this::handleHideRoute);
    }
}
