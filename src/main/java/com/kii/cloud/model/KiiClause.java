package com.kii.cloud.model;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class KiiClause {
	private JsonObject json = new JsonObject();
	private KiiClause() {
	}
	JsonObject getJson() {
		return this.json;
	}
	
	public static KiiClause all() {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "all");
		return clause;
	}
	public static KiiClause eq(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiClause eq(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiClause eq(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiClause eq(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiClause eq(String key, boolean value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key);
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiClause neq(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiClause neq(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiClause neq(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiClause neq(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiClause neq(String key, boolean value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiClause or(KiiClause first, KiiClause second, KiiClause... another) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "or");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiClause and(KiiClause first, KiiClause second, KiiClause... another) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "and");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiClause in(String key, int first, int second, int... values) {
		KiiClause clause = new KiiClause();
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
	public static KiiClause in(String key, long first, long second, long... values) {
		KiiClause clause = new KiiClause();
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
	public static KiiClause in(String key, BigDecimal first, BigDecimal second, BigDecimal... values) {
		KiiClause clause = new KiiClause();
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
	public static KiiClause in(String key, String first, String second, String... values) {
		KiiClause clause = new KiiClause();
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
	public static KiiClause gt(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiClause gt(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiClause gt(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiClause gt(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiClause gte(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiClause gte(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiClause gte(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiClause gte(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiClause lt(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiClause lt(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiClause lt(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiClause lt(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiClause lte(String key, int value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiClause lte(String key, long value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiClause lte(String key, BigDecimal value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiClause lte(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key);
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiClause prefix(String key, String value) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "prefix");
		clause.json.addProperty("field", key);
		clause.json.addProperty("prefix", value);
		return clause;
	}
	public static KiiClause geoBox(String key, KiiGeoPoint northEast, KiiGeoPoint southWest) {
		KiiClause clause = new KiiClause();
		clause.json.addProperty("type", "geobox");
		clause.json.addProperty("field", key);
		JsonObject box = new JsonObject();
		box.add("ne", northEast.toJson());
		box.add("sw", southWest.toJson());
		clause.json.add("box", box);
		return clause;
	}
	public static KiiClause geoDistance(String key, KiiGeoPoint center, double radius, String calculatedDistance) {
		KiiClause clause = new KiiClause();
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
