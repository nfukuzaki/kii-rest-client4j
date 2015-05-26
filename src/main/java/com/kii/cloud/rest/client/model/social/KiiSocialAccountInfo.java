package com.kii.cloud.rest.client.model.social;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiSocialAccountInfo extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_ID = new KiiJsonProperty<String>(String.class, "id");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED_AT = new KiiJsonProperty<Long>(Long.class, "createdAt");
	
	public KiiSocialAccountInfo(JsonObject json) {
		super(json);
	}
	public String getID() {
		return PROPERTY_ID.get(this.json);
	}
	public KiiSocialProvider getType() {
		return KiiSocialProvider.fromString(PROPERTY_TYPE.get(this.json));
	}
	public Long getCreatedAt() {
		return PROPERTY_CREATED_AT.get(this.json);
	}
}
