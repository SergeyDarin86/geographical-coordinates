package ru.darin.coordinates;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CoordinatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoordinatesApplication.class, args);
	}

}
