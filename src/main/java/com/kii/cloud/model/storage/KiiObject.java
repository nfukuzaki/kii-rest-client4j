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
		this.json.addProperty(PROPERTY_OBJECT_ID.getName(), objectID);
		return this;
	}
	public Long getCreatedAt() {
		return PROPERTY_CREATED_AT.get(this.json);
	}
	public KiiObject setCreatedAt(Long createdAt) {
		this.json.addProperty(PROPERTY_CREATED_AT.getName(), createdAt);
		return this;
	}
	public Long getModifiedAt() {
		return PROPERTY_MODIFIED_AT.get(this.json);
	}
	public KiiObject setModifiedAt(Long modifiedAt) {
		this.json.addProperty(PROPERTY_MODIFIED_AT.getName(), modifiedAt);
		return this;
	}
	public String getOwner() {
		return PROPERTY_OWNER.get(this.json);
	}
	public KiiObject setOwner(String owner) {
		this.json.addProperty(PROPERTY_OWNER.getName(), owner);
		return this;
	}
	public String getVersion() {
		return PROPERTY_VERSION.get(this.json);
	}
	public KiiObject setVersion(String version) {
		this.json.addProperty(PROPERTY_VERSION.getName(), version);
		return this;
	}
	public KiiObject set(String name, KiiGeoPoint value) {
		this.json.add(name, value.toJson());
		return this;
	}
}
