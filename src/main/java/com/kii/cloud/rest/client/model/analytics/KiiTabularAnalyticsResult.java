package com.kii.cloud.rest.client.model.analytics;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.model.KiiJsonModel;
import com.kii.cloud.rest.client.model.KiiJsonProperty;

public class KiiTabularAnalyticsResult extends KiiJsonModel implements KiiAnalyticsResult {
	
	public static final KiiJsonProperty<JsonArray> PROPERTY_LABELS = new KiiJsonProperty<JsonArray>(JsonArray.class, "labels");
	public static final KiiJsonProperty<String> PROPERTY_LABEL = new KiiJsonProperty<String>(String.class, "label");
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "type");
	public static final KiiJsonProperty<JsonArray> PROPERTY_SNAPSHOTS = new KiiJsonProperty<JsonArray>(JsonArray.class, "snapshots");
	public static final KiiJsonProperty<Long> PROPERTY_CREATED_AT = new KiiJsonProperty<Long>(Long.class, "createdAt");
	public static final KiiJsonProperty<JsonArray> PROPERTY_DATA = new KiiJsonProperty<JsonArray>(JsonArray.class, "data");
	
	public KiiTabularAnalyticsResult(JsonObject json) {
		super(json);
	}
	public List<Label> getLabels() {
		List<Label> labels = new ArrayList<Label>();
		JsonArray array = PROPERTY_LABELS.get(this.json);
		for (int i = 0; i < array.size(); i++) {
			labels.add(new Label(array.get(i).getAsJsonObject()));
		}
		return labels;
	}
	@Override
	public ResultType getResultType() {
		return ResultType.TabularResult;
	}
	public static class Label extends KiiJsonModel {
		public Label(JsonObject json) {
			super(json);
		}
		public String getLabel() {
			return PROPERTY_LABEL.get(this.json);
		}
		public String getType() {
			return PROPERTY_TYPE.get(this.json);
		}
	}
	public static class Snapshot extends KiiJsonModel {
		public Snapshot(JsonObject json) {
			super(json);
		}
		public Long getCreatedAt() {
			return PROPERTY_CREATED_AT.get(this.json);
		}
		public JsonArray getData() {
			return PROPERTY_DATA.get(this.json);
		}
	}
}
