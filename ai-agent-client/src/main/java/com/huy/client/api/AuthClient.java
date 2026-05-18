package com.huy.client.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.client.session.SessionManager;

import java.net.URI;
import java.net.http.*;

    public class AuthClient {

        private static final String BASE_URL = "http://localhost:8080";
        private static final HttpClient client = HttpClient.newHttpClient();

        public static String login(String email, String password) throws Exception {

            String json = """
            {
              "email": "%s",
              "password": "%s"
            }
        """.formatted(email, password);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + "/auth/login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readTree(response.body());

            String token = node.get("token").asText();

            SessionManager.setToken(token);

            return token;
        }
    }