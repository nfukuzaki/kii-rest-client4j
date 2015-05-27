package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiMqttMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public KiiMqttMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public KiiMqttMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public KiiMqttMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	public KiiMqttMessage setDryRun(boolean dryRun) {
		this.messageMeta.addProperty("dryRun", dryRun);
		return this;
	}
	public KiiMqttMessage setRetain(boolean retain) {
		this.messageMeta.addProperty("retain", retain);
		return this;
	}
	public KiiMqttMessage setQos(int qos) {
		this.messageMeta.addProperty("qos", qos);
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