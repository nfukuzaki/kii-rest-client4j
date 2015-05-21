package com.kii.cloud.rest.client.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.rest.client.annotation.AnonymousAPI;
import com.kii.cloud.rest.client.exception.KiiRestException;
import com.kii.cloud.rest.client.model.KiiScope;
import com.kii.cloud.rest.client.model.storage.KiiNormalUser;
import com.kii.cloud.rest.client.model.storage.KiiPseudoUser;
import com.kii.cloud.rest.client.model.storage.KiiUser;
import com.kii.cloud.rest.client.resource.KiiAppResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest;
import com.kii.cloud.rest.client.resource.KiiRestSubResource;
import com.kii.cloud.rest.client.resource.KiiScopedResource;
import com.kii.cloud.rest.client.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

/**
 * Represents the users resource like following URI:
 * <ul>
 * <li>https://hostname/api/apps/{APP_ID}/users
 * </ul>
 */
public class KiiUsersResource extends KiiRestSubResource implements KiiScopedResource {
	
	public static final String BASE_PATH = "/users";
	
	public static final MediaType MEDIA_TYPE_REGISTRATION_REQUEST = MediaType.parse("application/vnd.kii.RegistrationAndAuthorizationRequest+json");
	
	public KiiUsersResource(KiiAppResource parent) {
		super(parent);
	}
	/**
	 * @param user
	 * @param password
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/sign-up/
	 */
	@AnonymousAPI
	public KiiNormalUser register(KiiNormalUser user, String password) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		if (password == null) {
			throw new IllegalArgumentException("password is null");
		}
		Map<String, String> headers = this.newAppHeaders();
		JsonObject requestBody = (JsonObject)new JsonParser().parse(user.toJsonString());
		requestBody.addProperty("password", password);
		
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_REGISTRATION_REQUEST, requestBody);
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String accessToken = KiiUser.PROPERTY_ACCESS_TOKEN.get(responseBody);
			String refreshToken = KiiUser.PROPERTY_REFRESH_TOKEN.get(responseBody);
			responseBody.remove(KiiUser.PROPERTY_ACCESS_TOKEN.getName());
			responseBody.remove(KiiUser.PROPERTY_REFRESH_TOKEN.getName());
			KiiNormalUser registeredUser = new KiiNormalUser(responseBody);
			registeredUser.setAccessToken(accessToken);
			registeredUser.setRefreshToken(refreshToken);
			return registeredUser;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	/**
	 * @param user
	 * @return
	 * @throws KiiRestException
	 * @see http://documentation.kii.com/en/guides/rest/managing-users/pseudo-users/
	 */
	@AnonymousAPI
	public KiiPseudoUser register(KiiPseudoUser user) throws KiiRestException {
		if (user == null) {
			throw new IllegalArgumentException("user is null");
		}
		Map<String, String> headers = this.newAppHeaders();
		KiiRestRequest request = new KiiRestRequest(getUrl(), Method.POST, headers, MEDIA_TYPE_REGISTRATION_REQUEST, user.toJsonString());
		try {
			Response response = this.execute(request);
			JsonObject responseBody = this.parseResponseAsJsonObject(request, response);
			String accessToken = KiiUser.PROPERTY_ACCESS_TOKEN.get(responseBody);
			responseBody.remove(KiiUser.PROPERTY_ACCESS_TOKEN.getName());
			KiiPseudoUser registeredUser = new KiiPseudoUser(responseBody);
			registeredUser.setAccessToken(accessToken);
			return registeredUser;
		} catch (IOException e) {
			throw new KiiRestException(request.getCurl(), e);
		}
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
	@Override
	public KiiScope getScope() {
		return KiiScope.USER;
	}
}
