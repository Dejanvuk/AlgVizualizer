package com.algorithmvisualizer.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.algorithmvisualizer.utility.JwtTokenUtility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.util.StringUtils;

@Component
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);
	
	private JwtTokenUtility jwtTokenUtility;
	
	@Value("${app.RESPONSE_HEADER}")
	String RESPONSE_HEADER;
	
	private JwtProperties jwtProps;
	
	public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenUtility jwtTokenUtility, JwtProperties jwtProps) {
		super(authenticationManager);
		this.jwtTokenUtility = jwtTokenUtility;
		this.jwtProps = jwtProps;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		String headerBody = request.getHeader(RESPONSE_HEADER);
		
		if(StringUtils.isEmpty(headerBody) || !(headerBody.startsWith(jwtProps.getTOKEN_PREFIX()))) {
			chain.doFilter(request, response);
			return;
		}
		
		SecurityContextHolder.getContext().setAuthentication(jwtTokenUtility.getUsernamePasswordAuthenticationTokenFromJwt(headerBody));
		
		chain.doFilter(request, response);
	}
}
