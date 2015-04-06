package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;
import com.squareup.okhttp.MediaType;

public class KiiUsersResource extends KiiRestSubResource {
	
	public static final String BASE_PATH = "/users";
	
	public static final MediaType MEDIA_TYPE_REGISTRATION_REQUEST = MediaType.parse("application/vnd.kii.RegistrationAndAuthorizationRequest+json");
	
	public KiiUsersResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiNormalUser register(KiiNormalUser user, String password) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject request = (JsonObject)new JsonParser().parse(user.toJsonString());
		request.addProperty("password", password);
		JsonObject response = this.executePost(headers, MEDIA_TYPE_REGISTRATION_REQUEST, request);
		String accessToken = KiiUser.PROPERTY_ACCESS_TOKEN.getString(response);
		String refreshToken = KiiUser.PROPERTY_REFRESH_TOKEN.getString(response);
		response.remove(KiiUser.PROPERTY_ACCESS_TOKEN.getName());
		response.remove(KiiUser.PROPERTY_REFRESH_TOKEN.getName());
		KiiNormalUser registeredUser = new KiiNormalUser(response);
		registeredUser.setAccessToken(accessToken);
		registeredUser.setRefreshToken(refreshToken);
		return registeredUser;
	}
	public KiiPseudoUser register(KiiPseudoUser user) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject response = this.executePost(headers, MEDIA_TYPE_REGISTRATION_REQUEST, user.toJsonString());
		String accessToken = KiiUser.PROPERTY_ACCESS_TOKEN.getString(response);
		response.remove(KiiUser.PROPERTY_ACCESS_TOKEN.getName());
		KiiPseudoUser registeredUser = new KiiPseudoUser(response);
		registeredUser.setAccessToken(accessToken);
		return registeredUser;
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
