package com.algorithmvisualizer.config.oauth2;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.algorithmvisualizer.config.CustomUserDetails;
import com.algorithmvisualizer.model.Role;
import com.algorithmvisualizer.model.RoleType;
import com.algorithmvisualizer.model.User;
import com.algorithmvisualizer.repository.RoleRepository;
import com.algorithmvisualizer.repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		OAuth2User oAuth2User = super.loadUser(userRequest);
		
		Map<String, Object> userAttributes = oAuth2User.getAttributes();
		Optional<User> userOptional = userRepository.findByEmail((String)userAttributes.get("email"));
		if(userOptional.isPresent()) { //Throw mail already registered and make the user log in with the email Or 

			// Return the OAuth2User 
			User existentUser = userOptional.get();
			Set<GrantedAuthority> roles = existentUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
			return new CustomUserDetails(existentUser.getId(),existentUser.getName(), existentUser.getUsername(), existentUser.getEmail(), existentUser.getPassword(),existentUser.getVerified(), roles, userAttributes);
		}
		else {
			//Register new user
			User user = new User();
			OAuth2UserAttributes oAuth2UserAttributes = getOAuth2UserAttributesByProvider(userRequest, userAttributes);
			validateOAuth2UserAttributes(oAuth2UserAttributes);
			user.setName(oAuth2UserAttributes.getName());
			// use email but make the user input the username
			user.setUsername(oAuth2UserAttributes.getEmail());
			user.setEmail(oAuth2UserAttributes.getEmail());
			user.setImageUrl(oAuth2UserAttributes.getPictureUrl());
			user.setProviderType(userRequest.getClientRegistration().getRegistrationId());
			
			user.setToken(null);
			user.setVerified(true);
			
			Role roles = roleRepository.findByName(RoleType.ROLE_USER).orElseThrow(() -> new RuntimeException("Role not found!"));
			user.setRoles(Collections.singleton(roles));
			
			User savedUser = userRepository.save(user);
			Set<GrantedAuthority> savedRoles = savedUser.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toSet());
			return new CustomUserDetails(savedUser.getId(),savedUser.getName(),savedUser.getUsername(),savedUser.getEmail(),savedUser.getPassword(),true, savedRoles, userAttributes);
		}
	}
	
	private static OAuth2UserAttributes getOAuth2UserAttributesByProvider(OAuth2UserRequest userRequest, Map<String, Object> userAttributes) {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		switch(registrationId) {
			case "google": 
				return new GoogleOAuth2UserAttribute(userAttributes);
			case "facebook":
				return new FacebookOAuth2UserAttribute(userAttributes);
			case "github":
				return new GithubOAuth2UserAttribute(userAttributes);
			default:
				throw new RuntimeException("Provider: " + registrationId + " is not supported yet!");
		}
	}
	
	private static void validateOAuth2UserAttributes(OAuth2UserAttributes oAuth2UserAttributes) {
		if(oAuth2UserAttributes.getEmail() == null) throw new BadCredentialsException("Github Email is private,please add a public email or Sign up using a different provider!");
	}
}
