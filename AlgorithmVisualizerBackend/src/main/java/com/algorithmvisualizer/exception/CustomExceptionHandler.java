package com.algorithmvisualizer.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomExceptionHandler extends ResponseEntityExceptionHandler{
	@ExceptionHandler(FailedToAuthenticateException.class)
	public ResponseEntity<Object> handleFailedToAuthenticateException(HttpServletRequest request,
            Exception ex) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value= {AuthenticationException.class})
	public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex, WebRequest req) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value= {UsernameNotFoundException.class})
	public ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex, WebRequest req) {
		System.out.println(ex.getMessage());
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST); 
	}
	
	@ExceptionHandler(value= {Exception.class})
	public ResponseEntity<Object> handleOtherExceptions(Exception ex, WebRequest req) {
		return new ResponseEntity<Object>(ex.getMessage(), HttpStatus.BAD_REQUEST); 
	}
}
