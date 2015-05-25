package com.kii.cloud.rest.client.model.servercode;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class KiiScheduleExecutionQueryClause {
	private JsonObject json = new JsonObject();
	private KiiScheduleExecutionQueryClause() {
	}
	JsonObject getJson() {
		return this.json;
	}
	
	public static KiiScheduleExecutionQueryClause all() {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "all");
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiScheduleExecutionQueryProperty<?> key, boolean value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiScheduleExecutionQueryProperty<?> key, boolean value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause or(KiiScheduleExecutionQueryClause first, KiiScheduleExecutionQueryClause second, KiiScheduleExecutionQueryClause... another) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "or");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiScheduleExecutionQueryClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause and(KiiScheduleExecutionQueryClause first, KiiScheduleExecutionQueryClause second, KiiScheduleExecutionQueryClause... another) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "and");
		JsonArray conditions = new JsonArray();
		conditions.add(first.getJson());
		conditions.add(second.getJson());
		for (KiiScheduleExecutionQueryClause c : another) {
			conditions.add(c.getJson());
		}
		clause.json.add("clauses", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause in(KiiScheduleExecutionQueryProperty<?> key, int first, int second, int... values) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key.getName());
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (int value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause in(KiiScheduleExecutionQueryProperty<?> key, long first, long second, long... values) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key.getName());
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (long value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause in(KiiScheduleExecutionQueryProperty<?> key, BigDecimal first, BigDecimal second, BigDecimal... values) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key.getName());
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (BigDecimal value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause in(KiiScheduleExecutionQueryProperty<?> key, String first, String second, String... values) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "in");
		clause.json.addProperty("field", key.getName());
		JsonArray conditions = new JsonArray();
		conditions.add(new JsonPrimitive(first));
		conditions.add(new JsonPrimitive(second));
		for (String value : values) {
			conditions.add(new JsonPrimitive(value));
		}
		clause.json.add("values", conditions);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiScheduleExecutionQueryProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiScheduleExecutionQueryProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiScheduleExecutionQueryProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause prefix(KiiScheduleExecutionQueryProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "prefix");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("prefix", value);
		return clause;
	}
}
