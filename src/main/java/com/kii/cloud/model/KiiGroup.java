package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiGroup extends KiiJsonModel {
	public static final KiiJsonProperty PROPERTY_GROUP_ID = new KiiJsonProperty("groupID");
	public static final KiiJsonProperty PROPERTY_NAME = new KiiJsonProperty("name");
	public static final KiiJsonProperty PROPERTY_OWNER = new KiiJsonProperty("owner");
	public static final KiiJsonProperty PROPERTY_NOT_FOUND_USERS = new KiiJsonProperty("notFoundUsers");
	public static final KiiJsonProperty PROPERTY_CREATED_AT = new KiiJsonProperty("createdAt");
	public static final KiiJsonProperty PROPERTY_MODIFIED_AT = new KiiJsonProperty("modifiedAt");

	public KiiGroup() {
	}
	public KiiGroup(JsonObject json) {
		super(json);
	}
	
	public String getGroupID() {
		return PROPERTY_GROUP_ID.getString(this.json);
	}
	public KiiGroup setGroupID(String groupID) {
		this.json.addProperty(PROPERTY_GROUP_ID.getName(), groupID);
		return this;
	}
	public String getName() {
		return PROPERTY_NAME.getString(this.json);
	}
	public KiiGroup setName(String name) {
		this.json.addProperty(PROPERTY_NAME.getName(), name);
		return this;
	}
	public String getOwner() {
		return PROPERTY_OWNER.getString(this.json);
	}
	public KiiGroup setOwner(String userID) {
		this.json.addProperty(PROPERTY_OWNER.getName(), userID);
		return this;
	}
}
