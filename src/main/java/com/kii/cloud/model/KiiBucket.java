package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiBucket extends KiiJsonModel {
	public static final KiiJsonProperty PROPERTY_BUCKET_TYPE = new KiiJsonProperty("bucketType");
	public static final KiiJsonProperty PROPERTY_SIZE = new KiiJsonProperty("size");
	
	public KiiBucket() {
	}
	public KiiBucket(JsonObject json) {
		super(json);
	}
	public String getBucketType() {
		return PROPERTY_BUCKET_TYPE.getString(this.json);
	}
	public long getSize() {
		return PROPERTY_SIZE.getLong(this.json);
	}
}
