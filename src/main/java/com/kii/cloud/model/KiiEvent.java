package com.kii.cloud.model;

public class KiiEvent extends KiiCustomableJsonModel<KiiEvent> {
	public static final KiiJsonProperty PROPERTY_TYPE = new KiiJsonProperty("_type");
	public static final KiiJsonProperty PROPERTY_DEVICE_ID = new KiiJsonProperty("_deviceID");
	public static final KiiJsonProperty PROPERTY_TRIGGERED_AT = new KiiJsonProperty("_triggeredAt");
	public static final KiiJsonProperty PROPERTY_UPLOADED_AT = new KiiJsonProperty("_uploadedAt");
	
	public KiiEvent() {
	}
	public String getType() {
		return PROPERTY_TYPE.getString(this.json);
	}
	public KiiEvent setType(String type) {
		this.json.addProperty(PROPERTY_TYPE.getName(), type);
		return this;
	}
	public String getDeviceID() {
		return PROPERTY_DEVICE_ID.getString(this.json);
	}
	public KiiEvent setDeviceID(String deviceID) {
		this.json.addProperty(PROPERTY_DEVICE_ID.getName(), deviceID);
		return this;
	}
	public Long getTriggeredAt() {
		return PROPERTY_TRIGGERED_AT.getLong(this.json);
	}
	public KiiEvent setTriggeredAt(Long triggeredAt) {
		this.json.addProperty(PROPERTY_TRIGGERED_AT.getName(), triggeredAt);
		return this;
	}
	public Long getUploadedAt() {
		return PROPERTY_UPLOADED_AT.getLong(this.json);
	}
	public KiiEvent setUploadedAt(Long uploadedAt) {
		this.json.addProperty(PROPERTY_UPLOADED_AT.getName(), uploadedAt);
		return this;
	}
}
