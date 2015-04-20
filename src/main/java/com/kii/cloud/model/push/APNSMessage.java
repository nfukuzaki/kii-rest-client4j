package com.kii.cloud.model.push;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kii.cloud.util.GsonUtils;

public class APNSMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject alert = new JsonObject();
	private final JsonObject messageBody;
	
	public APNSMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public APNSMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public APNSMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	public APNSMessage setSound(String sound) {
		this.messageMeta.addProperty("sound", sound);
		return this;
	}
	
	public APNSMessage setBadge(int badge) {
		this.messageMeta.addProperty("badge", badge);
		return this;
	}
	public APNSMessage setActionLocKey(String actionLocKey) {
		this.messageMeta.addProperty("actionLocKey", actionLocKey);
		return this;
	}
	public APNSMessage setLocKey(String locKey) {
		this.messageMeta.addProperty("locKey", locKey);
		return this;
	}
	public APNSMessage setContentAvailable(int contentAvailable) {
		this.messageMeta.addProperty("contentAvailable", contentAvailable);
		return this;
	}
	public APNSMessage setCategory(String category) {
		this.messageMeta.addProperty("category", category);
		return this;
	}
	public APNSMessage setAlertLocArgs(String[] locArgs) {
		if (locArgs == null || locArgs.length == 0) {
			throw new IllegalArgumentException("locArgs is null or empty");
		}
		JsonArray array = new JsonArray();
		for (String locArg : locArgs) {
			array.add(new JsonPrimitive(locArg));
		}
		this.alert.add("locArgs", array);
		return this;
	}
	public APNSMessage setAlertLaunchImage(String launchImage) {
		this.alert.addProperty("launchImage", launchImage);
		return this;
	}
	public APNSMessage setAlertBody(String body) {
		this.alert.addProperty("body", body);
		return this;
	}

	
	
	
	
	
	
	
	public JsonObject toJson() {
		JsonObject result = GsonUtils.clone(this.messageMeta);
		if (this.messageBody != null) {
			result.add("data", GsonUtils.clone(this.messageBody));
		}
		if (this.alert != null) {
			result.add("alert", GsonUtils.clone(this.alert));
		}
		return result;
	}
}