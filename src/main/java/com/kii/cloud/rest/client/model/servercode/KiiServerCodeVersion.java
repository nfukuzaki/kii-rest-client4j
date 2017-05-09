package com.kii.cloud.rest.client.model.servercode;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiServerCodeVersion extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_VERSION_ID = new KiiJsonProperty<String>(String.class, "versionID");
	public static final KiiJsonProperty<String> PROPERTY_ENVIRONMENT_VERSION = new KiiJsonProperty<String>(String.class, "environmentVersion");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED_AT = new KiiJsonProperty<Long>(Long.class, "createdAt");
	public static final KiiJsonProperty<Long> PROPERTY_MODIFIED_AT = new KiiJsonProperty<Long>(Long.class, "modifiedAt");
	public static final KiiJsonProperty<Boolean> PROPERTY_CURRENT = new KiiJsonProperty<Boolean>(Boolean.class, "current");
	
	public KiiServerCodeVersion(JsonObject json) {
		super(json);
	}
	
	public String getVersionID() {
		return PROPERTY_VERSION_ID.get(this.json);
	}
	public String getEnvironmentVersion() {
		return PROPERTY_ENVIRONMENT_VERSION.get(this.json);
	}
	public Long getCreatedAt() {
		return PROPERTY_CREATED_AT.get(this.json);
	}
	public Long getModifiedAt() {
		return PROPERTY_MODIFIED_AT.get(this.json);
	}
	public boolean isCurrent() {
		return PROPERTY_CURRENT.get(this.json);
	}
	
}
