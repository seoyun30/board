package com.example.boardproj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BoardProjApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardProjApplication.class, args);
	}

}
