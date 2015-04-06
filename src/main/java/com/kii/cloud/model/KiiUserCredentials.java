package com.kii.cloud.model;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiUserCredentials extends KiiJsonModel implements KiiCredentialsContainer {
	
	public static final String PROPERTY_ID = "id";
	public static final String PROPERTY_ACCESS_TOKEN = "access_token";
	public static final String PROPERTY_EXPIRES_IN = "expires_in";
	public static final String PROPERTY_REFRESH_TOKEN = "refresh_token";
	
	public KiiUserCredentials(JsonObject json) {
		super(json);
	}
	@Override
	public String getID() {
		return GsonUtils.getString(this.json, PROPERTY_ID);
	}
	@Override
	public String getAccessToken() {
		return GsonUtils.getString(this.json, PROPERTY_ACCESS_TOKEN);
	}
	@Override
	public String getRefreshToken() {
		return GsonUtils.getString(this.json, PROPERTY_REFRESH_TOKEN);
	}
	public Long getExpiresIn() {
		return GsonUtils.getLong(this.json, PROPERTY_EXPIRES_IN);
	}
}
