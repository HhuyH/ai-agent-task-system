package com.huy.client.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;


public class App extends Application {

    @Override
    public void start(Stage stage) {

        Login login = new Login();

        Scene scene = new Scene(login.getView(), 400, 300);

        stage.setTitle("Task App - Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}