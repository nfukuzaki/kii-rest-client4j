package com.kii.cloud.rest.client.resource;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.annotation.AnonymousAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiCredentialsContainer;
import com.kii.cloud.rest.client.model.KiiCredentials;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.Response;

/**
 * Represents the oauth resource like following URI:
 * 
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/oauth2/token
 * </ul>
 * <p>
 * Basically This resource is same as the "https://hostname/api/oauth2/token"
 * 
 */
public class KiiOAuthResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/oauth2/token";
	public KiiOAuthResource(KiiRestResource parent) {
		super(parent);
	}
	/**
	 * @param identifier
	 * @param password
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials getAccessToken(String identifier, String password) throws KiiRestException {
		return this.getAccessToken(identifier, password, null);
	}
	/**
	 * @param identifier
	 * @param password
	 * @param expiresAt
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials getAccessToken(String identifier, String password, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("grant_type", "password");
		requestBody.addProperty("username", identifier);
		requestBody.addProperty("password", password);
		if (expiresAt != null) {
			requestBody.addProperty("expiresAt", expiresAt);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_APPLICATION_JSON, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiCredentials(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param clientID
	 * @param clientSecret
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/admin-features/
	 */
	@AnonymousAPI
	public KiiCredentials getAdminAccessToken(String clientID, String clientSecret) throws KiiRestException {
		return this.getAdminAccessToken(clientID, clientSecret, null);
	}
	/**
	 * @param clientID
	 * @param clientSecret
	 * @param expiresAt
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/admin-features/
	 */
	@AnonymousAPI
	public KiiCredentials getAdminAccessToken(String clientID, String clientSecret, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("grant_type", "client_credentials");
		requestBody.addProperty("client_id", clientID);
		requestBody.addProperty("client_secret", clientSecret);
		if (expiresAt != null) {
			requestBody.addProperty("expiresAt", expiresAt);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_APPLICATION_JSON, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiCredentials(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param credentials
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials refreshAccessToken(KiiCredentialsContainer credentials) throws KiiRestException {
		return refreshAccessToken(credentials, null);
	}
	/**
	 * @param credentials
	 * @param expiresAt
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials refreshAccessToken(KiiCredentialsContainer credentials, Long expiresAt) throws KiiRestException {
		return refreshAccessToken(credentials.getRefreshToken(), expiresAt);
	}
	/**
	 * @param refreshToken
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials refreshAccessToken(String refreshToken) throws KiiRestException {
		return this.refreshAccessToken(refreshToken, null);
	}
	/**
	 * @param refreshToken
	 * @param expiresAt
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-in/
	 */
	@AnonymousAPI
	public KiiCredentials refreshAccessToken(String refreshToken, Long expiresAt) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = new JsonObject();
		requestBody.addProperty("grant_type", "refresh_token");
		requestBody.addProperty("refresh_token", refreshToken);
		if (expiresAt != null) {
			requestBody.addProperty("expires_at", expiresAt);
		}
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_APPLICATION_JSON, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			return new KiiCredentials(responseBody);
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
