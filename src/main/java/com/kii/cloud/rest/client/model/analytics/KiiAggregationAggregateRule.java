package com.kii.cloud.rest.client.model.analytics;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiAggregationAggregateRule extends KiiJsonModel {
	
	public enum AggregationFunction {
		SUM,
		AVG,
		COUNT,
		MAX,
		MIN;
	}
	
	public static final KiiJsonProperty<String> PROPERTY_VALUE_OF = new KiiJsonProperty<String>(String.class, "valueOf");
	public static final KiiJsonProperty<String> PROPERTY_WITH = new KiiJsonProperty<String>(String.class, "with");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	
	public KiiAggregationAggregateRule() {
	}
	public KiiAggregationAggregateRule(JsonObject json) {
		super(json);
	}
	
	public String getValueOf() {
		return PROPERTY_VALUE_OF.get(this.json);
	}
	public KiiAggregationAggregateRule setValueOf(String valueOf) {
		PROPERTY_VALUE_OF.set(this.json, valueOf);
		return this;
	}
	
	public AggregationFunction getWith() {
		if (PROPERTY_WITH.has(this.json)) {
			Enum.valueOf(AggregationFunction.class, PROPERTY_WITH.get(this.json).toUpperCase());
		}
		return null;
	}
	public KiiAggregationAggregateRule setWith(AggregationFunction function) {
		PROPERTY_WITH.set(this.json, function.name().toLowerCase());
		return this;
	}
	public FieldType getType() {
		if (PROPERTY_TYPE.has(this.json)) {
			return Enum.valueOf(FieldType.class, PROPERTY_TYPE.get(this.json));
		}
		return null;
	}
	public KiiAggregationAggregateRule setType(FieldType fieldType) {
		PROPERTY_TYPE.set(this.json, fieldType.name());
		return this;
	}
}
