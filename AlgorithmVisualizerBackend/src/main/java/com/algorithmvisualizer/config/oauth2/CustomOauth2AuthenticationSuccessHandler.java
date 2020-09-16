package com.algorithmvisualizer.config.oauth2;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.algorithmvisualizer.config.JwtProperties;
import com.algorithmvisualizer.utility.CookieUtility;
import com.algorithmvisualizer.utility.JwtTokenUtility;

import static com.algorithmvisualizer.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.REDIRECT_COOKIE;
import static com.algorithmvisualizer.config.oauth2.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_AUTHORIZATION_COOKIE;

@Component
public class CustomOauth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	
	@Autowired
	private JwtTokenUtility jwtTokenUtility;
	
	@Autowired
	CookieUtility cookieUtility;
	
	@Autowired
	JwtProperties jwtProps;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//Redirect the user with the jwt token inside
		String redirectUri = cookieUtility.getCookieValue(request, request.getCookies(), REDIRECT_COOKIE);
		String jwt = jwtProps.getTOKEN_PREFIX() + " " + jwtTokenUtility.generateToken(authentication);
		 if (response.isCommitted()) {
	            logger.debug("Response has already been committed. Unable to redirect to " + redirectUri);
	            return;
	        }
		String location = UriComponentsBuilder.fromUriString(redirectUri).queryParam("jwt", jwt).build().toUriString();
		// authentication attributes and cookies are no longer needed
		super.clearAuthenticationAttributes(request);
		cookieUtility.deleteCookie(response, request.getCookies(), REDIRECT_COOKIE);
		cookieUtility.deleteCookie(response, request.getCookies(), OAUTH2_AUTHORIZATION_COOKIE);
		getRedirectStrategy().sendRedirect(request, response, location);
	}
}
