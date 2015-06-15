package com.kii.cloud.rest.client.model.storage;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.uri.KiiBucketURI;

public class KiiBucket extends KiiJsonModel {
	public static final KiiJsonProperty<String> PROPERTY_BUCKET_ID = new KiiJsonProperty<String>(String.class, "id");
	public static final KiiJsonProperty<String> PROPERTY_BUCKET_TYPE = new KiiJsonProperty<String>(String.class, "bucketType");
	public static final KiiJsonProperty<Long> PROPERTY_SIZE = new KiiJsonProperty<Long>(Long.class, "size");
	
	private KiiBucketURI uri;
	
	public KiiBucket() {
	}
	public KiiBucket(JsonObject json) {
		super(json);
	}
	public String getBucketID() {
		return PROPERTY_BUCKET_ID.get(this.json);
	}
	public KiiBucket setBucketID(String bucketID) {
		PROPERTY_BUCKET_ID.set(this.json, bucketID);
		return this;
	}
	public String getBucketType() {
		return PROPERTY_BUCKET_TYPE.get(this.json);
	}
	public long getSize() {
		return PROPERTY_SIZE.get(this.json);
	}
	public KiiBucketURI getURI() {
		return this.uri;
	}
	public KiiBucket setURI(KiiBucketURI uri) {
		this.uri = uri;
		return this;
	}	
}
