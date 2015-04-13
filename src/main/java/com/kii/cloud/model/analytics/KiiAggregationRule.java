package com.kii.cloud.model.analytics;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiAggregationRule extends KiiJsonModel {
	
	
	public static final KiiJsonProperty<String> PROPERTY_ID = new KiiJsonProperty<String>(String.class, "_id");
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<String> PROPERTY_SOURCE = new KiiJsonProperty<String>(String.class, "source");
	public static final KiiJsonProperty<Long> PROPERTY_CONVERSION_RULE_ID = new KiiJsonProperty<Long>(Long.class, "conversionRuleID");
	public static final KiiJsonProperty<String> PROPERTY_EVENT_TYPE = new KiiJsonProperty<String>(String.class, "eventType");
	public static final KiiJsonProperty<JsonObject> PROPERTY_AGGREGATE = new KiiJsonProperty<JsonObject>(JsonObject.class, "aggregate");
	public static final KiiJsonProperty<JsonArray> PROPERTY_GROUP_BY = new KiiJsonProperty<JsonArray>(JsonArray.class, "groupBy");
	
	public KiiAggregationRule() {
	}
	public KiiAggregationRule(JsonObject json) {
		super(json);
	}
	
	public String getID() {
		return PROPERTY_ID.get(this.json);
	}
	public String getName() {
		return PROPERTY_NAME.get(this.json);
	}
	public KiiAggregationRule setName(String name) {
		PROPERTY_NAME.set(this.json, name);
		return this;
	}
	public String getSource() {
		return PROPERTY_SOURCE.get(this.json);
	}
	public KiiAggregationRule setSource(String source) {
		PROPERTY_SOURCE.set(this.json, source);
		return this;
	}
	public Long getConversionRuleID() {
		return PROPERTY_CONVERSION_RULE_ID.get(this.json);
	}
	public KiiAggregationRule setConversionRuleID(Long conversionRuleID) {
		PROPERTY_CONVERSION_RULE_ID.set(this.json, conversionRuleID);
		return this;
	}
	public String getEventType() {
		return PROPERTY_EVENT_TYPE.get(this.json);
	}
	public KiiAggregationRule setEventType(String eventType) {
		PROPERTY_EVENT_TYPE.set(this.json, eventType);
		return this;
	}
	public KiiAggregationAggregateRule getAggregate() {
		if (PROPERTY_AGGREGATE.has(this.json)) {
			return new KiiAggregationAggregateRule(PROPERTY_AGGREGATE.get(this.json));
		}
		return null;
	}
	public KiiAggregationRule setAggregate(KiiAggregationAggregateRule aggregateRule) {
		PROPERTY_AGGREGATE.set(this.json, aggregateRule.getJsonObject());
		return this;
	}
	public List<KiiAggregationGroupByRule> getGroupBy() {
		 List<KiiAggregationGroupByRule> result = new ArrayList<KiiAggregationGroupByRule>();
		 if (PROPERTY_GROUP_BY.has(this.json)) {
			 JsonArray array = PROPERTY_GROUP_BY.get(this.json);
			 for (int i = 0; i < array.size(); i++) {
				 result.add(new KiiAggregationGroupByRule(array.get(i).getAsJsonObject()));
			 }
		 }
		 return result;
	}
	public KiiAggregationRule addGroupBy(KiiAggregationGroupByRule groupByRule) {
		JsonArray array = PROPERTY_GROUP_BY.get(this.json);
		if (array == null) {
			array = new JsonArray();
		}
		array.add(groupByRule.getJsonObject());
		PROPERTY_GROUP_BY.set(this.json, array);
		return this;
	}
}
