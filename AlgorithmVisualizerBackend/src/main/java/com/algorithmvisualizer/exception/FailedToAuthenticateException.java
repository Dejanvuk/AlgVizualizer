package com.algorithmvisualizer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class FailedToAuthenticateException extends RuntimeException{
	public FailedToAuthenticateException(String message) {
		super(message);
	}
}
