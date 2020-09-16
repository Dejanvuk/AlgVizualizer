package com.algorithmvisualizer.config.oauth2;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class GoogleOAuth2UserAttribute implements OAuth2UserAttributes {
	
	private Map<String, Object> userAttributes;

	@Override
	public String getId() {
		return (String) userAttributes.get("sub");
	}

	@Override
	public String getName() {
		return (String) userAttributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String) userAttributes.get("email");
	}

	@Override
	public String getPictureUrl() {
		return (String) userAttributes.get("picture");
	}
}
