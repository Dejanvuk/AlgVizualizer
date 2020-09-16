package com.algorithmvisualizer.config.oauth2;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class FacebookOAuth2UserAttribute implements OAuth2UserAttributes{
	private Map<String, Object> userAttributes;

	@Override
	public String getId() {
		return (String) userAttributes.get("id");
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
		if(userAttributes.containsKey("picture")) {
            Map<String, Object> pictureObj = (Map<String, Object>) userAttributes.get("picture");
            if(pictureObj.containsKey("data")) {
                Map<String, Object>  dataObj = (Map<String, Object>) pictureObj.get("data");
                if(dataObj.containsKey("url")) {
                    return (String) dataObj.get("url");
                }
            }
        }
        return null;
	}
}
