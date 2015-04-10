package com.kii.cloud.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class KiiGroupedAnalyticsResult extends KiiJsonModel {
	
	public static final KiiJsonProperty PROPERTY_SNAPSHOTS = new KiiJsonProperty("snapshots");
	public static final KiiJsonProperty PROPERTY_NAME = new KiiJsonProperty("name");
	public static final KiiJsonProperty PROPERTY_DATA = new KiiJsonProperty("data");
	public static final KiiJsonProperty PROPERTY_POINT_START = new KiiJsonProperty("pointStart");
	public static final KiiJsonProperty PROPERTY_POINT_INTERVAL = new KiiJsonProperty("pointInterval");
	
	public KiiGroupedAnalyticsResult(JsonObject json) {
		super(json);
	}
	
	public static class Snapshot extends KiiJsonModel {
		public Snapshot(JsonObject json) {
			super(json);
		}
		public String getName() {
			return PROPERTY_NAME.getString(this.json);
		}
		public long[] getData() {
			JsonArray array = PROPERTY_POINT_START.getJsonArray(this.json);
			return null;
		}
		public long getPointStart() {
			return PROPERTY_POINT_START.getLong(this.json);
		}
		public int getPointInterval() {
			return PROPERTY_POINT_INTERVAL.getInt(this.json);
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
