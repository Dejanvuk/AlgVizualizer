package com.algorithmvisualizer.config.oauth2;

import static com.algorithmvisualizer.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_COOKIE;
import static com.algorithmvisualizer.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_COOKIE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.algorithmvisualizer.utility.CookieUtility;

@Component
public class CustomOauth2AuthenticationFailHandler extends SimpleUrlAuthenticationFailureHandler {
	
	@Autowired
	CookieUtility cookieUtility;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		String redirectUri = cookieUtility.getCookieValue(request, request.getCookies(), REDIRECT_COOKIE);
		cookieUtility.deleteCookie(response, request.getCookies(), REDIRECT_COOKIE);
		cookieUtility.deleteCookie(response, request.getCookies(), OAUTH2_AUTHORIZATION_COOKIE);
		String location = UriComponentsBuilder.fromUriString(redirectUri).queryParam("error", exception.getLocalizedMessage()).build().toUriString();
		getRedirectStrategy().sendRedirect(request, response, location);
	}
}
