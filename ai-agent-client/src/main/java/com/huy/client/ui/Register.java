package com.huy.client.ui;

import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;

public class Register {
    private final Runnable onBackToLogin;

    public Register(Runnable onBackToLogin) {
        this.onBackToLogin = onBackToLogin;
    }

    public VBox getView() {
        Label title = new Label("ĐĂNG KÝ TÀI KHOẢN");

        // Định nghĩa rõ ràng, gói gọn trong hàm getView
        TextField emailField = new TextField();
        emailField.setPromptText("Email");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button registerBtn = new Button("Register");
        Button backBtn = new Button("Back to Login");
        Label status = new Label();

        registerBtn.setOnAction(e -> {
            // Lấy trực tiếp dữ liệu tại thời điểm click chuột
            String email = emailField.getText() != null ? emailField.getText().trim() : "";
            String password = passwordField.getText() != null ? passwordField.getText().trim() : "";

            if (email.isEmpty() || password.isEmpty()) {
                status.setStyle("-fx-text-fill: red;");
                status.setText("Lỗi: Không được để trống tài khoản/mật khẩu!");
                return;
            }

            try {
                // Đẩy sang AuthClient xử lý JSON Map chuẩn
                com.huy.client.api.AuthClient.register(email, password);
                status.setStyle("-fx-text-fill: green;");
                status.setText("Đăng ký thành công! Hãy quay lại Login.");
            } catch (Exception ex) {

                status.setStyle("-fx-text-fill: red;");

                status.setText(
                        UiErrorHandler.getMessage(ex)
                );
            }
        });

        backBtn.setOnAction(e -> onBackToLogin.run());

        VBox box = new VBox(10, title, emailField, passwordField, registerBtn, backBtn, status);
        box.setPadding(new Insets(20));
        return box;
    }
}