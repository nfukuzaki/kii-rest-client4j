package com.kii.cloud.model.storage;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiGroupMembers extends KiiJsonModel {
	public static final KiiJsonProperty<JsonArray> PROPERTY_MEMBERS = new KiiJsonProperty<JsonArray>(JsonArray.class, "members");
	public static final KiiJsonProperty<String> PROPERTY_USER_ID = new KiiJsonProperty<String>(String.class, "userID");
	
	public KiiGroupMembers() {
	}
	public KiiGroupMembers(JsonObject json) {
		super(json);
	}
	/**
	 * 
	 * @return list of user id who member of this group
	 */
	public List<String> getMembers() {
		List<String> members = new ArrayList<>();
		if (PROPERTY_MEMBERS.has(this.json)) {
			JsonArray array = PROPERTY_MEMBERS.get(this.json);
			for (int i = 0; i < array.size(); i++) {
				JsonElement member = array.get(i);
				if (member.isJsonPrimitive()) {
					// members ["UserID1", "UserID2", ...]
					members.add(member.getAsString());
				} else if (member.isJsonObject()) {
					// members [{"userID":"UserID1"}, {"userID":"UserID2"}, ...]
					members.add(PROPERTY_USER_ID.get(array.get(i).getAsJsonObject()));
				}
			}
		}
		return members;
	}
	public KiiGroupMembers addMember(String userID) {
		JsonArray members = null;
		if (PROPERTY_MEMBERS.has(this.json)) {
			members = PROPERTY_MEMBERS.get(this.json);
		} else {
			members = new JsonArray();
			PROPERTY_MEMBERS.set(this.json, members);
		}
		JsonObject member = new JsonObject();
		member.addProperty(PROPERTY_USER_ID.getName(), userID);
		members.add(member);
		return this;
	}
	public JsonArray toJsonArrayAsRequest() {
		// convert to 'members ["UserID1", "UserID2", ...]'
		JsonArray result = new JsonArray();
		for (String member : this.getMembers()) {
			result.add(new JsonPrimitive(member));
		}
		return result;
	}
}
