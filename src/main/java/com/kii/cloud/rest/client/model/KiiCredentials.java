package com.kii.cloud.rest.client.model;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiCredentials extends KiiJsonModel implements KiiCredentialsContainer {
	
	public static final KiiJsonProperty<String> PROPERTY_ID = new KiiJsonProperty<String>(String.class, "id");
	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "access_token");
	public static final KiiJsonProperty<Long> PROPERTY_EXPIRES_IN = new KiiJsonProperty<Long>(Long.class, "expires_in");
	public static final KiiJsonProperty<String> PROPERTY_REFRESH_TOKEN = new KiiJsonProperty<String>(String.class, "refresh_token");
	
	public KiiCredentials(JsonObject json) {
		super(json);
	}
	public KiiCredentials(String accessToken) {
		PROPERTY_ACCESS_TOKEN.set(this.json, accessToken);
	}
	@Override
	public String getID() {
		return PROPERTY_ID.get(this.json);
	}
	@Override
	public boolean hasCredentials() {
		return !StringUtils.isEmpty(this.getAccessToken());
	}
	@Override
	public String getAccessToken() {
		return PROPERTY_ACCESS_TOKEN.get(this.json);
	}
	@Override
	public String getRefreshToken() {
		return PROPERTY_REFRESH_TOKEN.get(this.json);
	}
	public Long getExpiresIn() {
		return PROPERTY_EXPIRES_IN.get(this.json);
	}
}
