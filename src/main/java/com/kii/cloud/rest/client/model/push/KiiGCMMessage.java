package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class KiiGCMMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public KiiGCMMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public KiiGCMMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public KiiGCMMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	
	public KiiGCMMessage setRestrictedPackageName(String restrictedPackageName) {
		this.messageMeta.addProperty("restrictedPackageName", restrictedPackageName);
		return this;
	}
	public KiiGCMMessage setTimeToLive(int timeToLive) {
		this.messageMeta.addProperty("timeToLive", timeToLive);
		return this;
	}
	public KiiGCMMessage setCollapseKey(String collapseKey) {
		this.messageMeta.addProperty("collapseKey", collapseKey);
		return this;
	}
	public KiiGCMMessage setDelayWhileIdle(boolean delayWhileIdle) {
		this.messageMeta.addProperty("delayWhileIdle", delayWhileIdle);
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