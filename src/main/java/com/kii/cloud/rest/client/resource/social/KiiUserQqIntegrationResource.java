package com.kii.cloud.rest.client.resource.social;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;

/**
 * Represents the integrate QQ with Kii user resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/qq
 * </ul>
 */
public class KiiUserQqIntegrationResource extends KiiUserSocialIntegrationResource {
	
	public static final String BASE_PATH = "/qq";
	
	public KiiUserQqIntegrationResource(KiiUserResource parent) {
		super(parent);
	}
	public void link(String accessToken, String openID) throws KiiRestException {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		if (StringUtils.isEmpty(openID)) {
			throw new IllegalArgumentException("openID is null or empty");
		}
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("accessToken", accessToken);
		requestBody.addProperty("openID", openID);
		super.link(MediaType.parse(KiiSocialProvider.QQ.getLinkRequestContentType()), requestBody);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
