package com.kii.cloud.rest.client.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

public class GCMMessage {
	private final JsonObject messageMeta = new JsonObject();
	private final JsonObject messageBody;
	
	public GCMMessage() {
		this.messageMeta.addProperty("enabled", false);
		this.messageBody = new JsonObject();
	}
	public GCMMessage(JsonObject messageBody) {
		this.messageMeta.addProperty("enabled", true);
		this.messageBody = messageBody;
	}
	
	public GCMMessage setEnable(boolean enabled) {
		this.messageMeta.addProperty("enabled", enabled);
		return this;
	}
	
	public GCMMessage setRestrictedPackageName(String restrictedPackageName) {
		this.messageMeta.addProperty("restrictedPackageName", restrictedPackageName);
		return this;
	}
	public GCMMessage setTimeToLive(int timeToLive) {
		this.messageMeta.addProperty("timeToLive", timeToLive);
		return this;
	}
	public GCMMessage setCollapseKey(String collapseKey) {
		this.messageMeta.addProperty("collapseKey", collapseKey);
		return this;
	}
	public GCMMessage setDelayWhileIdle(boolean delayWhileIdle) {
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