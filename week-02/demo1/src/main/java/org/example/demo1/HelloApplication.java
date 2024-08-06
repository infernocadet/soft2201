package org.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;


public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        Canvas canvas = new Canvas(600, 400);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokeLine(34, 45, 423, 333);
        gc.fillRect(342, 234, 124, 65);
        gc.setFill(Color.AQUA);
        gc.fillOval(32, 56, 320, 242);
        root.getChildren().add(canvas);

        primaryStage.setTitle("Hello JavaFX");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}