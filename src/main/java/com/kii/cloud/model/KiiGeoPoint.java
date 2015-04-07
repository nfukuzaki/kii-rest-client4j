package com.kii.cloud.model;

import com.google.gson.JsonObject;

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
}
