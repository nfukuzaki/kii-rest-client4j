package com.kii.cloud.model.analytics;

import com.kii.cloud.model.KiiCustomableJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

public class KiiEvent extends KiiCustomableJsonModel<KiiEvent> {
	public static final KiiJsonProperty<String> PROPERTY_TYPE = new KiiJsonProperty<String>(String.class, "_type");
	public static final KiiJsonProperty<String> PROPERTY_DEVICE_ID = new KiiJsonProperty<String>(String.class, "_deviceID");
	public static final KiiJsonProperty<Long> PROPERTY_TRIGGERED_AT = new KiiJsonProperty<Long>(Long.class, "_triggeredAt");
	public static final KiiJsonProperty<Long> PROPERTY_UPLOADED_AT = new KiiJsonProperty<Long>(Long.class, "_uploadedAt");
	
	public KiiEvent() {
	}
	public String getType() {
		return PROPERTY_TYPE.get(this.json);
	}
	public KiiEvent setType(String type) {
		PROPERTY_TYPE.set(this.json, type);
		return this;
	}
	public String getDeviceID() {
		return PROPERTY_DEVICE_ID.get(this.json);
	}
	public KiiEvent setDeviceID(String deviceID) {
		PROPERTY_DEVICE_ID.set(this.json, deviceID);
		return this;
	}
	public Long getTriggeredAt() {
		return PROPERTY_TRIGGERED_AT.get(this.json);
	}
	public KiiEvent setTriggeredAt(Long triggeredAt) {
		PROPERTY_TRIGGERED_AT.set(this.json, triggeredAt);
		return this;
	}
	public Long getUploadedAt() {
		return PROPERTY_UPLOADED_AT.get(this.json);
	}
	public KiiEvent setUploadedAt(Long uploadedAt) {
		PROPERTY_UPLOADED_AT.set(this.json, uploadedAt);
		return this;
	}
}
