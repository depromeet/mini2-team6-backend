package com.deprommet.mini2.team6;

import com.deprommet.mini2.team6.model.Models;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@EnableJpaRepositories
@EntityScan(
	basePackageClasses = { Jsr310JpaConverters.class, Models.class }
)
@SpringBootApplication
public class Team6Application {
	public static void main(String[] args) {
		SpringApplication.run(Team6Application.class, args);
	}
}
