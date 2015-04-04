package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiJsonModel {
	protected JsonObject json;
	public KiiJsonModel() {
		this.json = new JsonObject();
	}
	public KiiJsonModel(JsonObject json) {
		this.json = json;
	}
	public JsonObject getJsonObject() {
		return this.json;
	}
	public void setJsonObject(JsonObject json) {
		this.json = json;
	}
	public String toJsonString() {
		return this.json.toString();
	}
}
