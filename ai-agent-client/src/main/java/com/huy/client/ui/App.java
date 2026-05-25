package com.huy.client.ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import com.huy.client.session.SessionManager;
import com.huy.client.session.TokenStorage;

public class App extends Application {
    private Stage primaryStage;

    @Override
    public void start(Stage stage) {

        this.primaryStage = stage;

        String token = TokenStorage.loadToken();

        if (token != null && !token.isBlank()) {

            SessionManager.setToken(token);

            showTaskDashboard();

        } else {

            showLoginScreen();
        }

        stage.setTitle("Task App");

        stage.show();
    }

    public void showLoginScreen() {
        // Truyền các hành động chuyển màn hình vào Login View
        Login login = new Login(
                () -> showTaskDashboard(),  // Đăng nhập đúng thì qua Dashboard
                () -> showRegisterScreen()  // Bấm nút đăng ký thì qua Register
        );
        Scene scene = new Scene(login.getView(), 400, 350);
        primaryStage.setScene(scene);
    }

    public void showRegisterScreen() {
        Register register = new Register(() -> showLoginScreen());
        Scene scene = new Scene(register.getView(), 400, 350);
        primaryStage.setScene(scene);
    }

    public void showTaskDashboard() {
        TaskDashboard dashboardView =
                new TaskDashboard(this::showLoginScreen);
        Scene scene = new Scene(dashboardView.getView(), 500, 400);
        primaryStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch();
    }
}