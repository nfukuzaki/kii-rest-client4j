package com.kii.cloud.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KiiGroupedAnalyticsResult extends KiiJsonModel {
	
	public static final KiiJsonProperty<JsonArray> PROPERTY_SNAPSHOTS = new KiiJsonProperty<JsonArray>(JsonArray.class, "snapshots");
	public static final KiiJsonProperty<String> PROPERTY_NAME = new KiiJsonProperty<String>(String.class, "name");
	public static final KiiJsonProperty<JsonArray> PROPERTY_DATA = new KiiJsonProperty<JsonArray>(JsonArray.class, "data");
	public static final KiiJsonProperty<Long> PROPERTY_POINT_START = new KiiJsonProperty<Long>(Long.class, "pointStart");
	public static final KiiJsonProperty<Integer> PROPERTY_POINT_INTERVAL = new KiiJsonProperty<Integer>(Integer.class, "pointInterval");
	
	public KiiGroupedAnalyticsResult(JsonObject json) {
		super(json);
	}
	
	public static class Snapshot extends KiiJsonModel {
		public Snapshot(JsonObject json) {
			super(json);
		}
		public String getName() {
			return PROPERTY_NAME.get(this.json);
		}
		public long[] getData() {
			JsonArray array = PROPERTY_DATA.get(this.json);
			return null;
		}
		public long getPointStart() {
			return PROPERTY_POINT_START.get(this.json);
		}
		public int getPointInterval() {
			return PROPERTY_POINT_INTERVAL.get(this.json);
		}
	}
	
	
//	{
//		  "snapshots" : [ {
//		    "name" : "New York",
//		    "data" : [ 10, 10, 11,  (...snip...)   , 12 ],
//		    "pointStart" : 1357862400000,
//		    "pointInterval" : 86400000
//		  }, {
//		    "name" : "Tokyo",
//		    "data" : [ 5, 8, 10,  (...snip...)   , 15],
//		    "pointStart" : 1357862400000,
//		    "pointInterval" : 86400000
//		 }, {
}
