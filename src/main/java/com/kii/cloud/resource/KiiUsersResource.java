package com.kii.cloud.resource;

import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kii.cloud.KiiRestException;
import com.kii.cloud.model.KiiNormalUser;
import com.kii.cloud.model.KiiPseudoUser;
import com.kii.cloud.model.KiiUser;
import com.kii.cloud.util.GsonUtils;
import com.squareup.okhttp.MediaType;

public class KiiUsersResource extends KiiRestSubResource {
	public static final String BASE_PATH = "/users";
	
	public static final MediaType MEDIA_TYPE_REGISTRATION_REQUEST = MediaType.parse("application/vnd.kii.RegistrationAndAuthorizationRequest+json");
	
	public KiiUsersResource(KiiAppResource parent) {
		super(parent);
	}
	public KiiUser register(KiiUser user, String password) throws KiiRestException {
		if (user.isPseudo()) {
			return this.register((KiiPseudoUser)user, password);
		} else {
			return this.register((KiiNormalUser)user, password);
		}
	}
	private KiiUser register(KiiNormalUser user, String password) throws KiiRestException {
		Map<String, String> headers = this.newAppHeaders();
		JsonObject request = (JsonObject)new JsonParser().parse(user.toJsonString());
		request.addProperty("password", password);
		JsonObject response = this.executePost(headers, MEDIA_TYPE_REGISTRATION_REQUEST, request);
		String userID = GsonUtils.getString(response, KiiUser.PROPERTY_USER_ID);
		String accessToken = GsonUtils.getString(response, KiiUser.PROPERTY_ACCESS_TOKEN);
		String refreshToken = GsonUtils.getString(response, KiiUser.PROPERTY_REFRESH_TOKEN);
		return user.setUserID(userID).setAccessToken(accessToken).setRefreshToken(refreshToken);
	}
	private KiiUser register(KiiPseudoUser user, String password) throws KiiRestException {
		return null;
	}
	public KiiUser login(String identifier, String password) throws KiiRestException {
		return null;
	}
	public KiiUser login(String token) throws KiiRestException {
		return null;
	}
	@Override
	public String getPath() {
		return BASE_PATH;
	}
}
