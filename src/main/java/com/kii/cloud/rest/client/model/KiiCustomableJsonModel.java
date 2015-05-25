package com.kii.cloud.rest.client.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;

@SuppressWarnings("unchecked")
public abstract class KiiCustomableJsonModel<T extends KiiCustomableJsonModel<?>> extends KiiJsonModel {
	public KiiCustomableJsonModel() {
	}
	public KiiCustomableJsonModel(JsonObject json) {
		super(json);
	}
	public String getString(String name) {
		return GsonUtils.getString(this.json, name);
	}
	public Integer getInt(String name) {
		return GsonUtils.getInt(this.json, name);
	}
	public Long getLong(String name) {
		return GsonUtils.getLong(this.json, name);
	}
	public BigDecimal getBigDecimal(String name) {
		return GsonUtils.getBigDecimal(this.json, name);
	}
	public Boolean getBoolean(String name) {
		return GsonUtils.getBoolean(this.json, name);
	}
	public JsonObject getJsonObject(String name) {
		return GsonUtils.getJsonObject(this.json, name);
	}
	public JsonArray getJsonArray(String name) {
		return GsonUtils.getJsonArray(this.json, name);
	}
	public T set(String name, String value) {
		this.json.addProperty(name, value);
		return (T)this;
	}
	public T set(String name, int value) {
		this.json.addProperty(name, value);
		return (T)this;
	}
	public T set(String name, long value) {
		this.json.addProperty(name, value);
		return (T)this;
	}
	public T set(String name, BigDecimal value) {
		this.json.addProperty(name, value);
		return (T)this;
	}
	public T set(String name, boolean value) {
		this.json.addProperty(name, value);
		return (T)this;
	}
	public T set(String name, JsonObject value) {
		this.json.add(name, value);
		return (T)this;
	}
	public T set(String name, JsonArray value) {
		this.json.add(name, value);
		return (T)this;
	}
}
