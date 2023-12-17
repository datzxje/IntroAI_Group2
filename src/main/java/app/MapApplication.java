package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MapApplication extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MapApplication.fxml"));
        Parent root = loader.load();

        // Set up the JavaFX stage
        Scene scene = new Scene(root);

        // Set the controller for the FXML file
        MapController controller = loader.getController();

        primaryStage.setTitle("Map Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
