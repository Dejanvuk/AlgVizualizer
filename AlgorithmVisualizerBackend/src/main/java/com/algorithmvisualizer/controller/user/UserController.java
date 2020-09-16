package com.algorithmvisualizer.controller.user;

import java.util.Collections;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.algorithmvisualizer.config.CustomUserDetails;
import com.algorithmvisualizer.model.Role;
import com.algorithmvisualizer.model.RoleType;
import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.payload.SignUpPayload;
import com.algorithmvisualizer.repository.RoleRepository;
import com.algorithmvisualizer.response.ApiResponse;
import com.algorithmvisualizer.response.Status;
import com.algorithmvisualizer.service.AmazonSesService;
import com.algorithmvisualizer.service.BindingResultValidationService;
import com.algorithmvisualizer.service.UserService;
import com.algorithmvisualizer.utility.JwtTokenUtility;

import org.springframework.validation.BindingResult;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
    PasswordEncoder passwordEncoder;
	
	@Autowired
	BindingResultValidationService bindingResultValidationService;
	
	@Autowired
	JwtTokenUtility jwtTokenUtility;
	
	@Autowired
	AmazonSesService amazonSesService;
	
	@PostMapping("${app.AUTH_SIGN_UP_URL}")
	ResponseEntity<?> registerUser(@Valid @RequestBody SignUpPayload signUpPayload, BindingResult bindingResult, UriComponentsBuilder b) {
		
		ResponseEntity<?> errors = bindingResultValidationService.validateResult(bindingResult);
		if(errors != null) return errors;
		ResponseEntity<?> validationErrors = userService.checkForDuplicates(signUpPayload);
		if(validationErrors != null) return validationErrors;
		
		User user = new User();
		user.setUsername(signUpPayload.getUsername());
		user.setEmail(signUpPayload.getEmail());
		user.setName(signUpPayload.getName());
		user.setPassword(passwordEncoder.encode(signUpPayload.getPassword()));
		
		user.setVerified(false);
		user.setToken(jwtTokenUtility.generateTokenByEmail(signUpPayload.getEmail()));
		
		Role roles = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found!"));
		user.setRoles(Collections.singleton(roles));
		
		System.out.println(user);
		
		User result = userService.saveOrUpdateUser(user);
		
		// Send verification email
		amazonSesService.sendVerificationEmail(user);
		
		UriComponents location = b.path("/").buildAndExpand();
		return ResponseEntity.created(location.toUri()).body("User created succesfully!");
	}
	
	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	ResponseEntity<?> getCurrentLoggedInUser(@AuthenticationPrincipal CustomUserDetails currentUser) {
		//return new ResponseEntity<CustomUserDetails>(currentUser, HttpStatus.OK);
		User user = userService.findUserById(currentUser.getId());
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}
	
	@GetMapping("/api/user/verify-email")
	ApiResponse verifyEmail(@RequestParam(value = "token") String token){
		 ApiResponse apiResponse = new ApiResponse();
		 String status = jwtTokenUtility.validateToken(token);
		 apiResponse.setStatus(status);
		 System.out.println(status);
		 return apiResponse;
	}
	
	@GetMapping("/api/user/reset-password")
	ApiResponse resetPassword(@RequestParam(value = "usernameOrEmail") String usernameOrEmail) {
		ApiResponse apiResponse = new ApiResponse();
		User user = userService.findByUserNameOrEmail(usernameOrEmail);
		String temporaryPassword = generateRandomString(15);
		user.setPassword(passwordEncoder.encode(temporaryPassword));
		userService.saveOrUpdateUser(user);
		amazonSesService.sendResetPasswordEmail(user, temporaryPassword);
		apiResponse.setStatus(Status.SUCCESS.name());
		return apiResponse;
	}
	
	public String generateRandomString(int n) 
    { 
  
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz"; 
  
        StringBuilder sb = new StringBuilder(n); 
  
        for (int i = 0; i < n; i++) { 
            int index = (int)(AlphaNumericString.length() * Math.random()); 
            sb.append(AlphaNumericString .charAt(index)); 
        } 
  
        return sb.toString(); 
    } 
}
