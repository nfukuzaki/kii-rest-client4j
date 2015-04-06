package com.kii.cloud.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiJsonProperty {
	
	private final String name;
	private final String[] aliases;
	
	public KiiJsonProperty(String name, String... aliases) {
		this.name = name;
		this.aliases = aliases;
	}
	public String getName() {
		return this.name;
	}
	public boolean has(JsonObject json) {
		if (json == null) {
			return false;
		}
		if (json.has(this.name)) {
			return true;
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return true;
			}
		}
		return false;
	}
	public String getString(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getString(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getString(json, alias);
			}
		}
		return null;
	}
	public Boolean getBoolean(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getBoolean(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getBoolean(json, alias);
			}
		}
		return null;
	}
	public Integer getInt(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getInt(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getInt(json, alias);
			}
		}
		return null;
	}
	public Long getLong(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getLong(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getLong(json, alias);
			}
		}
		return null;
	}
	public BigDecimal getBigDecimal(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getBigDecimal(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getBigDecimal(json, alias);
			}
		}
		return null;
	}
	public JsonObject getJsonObject(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getJsonObject(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getJsonObject(json, alias);
			}
		}
		return null;
	}
	public JsonArray getJsonArray(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (json.has(this.name)) {
			return GsonUtils.getJsonArray(json, this.name);
		}
		for (String alias : this.aliases) {
			if (json.has(alias)) {
				return GsonUtils.getJsonArray(json, alias);
			}
		}
		return null;
	}
}
