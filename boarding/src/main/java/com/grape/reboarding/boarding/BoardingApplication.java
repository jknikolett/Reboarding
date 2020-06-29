package com.grape.reboarding.boarding;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.web.client.RestTemplate;

import java.awt.geom.Rectangle2D;

@EnableDiscoveryClient
@SpringBootApplication
public class BoardingApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoardingApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	@Bean
	public Jackson2ObjectMapperBuilder objectMapperBuilder() {
		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
		builder.serializationInclusion(JsonInclude.Include.NON_NULL);
		builder.mixIn(Rectangle2D.class, Rectangle2DJsonIgnore.class);
		return builder;
	}

	public static interface Rectangle2DJsonIgnore {
		@JsonIgnore
		String getBounds2D();
	}
}
