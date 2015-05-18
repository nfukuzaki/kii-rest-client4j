package com.kii.cloud.rest.client.model.storage;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class KiiQueryClause {
	private JsonObject json = new JsonObject();
	private KiiQueryClause() {
	}
	JsonObject getJson() {
		return this.json;
	}
	
	public static KiiQueryClause all() {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "all");
		return clause;
	}
	public static KiiQueryClause eq(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiQueryClause eq(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiQueryClause eq(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiQueryClause eq(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiQueryClause eq(String key, boolean value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiQueryClause neq(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiQueryClause neq(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiQueryClause neq(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiQueryClause neq(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiQueryClause neq(String key, boolean value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiQueryClause or(KiiQueryClause first, KiiQueryClause second, KiiQueryClause... another) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "or");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiQueryClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiQueryClause and(KiiQueryClause first, KiiQueryClause second, KiiQueryClause... another) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "and");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiQueryClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiQueryClause in(String key, int first, int second, int... values) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key);
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (int value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiQueryClause in(String key, long first, long second, long... values) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key);
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (long value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiQueryClause in(String key, BigDecimal first, BigDecimal second, BigDecimal... values) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key);
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (BigDecimal value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiQueryClause in(String key, String first, String second, String... values) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key);
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (String value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiQueryClause gt(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiQueryClause gt(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiQueryClause gt(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiQueryClause gt(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiQueryClause gte(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiQueryClause gte(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiQueryClause gte(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiQueryClause gte(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiQueryClause lt(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiQueryClause lt(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiQueryClause lt(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiQueryClause lt(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiQueryClause lte(String key, int value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiQueryClause lte(String key, long value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiQueryClause lte(String key, BigDecimal value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiQueryClause lte(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiQueryClause prefix(String key, String value) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "prefix");
		clause.json.addProperty("field", key);
		clause.json.addProperty("prefix", value);
		return clause;
	}
	public static KiiQueryClause geoBox(String key, KiiGeoPoint northEast, KiiGeoPoint southWest) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "geobox");
		clause.json.addProperty("field", key);
		JsonObject box = new JsonObject();
		box.add("ne", northEast.toJson());
		box.add("sw", southWest.toJson());
		clause.json.add("box", box);
		return clause;
	}
	public static KiiQueryClause geoDistance(String key, KiiGeoPoint center, double radius, String calculatedDistance) {
		KiiQueryClause clause = new KiiQueryClause();
		clause.json.addProperty("type", "geodistance");
		clause.json.addProperty("field", key);
		clause.json.add("center", center.toJson());
		clause.json.addProperty("radius", radius);
		if(calculatedDistance != null) {
			clause.json.addProperty("calculatedDistance", calculatedDistance);
		}
		return clause;
	}
}
