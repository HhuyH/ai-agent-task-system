package com.huy.client.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huy.client.model.Task;
import com.huy.client.session.SessionManager;

import java.net.URI;
import java.net.http.*;
import java.util.List;

public class TaskClient {

    private static final String BASE_URL = "http://localhost:8080";
    private static final HttpClient client = HttpClient.newHttpClient();

    public static List<Task> getTasks() throws Exception {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/tasks"))
                .header("Authorization", "Bearer " + SessionManager.getToken())
                .GET()
                .build();


        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(
                response.body(),
                new TypeReference<List<Task>>() {}
        );
    }
}