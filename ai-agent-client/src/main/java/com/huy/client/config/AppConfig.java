package com.huy.client.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AppConfig {

    private static final Properties properties = new Properties();

    static {

        try {

            Path externalPath = Paths.get("config/app.properties");

            if (Files.exists(externalPath)) {

                try (InputStream input = Files.newInputStream(externalPath)) {

                    properties.load(input);

                    System.out.println("Loaded external config");

                }

            } else {

                try (InputStream input =
                             AppConfig.class
                                     .getClassLoader()
                                     .getResourceAsStream("app.properties")) {

                    if (input == null) {
                        throw new RuntimeException("app.properties not found");
                    }

                    properties.load(input);

                    System.out.println("Loaded internal config");
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Cannot load app.properties", e);
        }
    }

    public static String getApiBaseUrl() {
        return properties.getProperty("api.base-url");
    }
}