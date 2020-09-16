package com.algorithmvisualizer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algorithmvisualizer.config.CustomUserDetails;
import com.algorithmvisualizer.exception.FailedToAuthenticateException;
import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.repository.UserRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository userRepository;
		
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException, FailedToAuthenticateException {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
				.orElseThrow(() -> new UsernameNotFoundException("Username or email dont exist! " + usernameOrEmail));
		// authorities are not necessary for authentification, might aswell pass an emptyList();
		
		Set<GrantedAuthority> authorities = user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
		return new CustomUserDetails(user.getId(),user.getName(), user.getUsername(), user.getEmail(), user.getPassword(),user.getVerified(), authorities, null);	
	}
}
