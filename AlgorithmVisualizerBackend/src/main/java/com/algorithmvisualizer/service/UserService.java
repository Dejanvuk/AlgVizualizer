package com.algorithmvisualizer.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.payload.SignUpPayload;
import com.algorithmvisualizer.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public User saveOrUpdateUser(User user) {
		try {
			return userRepository.save(user);
		}
		catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to register the user!", e);
		}
	}
	
	@Transactional
	public ResponseEntity<?> checkForDuplicates(SignUpPayload signUpPayload) {
		List<String> duplicatesList= new ArrayList<String>();
		if(userRepository.existsByUsername(signUpPayload.getUsername())) {
			duplicatesList.add("Username is already taken!"); 
		}
		if(userRepository.existsByEmail(signUpPayload.getEmail())) {
			duplicatesList.add("Email is already taken!");
		}
		return (!duplicatesList.isEmpty()) ? new ResponseEntity<List<String>>(duplicatesList, HttpStatus.UNPROCESSABLE_ENTITY) : null;
	}
	
	@Transactional
	public User findUserById(Long id){
		return userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User with id: " + id + " not found!"));
	}
	
	@Transactional
	public User findByUserNameOrEmail(String usernameOrEmail) {
		return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail).orElseThrow(() -> new UsernameNotFoundException("User not found!"));
	}
	
}
