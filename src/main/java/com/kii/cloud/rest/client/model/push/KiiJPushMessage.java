package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiJPushMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public KiiJPushMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public KiiJPushMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public KiiJPushMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	
	public JsonObject toJson() {
		JsonObject result = GsonUtils.clone(this.messageMeta);
		if (this.messageBody != null) {
			result.add("data", GsonUtils.clone(this.messageBody));
		}
		return result;
	}
}