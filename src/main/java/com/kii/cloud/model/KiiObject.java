package com.kii.cloud.model;

import com.kii.cloud.util.GsonUtils;

public class KiiObject extends KiiCustomableJsonModel<KiiObject> {
	public static final String PROPERTY_OBJECT_ID = "objectID";
	public static final String PROPERTY_CREATED_AT = "createdAt";
	public static final String PROPERTY_DATA_TYPE = "dataType";
	
	public static final String PROPERTY_ID = "_id";
	public static final String PROPERTY_CREATED = "_created";
	public static final String PROPERTY_MODIFIED = "_modified";
	public static final String PROPERTY_OWNER = "_owner";
	public static final String PROPERTY_VERSION = "_version";
	
	public String getObjectID() {
		return GsonUtils.getString(this.json, PROPERTY_OBJECT_ID);
	}
	public KiiObject setObjectID(String objectID) {
		this.json.addProperty(PROPERTY_OBJECT_ID, objectID);
		return this;
	}
	public Long getCreatedAt() {
		return GsonUtils.getLong(this.json, PROPERTY_CREATED_AT);
	}
	public KiiObject setCreatedAt(Long createdAt) {
		this.json.addProperty(PROPERTY_CREATED_AT, createdAt);
		return this;
	}

}
