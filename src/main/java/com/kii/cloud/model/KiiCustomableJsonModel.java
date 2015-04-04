package com.kii.cloud.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

@SuppressWarnings("unchecked")
public abstract class KiiCustomableJsonModel<T> extends KiiJsonModel {
	public String getString(String name) {
		return GsonUtils.getString(this.json, name);
	}
	public int getInt(String name) {
		return GsonUtils.getInt(this.json, name);
	}
	public long getLong(String name) {
		return GsonUtils.getLong(this.json, name);
	}
	public BigDecimal getBigDecimal(String name) {
		return GsonUtils.getBigDecimal(this.json, name);
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
