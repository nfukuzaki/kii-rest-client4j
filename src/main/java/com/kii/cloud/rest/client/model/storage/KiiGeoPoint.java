package com.kii.cloud.rest.client.model.storage;

import com.google.gson.JsonObject;
import com.kii.cloud.rest.client.util.GsonUtils;
import com.kii.cloud.rest.client.util.StringUtils;

public class KiiGeoPoint {
	private final double latitude;
	private final double longitude;
	
	public KiiGeoPoint(double latitude, double longitude) {
		if (!inRange(-90, +90, latitude)) {
			throw new IllegalArgumentException("latitude is out of range. should be in -90 to +90");
		}
		if (!inRange(-180, +180, longitude)) {
			throw new IllegalArgumentException("longitude format is out of range. should be in -180 to +180");
		}
		this.latitude = latitude;
		this.longitude = longitude;
	}
	public double getLatitude() {
		return latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public JsonObject toJson() {
		JsonObject json = new JsonObject();
		json.addProperty("_type", "point");
		json.addProperty("lat", this.latitude);
		json.addProperty("lon", this.longitude);
		return json;
	}
	private boolean inRange(double min, double max, double number) {
		return (!Double.isNaN(number) && number > min && number < max) ? true : false;
	}
	public static KiiGeoPoint fromJson(JsonObject json) {
		if (StringUtils.equals("point", GsonUtils.getString(json, "_type"))) {
			Double lat = GsonUtils.getDouble(json, "lat");
			Double lon = GsonUtils.getDouble(json, "lon");
			if (lat != null && lon != null) {
				return new KiiGeoPoint(lat, lon);
			}
		}
		return null;
	}
}
