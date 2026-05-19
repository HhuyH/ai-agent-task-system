package com.huy.client.ui;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class Login {
    // Thêm 2 callback để báo cho App biết khi nào cần đổi màn hình
    private final Runnable onLoginSuccess;
    private final Runnable onGoToRegister;

    public Login(Runnable onLoginSuccess, Runnable onGoToRegister) {
        this.onLoginSuccess = onLoginSuccess;
        this.onGoToRegister = onGoToRegister;
    }

    public VBox getView() {
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Button goToRegisterBtn = new Button("Chưa có tài khoản? Đăng ký");
        Label status = new Label();

        loginBtn.setOnAction(e -> {
            try {
                com.huy.client.api.AuthClient.login(emailField.getText(), passwordField.getText());
                status.setText("Login success");

                // Gọi callback chuyển sang màn hình Task
                onLoginSuccess.run();
            } catch (Exception ex) {
                ex.printStackTrace();
                status.setText("Login failed: " + ex.getMessage());
            }
        });

        goToRegisterBtn.setOnAction(e -> onGoToRegister.run());

        VBox box = new VBox(10, emailField, passwordField, loginBtn, goToRegisterBtn, status);
        box.setPadding(new Insets(20));
        return box;
    }
}