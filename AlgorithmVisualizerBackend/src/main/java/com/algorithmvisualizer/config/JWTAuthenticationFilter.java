package com.algorithmvisualizer.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.algorithmvisualizer.payload.LoginPayload;
import com.algorithmvisualizer.utility.JwtTokenUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	private JwtTokenUtility jwtTokenUtility;
	JwtProperties jwtProps;
	String AUTH_LOGIN_URL;
	String RESPONSE_HEADER;
	
	public JWTAuthenticationFilter(@Value("${app.AUTH_LOGIN_URL}") String AUTH_LOGIN_URL, @Value("${app.RESPONSE_HEADER}") String RESPONSE_HEADER,
			AuthenticationManager authenticationManager, JwtTokenUtility jwtTokenUtility, JwtProperties jwtProps) {
		this.AUTH_LOGIN_URL = AUTH_LOGIN_URL;
		this.RESPONSE_HEADER = RESPONSE_HEADER;
		this.authenticationManager = authenticationManager;
		this.jwtTokenUtility = jwtTokenUtility;
		this.jwtProps = jwtProps;
		setFilterProcessesUrl(AUTH_LOGIN_URL);
	}
	
	@Override
	@Autowired
	public void setAuthenticationManager(AuthenticationManager authenticationManager) {
	    super.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		if ("POST".equalsIgnoreCase(request.getMethod())) {
			try {
				LoginPayload loginPayload = new ObjectMapper().readValue(request.getInputStream(), LoginPayload.class);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						loginPayload.getUsernameOrEmail(), loginPayload.getPassword());
				return authenticationManager.authenticate(authenticationToken);
			} catch (IOException e) {
				System.out.println("Failed to read the response body!");
			}
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		// Generate JWT token and send it in the response header back
		response.addHeader(RESPONSE_HEADER, jwtProps.getTOKEN_PREFIX() + " " + jwtTokenUtility.generateToken(authResult));
	}
	
}
