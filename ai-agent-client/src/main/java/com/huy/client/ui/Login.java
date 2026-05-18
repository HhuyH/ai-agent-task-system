package com.huy.client.ui;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class Login {

    public VBox getView() {

        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");

        Label status = new Label();

        loginBtn.setOnAction(e -> {
            try {
                String token = com.huy.client.api.AuthClient.login(
                        emailField.getText(),
                        passwordField.getText()
                );

                status.setText("Login success");

                // sau này chuyển sang Task screen
            } catch (Exception ex) {
                status.setText("Login failed: " + ex.getMessage());
            }
        });

        VBox box = new VBox(10, emailField, passwordField, loginBtn, status);
        box.setPadding(new Insets(20));

        return box;
    }
}