package com.huy.client.session;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class TokenStorage {

    private static final Path TOKEN_PATH =
            Path.of("session.token");

    public static void saveToken(String token) {

        try {
            Files.writeString(TOKEN_PATH, token);

        } catch (IOException e) {
            throw new RuntimeException("Cannot save token", e);
        }
    }

    public static String loadToken() {

        try {

            if (!Files.exists(TOKEN_PATH)) {
                return null;
            }

            return Files.readString(TOKEN_PATH);

        } catch (IOException e) {
            throw new RuntimeException("Cannot load token", e);
        }
    }

    public static void clearToken() {

        try {
            Files.deleteIfExists(TOKEN_PATH);

        } catch (IOException e) {
            throw new RuntimeException("Cannot clear token", e);
        }
    }
}