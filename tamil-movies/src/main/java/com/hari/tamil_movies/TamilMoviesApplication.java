package com.hari.tamil_movies;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

//@EnableScheduling
@SpringBootApplication
//@EnableCaching
public class TamilMoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(TamilMoviesApplication.class, args);
	}

}
