package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiAdminCredentials;
import com.kii.cloud.model.KiiCredentialsContainer;
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
			request.addProperty("expiresAt", expiresAt);
		}
		JsonObject response = this.executePost(headers, MEDIA_TYPE_APPLICATION_JSON, request);
		return new KiiUserCredentials(response);
	}
	public KiiAdminCredentials getAdminAccessToken(String clientID, String clientSecret) throws KiiRestException {
		return this.getAdminAccessToken(clientID, clientSecret, null);
	}
	public KiiAdminCredentials getAdminAccessToken(String clientID, String clientSecret, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("grant_type", "client_credentials");
		request.addProperty("client_id", clientID);
		request.addProperty("client_secret", clientSecret);
		if (expiresAt != null) {
			request.addProperty("expiresAt", expiresAt);
		}
		JsonObject response = this.executePost(headers, MEDIA_TYPE_APPLICATION_JSON, request);
		return new KiiAdminCredentials(response);
	}
	public KiiCredentialsContainer refreshAccessToken(KiiCredentialsContainer credentials) throws KiiRestException {
		return refreshAccessToken(credentials, null);
	}
	public KiiCredentialsContainer refreshAccessToken(KiiCredentialsContainer credentials, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject request = new JsonObject();
		request.addProperty("grant_type", "refresh_token");
		request.addProperty("refresh_token", credentials.getRefreshToken());
		if (expiresAt != null) {
			request.addProperty("expires_at", expiresAt);
		}
		JsonObject response = this.executePost(headers, MEDIA_TYPE_APPLICATION_JSON, request);
		if (credentials.isAdmin()) {
			return new KiiAdminCredentials(response);
		} else {
			return new KiiUserCredentials(response);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}

}
