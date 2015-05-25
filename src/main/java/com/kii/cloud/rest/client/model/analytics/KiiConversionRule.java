package com.kii.cloud.rest.client.model.analytics;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;
import com.kii.cloud.rest.client.model.KiiScope;

public class KiiConversionRule extends KiiJsonModel {
	
	public enum Target {
		BUCKET,
		OBJECT
	}
	
	public static final KiiJsonProperty<Long> PROPERTY_ID = new KiiJsonProperty<Long>(Long.class, "_id");
	public static final KiiJsonProperty<String> PROPERTY_SCOPE = new KiiJsonProperty<String>(String.class, "scope");
	public static final KiiJsonProperty<String> PROPERTY_BUCKET = new KiiJsonProperty<String>(String.class, "bucket");
	public static final KiiJsonProperty<String> PROPERTY_TARGET = new KiiJsonProperty<String>(String.class, "target");
	public static final KiiJsonProperty<JsonArray> PROPERTY_DERIVED = new KiiJsonProperty<JsonArray>(JsonArray.class, "derived");
	
	public KiiConversionRule() {
	}
	public KiiConversionRule(JsonObject json) {
		super(json);
	}
	public Long getID() {
		return PROPERTY_ID.get(this.json);
	}
	public KiiConversionRule setID(Long id) {
		PROPERTY_ID.set(this.json, id);
		return this;
	}
	public KiiScope getScope() {
		if (PROPERTY_SCOPE.has(this.json)) {
			return Enum.valueOf(KiiScope.class, PROPERTY_SCOPE.get(this.json));
		}
		return null;
	}
	public KiiConversionRule setScope(KiiScope scope) {
		PROPERTY_SCOPE.set(this.json, scope.name());
		return this;
	}
	public String getBucket() {
		return PROPERTY_BUCKET.get(this.json);
	}
	public KiiConversionRule setBucket(String bucket) {
		PROPERTY_BUCKET.set(this.json, bucket);
		return this;
	}
	public Target getTarget() {
		if (PROPERTY_TARGET.has(this.json)) {
			return Enum.valueOf(Target.class, PROPERTY_TARGET.get(this.json));
		}
		return null;
	}
	public KiiConversionRule setTarget(Target target) {
		PROPERTY_TARGET.set(this.json, target.name());
		return this;
	}
	public KiiConversionRule addMappingRule(KiiConversionMappingRule mappingRule) {
		JsonArray array = PROPERTY_DERIVED.get(this.json);
		if (array == null) {
			array = new JsonArray();
		}
		array.add(mappingRule.getJsonObject());
		PROPERTY_DERIVED.set(this.json, array);
		return this;
	}
	public List<KiiConversionMappingRule> getMappingRules() {
		List<KiiConversionMappingRule> result = new ArrayList<KiiConversionMappingRule>();
		if (PROPERTY_DERIVED.has(this.json)) {
			JsonArray array = PROPERTY_DERIVED.get(this.json);
			for (int i = 0; i < array.size(); i++) {
				result.add(new KiiConversionMappingRule(array.get(i).getAsJsonObject()));
			}
		}
		return result;
	}
}
