package com.algorithmvisualizer.config.oauth2;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.algorithmvisualizer.utility.CookieUtility;

import java.util.Base64;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

@Component
public class HttpCookieOAuth2AuthorizationRequestRepository
		implements AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public static final String OAUTH2_AUTHORIZATION_COOKIE = "oauth2_authorization_cookie";
	public static final String REDIRECT_COOKIE = "redirect_uri";
	public static final int COOKIE_AGE = 120;
	
	@Autowired
	CookieUtility cookieUtility;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String oauth2CookieValue = cookieUtility.getCookieValue(request, cookies, OAUTH2_AUTHORIZATION_COOKIE);
			return OAuth2AuthorizationRequest.class
					.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(oauth2CookieValue)));

		}
		else return null;  // throw custom error
	}

	// persist the OAuth2AuthorizationRequest and the redirect uri in cookies
	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
			HttpServletResponse response) {
		if(authorizationRequest == null) {
			Cookie[] cookies = request.getCookies();
			if(cookies != null) {
				cookieUtility.deleteCookie(response, cookies, OAUTH2_AUTHORIZATION_COOKIE);
				cookieUtility.deleteCookie(response, cookies, REDIRECT_COOKIE);
			}
		}
		else {
			cookieUtility.addCookie(response, OAUTH2_AUTHORIZATION_COOKIE, Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(authorizationRequest)), "", COOKIE_AGE);
			cookieUtility.addCookie(response, REDIRECT_COOKIE, request.getParameter(REDIRECT_COOKIE), "", COOKIE_AGE);	        
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request) {
		return this.loadAuthorizationRequest(request);
	}

}
