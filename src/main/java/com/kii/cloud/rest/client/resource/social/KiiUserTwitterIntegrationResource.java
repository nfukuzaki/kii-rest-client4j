package com.kii.cloud.rest.client.resource.social;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;

/**
 * Represents the integrate Twitter with Kii user resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/twitter
 * </ul>
 */
public class KiiUserTwitterIntegrationResource extends KiiUserSocialIntegrationResource {
	
	public static final String BASE_PATH = "/twitter";
	
	public KiiUserTwitterIntegrationResource(KiiUserResource parent) {
		super(parent);
	}
	public void link(String accessToken, String accessTokenSecret) throws KiiRestException {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		if (StringUtils.isEmpty(accessTokenSecret)) {
			throw new IllegalArgumentException("accessTokenSecret is null or empty");
		}
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("accessToken", accessToken);
		requestBody.addProperty("accessTokenSecret", accessTokenSecret);
		super.link(MediaType.parse(KiiSocialProvider.TWITTER.getLinkRequestContentType()), requestBody);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
