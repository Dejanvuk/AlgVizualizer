package com.algorithmvisualizer.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.algorithmvisualizer.config.oauth2.CustomOAuth2UserService;
import com.algorithmvisualizer.config.oauth2.CustomOauth2AuthenticationFailHandler;
import com.algorithmvisualizer.config.oauth2.CustomOauth2AuthenticationSuccessHandler;
import com.algorithmvisualizer.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository;
import com.algorithmvisualizer.exception.ExceptionHandlerFilter;
import com.algorithmvisualizer.service.CustomUserDetailsService;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserDetailsService userDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Bean(name = BeanIds.AUTHENTICATION_MANAGER)
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Lazy
	@Autowired
	JwtAuthorizationFilter jwtAuthorizationFilter;

	@Lazy
	@Autowired
	JWTAuthenticationFilter jwtAuthenticationFilter;
	
	@Autowired
	HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;
	
	@Autowired
	CustomOAuth2UserService customOAuth2UserService;
	
	@Autowired
	CustomOauth2AuthenticationSuccessHandler customOauth2AuthenticationSuccessHandler;
	
	@Autowired
	CustomOauth2AuthenticationFailHandler customOauth2AuthenticationFailHandler;
	
	@Autowired
	ExceptionHandlerFilter exceptionHandlerFilter;
	
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization"));
	    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "OPTIONS", "DELETE", "PATCH"));
	    config.setExposedHeaders(Arrays.asList("Authorization"));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().httpBasic().disable().formLogin().disable().logout()
        .disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
						"/**/*.css", "/**/*.js")
				.permitAll().antMatchers("/auth/**", "/oauth2/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/user/verify-email").permitAll()
				.antMatchers(HttpMethod.GET, "/email-verification/**").permitAll()
				.antMatchers(HttpMethod.GET, "/api/user/reset-password/**").permitAll()
				.anyRequest().authenticated();

		http.oauth2Login().authorizationEndpoint().baseUri("/oauth2/authorize").authorizationRequestRepository(httpCookieOAuth2AuthorizationRequestRepository).and()
				.userInfoEndpoint().userService(customOAuth2UserService).and()
				.successHandler(customOauth2AuthenticationSuccessHandler)
				.failureHandler(customOauth2AuthenticationFailHandler);

		// Custom JWT based security filter
		http.addFilterBefore(exceptionHandlerFilter, JWTAuthenticationFilter.class);
		http.addFilter(jwtAuthenticationFilter);
		http.addFilter(jwtAuthorizationFilter);
	}
}
