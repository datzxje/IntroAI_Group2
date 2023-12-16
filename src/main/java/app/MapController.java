package app;

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

import java.util.List;

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
                hideRoute();
            } else {
                setIconPosition(goalHighlightIcon, mouseX, mouseY);
                goalIconX = mouseX;
                goalIconY = mouseY;
                hideRoute();
            }
        }
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
//        if (startIconX != 0 && startIconY != 0 && goalIconX != 0 && goalIconY != 0) {
//            // Draw a line connecting the start and goal points
//            Line routeLine = new Line(startIconX, startIconY, goalIconX, goalIconY);
//
//            // Add the line to your dotPane or another appropriate container
//            dotPane.getChildren().add(routeLine);
//        }

        if (graph != null) {
            List<Vertex> shortestPath = graph.getShortestPath();
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

                routeLine.setStyle("-fx-stroke: #ff0000; -fx-stroke-width: 4;");

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
