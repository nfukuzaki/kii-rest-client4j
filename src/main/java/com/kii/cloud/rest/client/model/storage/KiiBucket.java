package com.kii.cloud.rest.client.model.storage;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiBucket extends KiiJsonModel {
	public static final KiiJsonProperty<String> PROPERTY_BUCKET_TYPE = new KiiJsonProperty<String>(String.class, "bucketType");
	public static final KiiJsonProperty<Long> PROPERTY_SIZE = new KiiJsonProperty<Long>(Long.class, "size");
	
	public KiiBucket() {
	}
	public KiiBucket(JsonObject json) {
		super(json);
	}
	public String getBucketType() {
		return PROPERTY_BUCKET_TYPE.get(this.json);
	}
	public long getSize() {
		return PROPERTY_SIZE.get(this.json);
	}
}
