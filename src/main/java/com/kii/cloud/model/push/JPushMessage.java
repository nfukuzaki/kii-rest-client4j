package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class JPushMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public JPushMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public JPushMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public JPushMessage setEnable(boolean enabled) {
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