package com.kii.cloud.rest.client.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.validation.KiiJsonPropertyValidator;
import com.kii.cloud.rest.client.util.GsonUtils;

/**
 * 
 * @param <T>
 */
public class KiiJsonProperty<T> {
	
	private final String name;
	private final String alias;
	private final Class<T> propertyType;
	private final KiiJsonPropertyValidator[] validators;
	
	public KiiJsonProperty(Class<T> propertyType, String name, KiiJsonPropertyValidator... validators) {
		this(propertyType, name, null, validators);
	}
	public KiiJsonProperty(Class<T> propertyType, String name, String alias, KiiJsonPropertyValidator... validators) {
		this.propertyType = propertyType;
		this.name = name;
		this.alias = alias;
		this.validators = validators;
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
		if (json.has(this.alias)) {
			return true;
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
	public void set(JsonObject json, T value) {
		if (json == null) {
			return;
		}
		for (KiiJsonPropertyValidator validator : this.validators) {
			validator.validate(this, value);
		}
		if (this.propertyType == String.class) {
			json.addProperty(this.getName(), (String)value);
		} else if (this.propertyType == Integer.class) {
			json.addProperty(this.getName(), (Integer)value);
		} else if (this.propertyType == Long.class) {
			json.addProperty(this.getName(), (Long)value);
		} else if (this.propertyType == BigDecimal.class) {
			json.addProperty(this.getName(), (BigDecimal)value);
		} else if (this.propertyType == JsonObject.class) {
			json.add(this.getName(), (JsonObject)value);
		} else if (this.propertyType == JsonArray.class) {
			json.add(this.getName(), (JsonArray)value);
		} else if (this.propertyType == Boolean.class) {
			json.addProperty(this.getName(), (Boolean)value);
		} else {
			throw new RuntimeException("Unexpected type of property.");
		}
	}
	private String getString(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getString(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getString(json, this.alias);
		}
		return null;
	}
	private Boolean getBoolean(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getBoolean(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getBoolean(json, this.alias);
		}
		return null;
	}
	private Integer getInt(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getInt(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getInt(json, this.alias);
		}
		return null;
	}
	private Long getLong(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getLong(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getLong(json, this.alias);
		}
		return null;
	}
	private BigDecimal getBigDecimal(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getBigDecimal(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getBigDecimal(json, this.alias);
		}
		return null;
	}
	private JsonObject getJsonObject(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getJsonObject(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getJsonObject(json, this.alias);
		}
		return null;
	}
	private JsonArray getJsonArray(JsonObject json) {
		if (json.has(this.name)) {
			return GsonUtils.getJsonArray(json, this.name);
		}
		if (this.alias != null && json.has(this.alias)) {
			return GsonUtils.getJsonArray(json, this.alias);
		}
		return null;
	}
}
