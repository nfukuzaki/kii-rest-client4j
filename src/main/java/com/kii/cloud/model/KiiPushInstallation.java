package com.kii.cloud.model;

import com.google.gson.JsonObject;

public class KiiPushInstallation extends KiiJsonModel {
	public enum InstallationType {
		ANDROID,
		IOS,
		JPUSH,
		MQTT
	}
	
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_ID = new KiiJsonProperty<String>(String.class, "installationID");
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_REGISTRATION_ID = new KiiJsonProperty<String>(String.class, "installationRegistrationID");
	public static final KiiJsonProperty<String> PROPERTY_INSTALLATION_TYPE = new KiiJsonProperty<String>(String.class, "installationType");
	public static final KiiJsonProperty<String> PROPERTY_USER_ID = new KiiJsonProperty<String>(String.class, "userID");
	public static final KiiJsonProperty<String> PROPERTY_THING_ID = new KiiJsonProperty<String>(String.class, "thingID");
	public static final KiiJsonProperty<Boolean> PROPERTY_DEVELOPMENT = new KiiJsonProperty<Boolean>(Boolean.class, "development");
	
	
	public KiiPushInstallation() {
	}
	public KiiPushInstallation(JsonObject json) {
		super(json);
	}
	
	public String getInstallationID() {
		return PROPERTY_INSTALLATION_ID.get(this.json);
	}
	public KiiPushInstallation setInstallationID(String installationID) {
		this.json.addProperty(PROPERTY_INSTALLATION_ID.getName(), installationID);
		return this;
	}
	public String getInstallationRegistrationID() {
		return PROPERTY_INSTALLATION_REGISTRATION_ID.get(this.json);
	}
	public KiiPushInstallation setInstallationRegistrationID(String installationRegistrationID) {
		this.json.addProperty(PROPERTY_INSTALLATION_REGISTRATION_ID.getName(), installationRegistrationID);
		return this;
	}
	public InstallationType getEnstallationType() {
		String installationType = PROPERTY_INSTALLATION_TYPE.get(this.json);
		return Enum.valueOf(InstallationType.class, installationType);
	}
	public KiiPushInstallation setInstallationType(InstallationType installationType) {
		this.json.addProperty(PROPERTY_INSTALLATION_TYPE.getName(), installationType.name());
		return this;
	}
	public String getUserID() {
		return PROPERTY_USER_ID.get(this.json);
	}
	public KiiPushInstallation setUserID(String userID) {
		this.json.addProperty(PROPERTY_USER_ID.getName(), userID);
		return this;
	}
	public String getThingID() {
		return PROPERTY_THING_ID.get(this.json);
	}
	public KiiPushInstallation setThingID(String thingID) {
		this.json.addProperty(PROPERTY_THING_ID.getName(), thingID);
		return this;
	}
	public boolean getDevelopment() {
		if (!PROPERTY_DEVELOPMENT.has(this.json)) {
			return false;
		}
		return PROPERTY_DEVELOPMENT.get(this.json);
	}
	public KiiPushInstallation setDevelopment(boolean development) {
		this.json.addProperty(PROPERTY_DEVELOPMENT.getName(), development);
		return this;
	}
}
