package com.algorithmvisualizer.payload;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginPayload {
	@NotBlank(message = "Username or Email cannot be empty!")
	private String usernameOrEmail;
	@NotBlank(message = "Password cannot be empty!")
	private String password;
}
