package com.huy.client.ui;

import com.huy.client.api.TaskClient;
import com.huy.client.model.Task;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.*;
import javafx.geometry.Insets;
import java.util.List;
import com.huy.client.session.SessionManager;
import com.huy.client.session.TokenStorage;
import com.huy.client.exception.UnauthorizedException;

public class TaskDashboard {

    private final Runnable onLogout;

    private ListView<Task> taskListView;
    private TextField titleField;
    private Label status;

    public TaskDashboard(Runnable onLogout) {
        this.onLogout = onLogout;
    }

    public VBox getView() {
        Label titleLabel = new Label("QUẢN LÝ TASK CỦA BẠN");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        // Sử dụng ListView chứa trực tiếp object Task
        taskListView = new ListView<>();

        // Custom cách hiển thị chữ của object Task trong ListView
        taskListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Task item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("ID: %s | %s [%s]", item.getId(), item.getTitle(), item.getDescription()));
                }
            }
        });


        // Ô nhập tên Task (dùng cho cả Thêm và Sửa)
        titleField = new TextField();
        titleField.setPromptText("Nhập tiêu đề task tại đây...");

        // Lắng nghe sự kiện click chuột vào danh sách để đổ ngược title lên ô nhập
        taskListView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                titleField.setText(newSelection.getTitle());
            }
        });

        // Các nút chức năng
        Button addBtn = new Button("Add Task");
        Button updateBtn = new Button("Update Task");
        Button deleteBtn = new Button("Delete Task");
        Button logoutBtn = new Button("Logout");
        status = new Label();

        // Xếp các nút nằm ngang
        HBox actionsBox = new HBox(10, addBtn, updateBtn, deleteBtn, logoutBtn);

        // --- XỬ LÝ SỰ KIỆN CHO CÁC NÚT BẤM ---

        // Nút Thêm
        addBtn.setOnAction(e -> {
            String txt = titleField.getText().trim();
            if (txt.isEmpty()) {
                showError("Vui lòng nhập tiêu đề!");
                return;
            }
            try {
                TaskClient.createTask(txt);
                showSuccess("Thêm task thành công!");
                refreshTaskList();
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Lỗi thêm: " + ex.getMessage());
            }
        });

        // Nút Sửa
        updateBtn.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask == null) {
                showError("Hãy chọn 1 task trong danh sách để sửa!");
                return;
            }
            String txt = titleField.getText().trim();
            if (txt.isEmpty()) {
                showError("Tiêu đề không được để trống!");
                return;
            }
            try {
                TaskClient.updateTask(selectedTask.getId(), txt);
                showSuccess("Cập nhật task thành công!");
                refreshTaskList();
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Lỗi sửa: " + ex.getMessage());
            }
        });

        // Nút Xóa
        deleteBtn.setOnAction(e -> {
            Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
            if (selectedTask == null) {
                showError("Hãy chọn 1 task để xóa!");
                return;
            }
            try {
                TaskClient.deleteTask(selectedTask.getId());
                showSuccess("Xóa task thành công!");
                refreshTaskList();
            } catch (Exception ex) {
                ex.printStackTrace();
                showError("Lỗi xóa: " + ex.getMessage());
            }
        });

        // Nút thoát tài khoản
        logoutBtn.setOnAction(e -> {

            SessionManager.clear();

            TokenStorage.clearToken();

            onLogout.run();
        });

        // Tải dữ liệu lần đầu tiên khi mở màn hình
        refreshTaskList();

        VBox box = new VBox(10, titleLabel, taskListView, titleField, actionsBox, status);
        box.setPadding(new Insets(20));
        return box;
    }

    // Hàm load lại danh sách đổ vào ListView
    private void refreshTaskList() {
        taskListView.getItems().clear();
        titleField.clear();
        try {
            List<Task> tasks = TaskClient.getTasks();
            if (tasks != null) {
                taskListView.getItems().addAll(tasks);
            }
        } catch (Exception ex) {

            ex.printStackTrace();

            if (ex instanceof UnauthorizedException) {

                SessionManager.clear();

                TokenStorage.clearToken();

                showError("Phiên đăng nhập đã hết hạn!");

                onLogout.run();

                return;
            }

            showError(
                    UiErrorHandler.getMessage(ex)
            );
        }
    }

    private void showSuccess(String message) {
        status.setStyle("-fx-text-fill: green;");
        status.setText(message);
    }

    private void showError(String message) {
        status.setStyle("-fx-text-fill: red;");
        status.setText(message);
    }
}