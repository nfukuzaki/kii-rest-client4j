package com.kii.cloud.model.storage;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiCustomableJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiObject extends KiiCustomableJsonModel<KiiObject> {
	public static final KiiJsonProperty<String> PROPERTY_OBJECT_ID = new KiiJsonProperty<String>(String.class, "objectID", "_id");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED_AT = new KiiJsonProperty<Long>(Long.class, "createdAt", "_created");
	public static final KiiJsonProperty<Long> PROPERTY_MODIFIED_AT = new KiiJsonProperty<Long>(Long.class, "modifiedAt", "_modified");
	public static final KiiJsonProperty<String> PROPERTY_DATA_TYPE = new KiiJsonProperty<String>(String.class, "dataType", "_dataType");
	public static final KiiJsonProperty<String> PROPERTY_OWNER = new KiiJsonProperty<String>(String.class, "_owner");
	public static final KiiJsonProperty<String> PROPERTY_VERSION = new KiiJsonProperty<String>(String.class, "_version");
	
	public KiiObject() {
	}
	public KiiObject(JsonObject json) {
		super(json);
	}
	public String getObjectID() {
		return PROPERTY_OBJECT_ID.get(this.json);
	}
	public KiiObject setObjectID(String objectID) {
		PROPERTY_OBJECT_ID.set(this.json, objectID);
		return this;
	}
	public Long getCreatedAt() {
		return PROPERTY_CREATED_AT.get(this.json);
	}
	public KiiObject setCreatedAt(Long createdAt) {
		PROPERTY_CREATED_AT.set(this.json, createdAt);
		return this;
	}
	public Long getModifiedAt() {
		return PROPERTY_MODIFIED_AT.get(this.json);
	}
	public KiiObject setModifiedAt(Long modifiedAt) {
		PROPERTY_MODIFIED_AT.set(this.json, modifiedAt);
		return this;
	}
	public String getOwner() {
		return PROPERTY_OWNER.get(this.json);
	}
	public KiiObject setOwner(String owner) {
		PROPERTY_OWNER.set(this.json, owner);
		return this;
	}
	public String getVersion() {
		return PROPERTY_VERSION.get(this.json);
	}
	public KiiObject setVersion(String version) {
		PROPERTY_VERSION.set(this.json, version);
		return this;
	}
	public KiiObject set(String name, KiiGeoPoint value) {
		this.json.add(name, value.toJson());
		return this;
	}
}
