package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiAPNSMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject alert = new JsonObject();
	private final JsonObject messageBody;
	
	public KiiAPNSMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public KiiAPNSMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public KiiAPNSMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	public KiiAPNSMessage setSound(String sound) {
		this.messageMeta.addProperty("sound", sound);
		return this;
	}
	
	public KiiAPNSMessage setBadge(int badge) {
		this.messageMeta.addProperty("badge", badge);
		return this;
	}
	public KiiAPNSMessage setActionLocKey(String actionLocKey) {
		this.messageMeta.addProperty("actionLocKey", actionLocKey);
		return this;
	}
	public KiiAPNSMessage setLocKey(String locKey) {
		this.messageMeta.addProperty("locKey", locKey);
		return this;
	}
	public KiiAPNSMessage setContentAvailable(int contentAvailable) {
		this.messageMeta.addProperty("contentAvailable", contentAvailable);
		return this;
	}
	public KiiAPNSMessage setCategory(String category) {
		this.messageMeta.addProperty("category", category);
		return this;
	}
	public KiiAPNSMessage setAlertLocArgs(String[] locArgs) {
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
	public KiiAPNSMessage setAlertLaunchImage(String launchImage) {
		this.alert.addProperty("launchImage", launchImage);
		return this;
	}
	public KiiAPNSMessage setAlertBody(String body) {
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