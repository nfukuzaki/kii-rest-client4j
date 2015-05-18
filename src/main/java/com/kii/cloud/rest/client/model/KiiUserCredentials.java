package com.kii.cloud.rest.client.model;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiUserCredentials extends KiiJsonModel implements KiiCredentialsContainer {
	
	public static final KiiJsonProperty<String> PROPERTY_ID = new KiiJsonProperty<String>(String.class, "id");
	public static final KiiJsonProperty<String> PROPERTY_ACCESS_TOKEN = new KiiJsonProperty<String>(String.class, "access_token");
	public static final KiiJsonProperty<Long> PROPERTY_EXPIRES_IN = new KiiJsonProperty<Long>(Long.class, "expires_in");
	public static final KiiJsonProperty<String> PROPERTY_REFRESH_TOKEN = new KiiJsonProperty<String>(String.class, "refresh_token");
	
	public KiiUserCredentials(JsonObject json) {
		super(json);
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
	@Override
	public boolean isAdmin() {
		return false;
	}
	public Long getExpiresIn() {
		return PROPERTY_EXPIRES_IN.get(this.json);
	}
}
