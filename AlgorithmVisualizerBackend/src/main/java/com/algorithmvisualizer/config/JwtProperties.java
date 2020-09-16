package com.algorithmvisualizer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Primary
@Configuration
@PropertySource("classpath:jwtprops.properties")
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {
	private String JWT_SECRET;
	private String TOKEN_HEADER;
	private String TOKEN_PREFIX;
	private String TOKEN_TYPE;
	private Long EXPIRATION_TIME;
	private String TOKEN_ISSUER;
	private String TOKEN_AUDIENCE;
}
