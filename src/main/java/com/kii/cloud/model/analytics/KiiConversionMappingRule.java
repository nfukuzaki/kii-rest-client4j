package com.kii.cloud.model.analytics;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiConversionMappingRule extends KiiJsonModel {
	
	public enum FieldType {
		TINYINT,
		SMALLINT,
		INT,
		BIGINT,
		BOOLEAN,
		FLOAT,
		STRING
	}
	
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<String> PROPERTY_SOURCE = new KiiJsonProperty<String>(String.class, "source");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	
	public KiiConversionMappingRule() {
	}
	public KiiConversionMappingRule(JsonObject json) {
		super(json);
	}
	
	public String getName() {
		return PROPERTY_NAME.get(this.json);
	}
	public KiiConversionMappingRule setName(String name) {
		PROPERTY_NAME.set(this.json, name);
		return this;
	}
	public String getSource() {
		return PROPERTY_SOURCE.get(this.json);
	}
	public KiiConversionMappingRule setSource(String source) {
		PROPERTY_SOURCE.set(this.json, source);
		return this;
	}
	public FieldType getType() {
		if (PROPERTY_TYPE.has(this.json)) {
			return Enum.valueOf(FieldType.class, PROPERTY_TYPE.get(this.json));
		}
		return null;
	}
	public KiiConversionMappingRule setType(FieldType fieldType) {
		PROPERTY_TYPE.set(this.json, fieldType.name());
		return this;
	}
}