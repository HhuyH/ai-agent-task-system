package com.huy.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.client.session.SessionManager;

import java.net.URI;
import java.net.http.*;
import java.util.Map;

public class AuthClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String login(String email, String password) throws Exception {
        Map<String, String> credentials = Map.of(
                "email", email,
                "password", password
        );
        String json = mapper.writeValueAsString(credentials);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/login"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            System.out.println("Lỗi từ Server: " + response.body());
            throw new RuntimeException("Sai email hoặc mật khẩu!");
        }

        JsonNode node = mapper.readTree(response.body());
        String token = node.get("token").asText();
        SessionManager.setToken(token);

        return token;
    }

    public static void register(String email, String password) throws Exception {
        Map<String, String> credentials = Map.of(
                "email", email,
                "password", password
        );
        String json = mapper.writeValueAsString(credentials);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/auth/register"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200 && response.statusCode() != 201) {
            System.out.println("Register Error Body: " + response.body());

            try {
                JsonNode errorNode = mapper.readTree(response.body());
                if (errorNode.has("email")) {
                    throw new RuntimeException(errorNode.get("email").asText());
                } else if (errorNode.has("password")) {
                    throw new RuntimeException(errorNode.get("password").asText());
                }
            } catch (Exception e) {
                if (e instanceof RuntimeException) throw e;
            }
            throw new RuntimeException("Đăng ký thất bại! Vui lòng kiểm tra dữ liệu đầu vào.");
        }
    }
}