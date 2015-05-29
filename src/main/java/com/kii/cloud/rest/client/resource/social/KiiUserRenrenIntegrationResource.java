package com.kii.cloud.rest.client.resource.social;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.social.KiiSocialProvider;
import com.kii.cloud.rest.client.resource.storage.KiiUserResource;
import com.kii.cloud.rest.client.util.StringUtils;
import com.squareup.okhttp.MediaType;

/**
 * Represents the integrate RenRen with Kii user resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users/{USER_IDENTIFIER}/renren
 * </ul>
 */
public class KiiUserRenrenIntegrationResource extends KiiUserSocialIntegrationResource {
	
	public static final String BASE_PATH = "/renren";
	
	public KiiUserRenrenIntegrationResource(KiiUserResource parent) {
		super(parent);
	}
	public void link(String accessToken) throws KiiRestException {
		if (StringUtils.isEmpty(accessToken)) {
			throw new IllegalArgumentException("accessToken is null or empty");
		}
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("accessToken", accessToken);
		super.link(MediaType.parse(KiiSocialProvider.RENREN.getLinkRequestContentType()), requestBody);
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
