package com.kii.cloud.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.util.GsonUtils;

public class KiiJsonProperty<T> {
	
	private final String name;
	private final String[] aliases;
	private final Class<T> propertyType;
	
	public KiiJsonProperty(Class<T> propertyType, String name, String... aliases) {
		this.propertyType = propertyType;
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
	@SuppressWarnings("unchecked")
	public T get(JsonObject json) {
		if (json == null) {
			return null;
		}
		if (this.propertyType == String.class) {
			return (T)this.getString(json);
		} else if (this.propertyType == Integer.class) {
			return (T)this.getInt(json);
		} else if (this.propertyType == Long.class) {
			return (T)this.getLong(json);
		} else if (this.propertyType == BigDecimal.class) {
			return (T)this.getBigDecimal(json);
		} else if (this.propertyType == JsonObject.class) {
			return (T)this.getJsonObject(json);
		} else if (this.propertyType == JsonArray.class) {
			return (T)this.getJsonArray(json);
		} else if (this.propertyType == Boolean.class) {
			return (T)this.getBoolean(json);
		}
		return null;
	}
	private String getString(JsonObject json) {
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
	private Boolean getBoolean(JsonObject json) {
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
	private Integer getInt(JsonObject json) {
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
	private Long getLong(JsonObject json) {
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
	private BigDecimal getBigDecimal(JsonObject json) {
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
	private JsonObject getJsonObject(JsonObject json) {
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
	private JsonArray getJsonArray(JsonObject json) {
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
