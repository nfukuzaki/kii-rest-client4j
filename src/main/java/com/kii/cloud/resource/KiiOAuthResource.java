package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiUserCredentials;

public class KiiOAuthResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/oauth2/token";
	public KiiOAuthResource(KiiRestResource parent) {
		super(parent);
	}
	public KiiUserCredentials getAccessToken(String identifier, String password) throws KiiRestException {
		return this.getAccessToken(identifier, password, null);
	}
	public KiiUserCredentials getAccessToken(String identifier, String password, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		
		JsonObject request = new JsonObject();
		request.addProperty("grant_type", "password");
		request.addProperty("username", identifier);
		request.addProperty("password", password);
		if (expiresAt != null) {
			request.addProperty("expires_at", expiresAt);
		}
		JsonObject response = this.executePost(headers, MEDIA_TYPE_APPLICATION_JSON, request);
		return new KiiUserCredentials(response);
	}
	public void refreshAccessToken() throws KiiRestException {
		
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
