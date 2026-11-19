package co.edu.uniquindio.cinemauq;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;

public class App extends Application {
    @Override
    public void start(Stage stage) {
        Label etiqueta = new Label("CinemaUQ - proyecto inicializado correctamente");
        stage.setScene(new Scene(new StackPane(), 640, 360));
        stage.setTitle("CinemaUQ");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
