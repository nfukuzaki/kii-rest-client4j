package com.kii.cloud.util;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class GsonUtils {
	public static JsonObject clone(JsonObject json) {
		JsonObject result = (JsonObject)new JsonParser().parse(json.toString());
		return result;
	}
	public static String getString(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsString();
		}
		return null;
	}
	public static Boolean getBoolean(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsBoolean();
		}
		return null;
	}
	public static Integer getInt(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsInt();
		}
		return null;
	}
	public static Long getLong(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsLong();
		}
		return null;
	}
	public static BigDecimal getBigDecimal(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsBigDecimal();
		}
		return null;
	}
	public static JsonObject getJsonObject(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsJsonObject();
		}
		return null;
	}
	public static JsonArray getJsonArray(JsonObject json, String name) {
		if (json == null || json.isJsonNull()) {
			return null;
		}
		if (json.has(name)) {
			return json.get(name).getAsJsonArray();
		}
		return null;
	}
}
