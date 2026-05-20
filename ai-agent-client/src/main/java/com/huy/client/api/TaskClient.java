package com.huy.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.client.model.Task;
import com.huy.client.session.SessionManager;
import static com.huy.client.config.ApiConfig.BASE_URL;

import java.net.URI;
import java.net.http.*;
import java.util.List;


public class TaskClient {

    private static final HttpClient client = HttpClient.newHttpClient();

    // Lấy danh sách task
    public static List<Task> getTasks() throws Exception {
        // Lấy token đã lưu sau khi login thành công
        String token = SessionManager.getToken();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tasks"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token) // Gửi kèm mã JWT
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Không thể lấy danh sách task: " + response.statusCode());
        }

        ObjectMapper mapper = new ObjectMapper();
        // Parse tạm ra List<Map> cho đơn giản, không cần tạo DTO cồng kềnh lúc học
        return mapper.readValue(
                response.body(),
                new TypeReference<List<Task>>() {}
        );
    }

    // Thêm Task mới
    public static void createTask(String title) throws Exception {
        // Tạo JSON request khớp với CreateTaskRequest ở Backend
        String json = "{\"title\": \"" + title + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tasks"))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 201) {
            throw new RuntimeException("Không thể tạo task. Mã lỗi: " + response.statusCode());
        }
    }

    // Sửa Task theo ID
    public static void updateTask(Long id, String newTitle) throws Exception {
        // Tạo JSON request khớp with UpdateTaskRequest ở Backend
        String json = "{\"title\": \"" + newTitle + "\"}";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tasks/" + id))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .PUT(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200) {
            throw new RuntimeException("Không thể cập nhật task. Mã lỗi: " + response.statusCode());
        }
    }

    // Xóa Task theo ID
    public static void deleteTask(Long id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tasks/" + id))
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() != 200 && response.statusCode() != 204) {
            throw new RuntimeException("Không thể xóa task. Mã lỗi: " + response.statusCode());
        }
    }

}