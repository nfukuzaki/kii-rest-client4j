package com.kii.cloud.model.analytics;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiGroupedAnalyticsResult extends KiiJsonModel implements KiiAnalyticsResult{
	
	public static final KiiJsonProperty<JsonArray> PROPERTY_SNAPSHOTS = new KiiJsonProperty<JsonArray>(JsonArray.class, "snapshots");
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<JsonArray> PROPERTY_DATA = new KiiJsonProperty<JsonArray>(JsonArray.class, "data");
	public static final KiiJsonProperty<Long> PROPERTY_POINT_START = new KiiJsonProperty<Long>(Long.class, "pointStart");
	public static final KiiJsonProperty<Integer> PROPERTY_POINT_INTERVAL = new KiiJsonProperty<Integer>(Integer.class, "pointInterval");
	
	public KiiGroupedAnalyticsResult(JsonObject json) {
		super(json);
	}
	public List<Snapshot> getSnapshots() {
		List<Snapshot> snapshots = new ArrayList<Snapshot>();
		JsonArray array = PROPERTY_SNAPSHOTS.get(this.json);
		for (int i = 0; i < array.size(); i++) {
			snapshots.add(new Snapshot(array.get(i).getAsJsonObject()));
		}
		return snapshots;
	}
	public static class Snapshot extends KiiJsonModel {
		public Snapshot(JsonObject json) {
			super(json);
		}
		public String getName() {
			return PROPERTY_NAME.get(this.json);
		}
		public JsonArray getData() {
			return PROPERTY_DATA.get(this.json);
		}
		public long getPointStart() {
			return PROPERTY_POINT_START.get(this.json);
		}
		public int getPointInterval() {
			return PROPERTY_POINT_INTERVAL.get(this.json);
		}
	}
	@Override
	public ResultType getResultType() {
		return ResultType.GroupedResult;
	}
}
