package com.algorithmvisualizer.utility;

import java.util.Arrays;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CookieUtility {
		public String getCookieValue(HttpServletRequest request, Cookie[] cookies, String cookieName) {
			return Arrays.stream(cookies)
			.filter(c -> c.getName().equals(cookieName)).findFirst().map(Cookie::getValue)
			.orElse(null);
		}
		
		public void deleteCookie(HttpServletResponse response, Cookie[] cookies, String cookieName) {
			for(Cookie cookie: cookies) {
				var currentCookieName = cookie.getName();
				if(currentCookieName.equals(cookieName)) {
					cookie.setValue("");
					cookie.setPath("/");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		
		public void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String path, int age) {
			Cookie cookie = new Cookie(cookieName, cookieValue);
			cookie.setPath("/");
			cookie.setHttpOnly(true);
			cookie.setMaxAge(120);
	        response.addCookie(cookie);
		}
}
