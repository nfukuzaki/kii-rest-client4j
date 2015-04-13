package com.kii.cloud.model.analytics;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiAggregationGroupByRule extends KiiJsonModel {
	
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<String> PROPERTY_LABEL = new KiiJsonProperty<String>(String.class, "label");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	
	public KiiAggregationGroupByRule() {
	}
	public KiiAggregationGroupByRule(JsonObject json) {
		super(json);
	}
	
	public String getName() {
		return PROPERTY_NAME.get(this.json);
	}
	public KiiAggregationGroupByRule setName(String name) {
		PROPERTY_NAME.set(this.json, name);
		return this;
	}
	public String getLabel() {
		return PROPERTY_LABEL.get(this.json);
	}
	public KiiAggregationGroupByRule setLabel(String label) {
		PROPERTY_LABEL.set(this.json, label);
		return this;
	}
	public FieldType getType() {
		if (PROPERTY_TYPE.has(this.json)) {
			return Enum.valueOf(FieldType.class, PROPERTY_TYPE.get(this.json));
		}
		return null;
	}
	public KiiAggregationGroupByRule setType(FieldType fieldType) {
		PROPERTY_TYPE.set(this.json, fieldType.name());
		return this;
	}

}
