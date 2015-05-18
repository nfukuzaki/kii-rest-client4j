package com.kii.cloud.rest.client.model.storage;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

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
		PROPERTY_GROUP_ID.set(this.json, groupID);
		return this;
	}
	public String getName() {
		return PROPERTY_NAME.get(this.json);
	}
	public KiiGroup setName(String name) {
		PROPERTY_NAME.set(this.json, name);
		return this;
	}
	public String getOwner() {
		return PROPERTY_OWNER.get(this.json);
	}
	public KiiGroup setOwner(KiiUser user) {
		return this.setOwner(user.getUserID());
	}
	public KiiGroup setOwner(String userID) {
		PROPERTY_OWNER.set(this.json, userID);
		return this;
	}
}
