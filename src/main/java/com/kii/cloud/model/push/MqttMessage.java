package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class MqttMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public MqttMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public MqttMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public MqttMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	public MqttMessage setDryRun(boolean dryRun) {
		this.messageMeta.addProperty("dryRun", dryRun);
		return this;
	}
	public MqttMessage setRetain(boolean retain) {
		this.messageMeta.addProperty("retain", retain);
		return this;
	}
	public MqttMessage setQos(int qos) {
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