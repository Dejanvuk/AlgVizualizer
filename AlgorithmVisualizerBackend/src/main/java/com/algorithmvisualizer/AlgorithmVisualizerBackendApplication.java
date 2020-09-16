package com.algorithmvisualizer;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.algorithmvisualizer.config.JwtProperties;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
		AlgorithmVisualizerBackendApplication.class,
		Jsr310JpaConverters.class 
})
@EnableConfigurationProperties(JwtProperties.class)
public class AlgorithmVisualizerBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlgorithmVisualizerBackendApplication.class, args);
	}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
