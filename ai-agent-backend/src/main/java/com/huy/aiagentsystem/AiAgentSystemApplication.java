package com.huy.aiagentsystem;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class AiAgentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiAgentSystemApplication.class, args);
	}

	@Bean
	public CommandLineRunner debug(DataSource dataSource) {
		return args -> {
			System.out.println("==== DATASOURCE URL ====");
			System.out.println(dataSource.getConnection().getMetaData().getURL());
		};
	}
}