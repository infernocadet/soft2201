package org.example.demo1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;


public class HelloFx extends Application {

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
    }
    public static void main(String[] args){
        launch(args);
    }
}
