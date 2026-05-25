package com.huy.client.api;

import java.net.http.HttpClient;
import java.time.Duration;

public class HttpClientProvider {

    private static final HttpClient CLIENT =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(5))
                    .build();

    public static HttpClient getClient() {
        return CLIENT;
    }
}