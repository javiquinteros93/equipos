package com.futbol.equipos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
public class EquiposApplication {

	public static void main(String[] args) {
		SpringApplication.run(EquiposApplication.class, args);
	}

}
