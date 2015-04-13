package com.kii.cloud.resource.storage;

import java.io.IOException;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.annotation.AnonymousAPI;
import com.kii.cloud.model.storage.KiiNormalUser;
import com.kii.cloud.model.storage.KiiPseudoUser;
import com.kii.cloud.model.storage.KiiUser;
import com.kii.cloud.resource.KiiAppResource;
import com.kii.cloud.resource.KiiRestRequest;
import com.kii.cloud.resource.KiiRestSubResource;
import com.kii.cloud.resource.KiiRestRequest.Method;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.Response;

public class KiiUsersResource extends KiiRestSubResource {
	
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
}
