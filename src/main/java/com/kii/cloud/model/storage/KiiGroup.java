package com.kii.cloud.model.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiGroup extends KiiJsonModel {
	public static final KiiJsonProperty<String> PROPERTY_GROUP_ID = new KiiJsonProperty<String>(String.class, "groupID");
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<String> PROPERTY_OWNER = new KiiJsonProperty<String>(String.class, "owner");
	public static final KiiJsonProperty<JsonArray> PROPERTY_NOT_FOUND_USERS = new KiiJsonProperty<JsonArray>(JsonArray.class, "notFoundUsers");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED_AT = new KiiJsonProperty<Long>(Long.class, "createdAt");
	public static final KiiJsonProperty<Long> PROPERTY_MODIFIED_AT = new KiiJsonProperty<Long>(Long.class, "modifiedAt");

	public KiiGroup() {
	}
	public KiiGroup(JsonObject json) {
		super(json);
	}
	
	public String getGroupID() {
		return PROPERTY_GROUP_ID.get(this.json);
	}
	public KiiGroup setGroupID(String groupID) {
		this.json.addProperty(PROPERTY_GROUP_ID.getName(), groupID);
		return this;
	}
	public String getName() {
		return PROPERTY_NAME.get(this.json);
	}
	public KiiGroup setName(String name) {
		this.json.addProperty(PROPERTY_NAME.getName(), name);
		return this;
	}
	public String getOwner() {
		return PROPERTY_OWNER.get(this.json);
	}
	public KiiGroup setOwner(String userID) {
		this.json.addProperty(PROPERTY_OWNER.getName(), userID);
		return this;
	}
}
