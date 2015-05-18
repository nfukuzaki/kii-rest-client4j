package com.kii.cloud.rest.client.model.servercode;

import java.math.BigDecimal;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

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
	public static KiiScheduleExecutionQueryClause eq(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause eq(KiiJsonProperty<?> key, boolean value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "eq");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("value", value);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "not");
		clause.json.add("clause", eq(key, value).getJson());
		return clause;
	}
	public static KiiScheduleExecutionQueryClause neq(KiiJsonProperty<?> key, boolean value) {
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
	public static KiiScheduleExecutionQueryClause in(KiiJsonProperty<?> key, int first, int second, int... values) {
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
	public static KiiScheduleExecutionQueryClause in(KiiJsonProperty<?> key, long first, long second, long... values) {
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
	public static KiiScheduleExecutionQueryClause in(KiiJsonProperty<?> key, BigDecimal first, BigDecimal second, BigDecimal... values) {
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
	public static KiiScheduleExecutionQueryClause in(KiiJsonProperty<?> key, String first, String second, String... values) {
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
	public static KiiScheduleExecutionQueryClause gt(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gt(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause gte(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("lowerLimit", value);
		clause.json.addProperty("lowerIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lt(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", false);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiJsonProperty<?> key, int value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiJsonProperty<?> key, long value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiJsonProperty<?> key, BigDecimal value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause lte(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "range");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("upperLimit", value);
		clause.json.addProperty("upperIncluded", true);
		return clause;
	}
	public static KiiScheduleExecutionQueryClause prefix(KiiJsonProperty<?> key, String value) {
		KiiScheduleExecutionQueryClause clause = new KiiScheduleExecutionQueryClause();
		clause.json.addProperty("type", "prefix");
		clause.json.addProperty("field", key.getName());
		clause.json.addProperty("prefix", value);
		return clause;
	}
}
