package com.kii.cloud.model.push;

import com.google.gson.JsonObject;
import com.kii.cloud.model.KiiJsonModel;
import com.kii.cloud.model.KiiJsonProperty;

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
		PROPERTY_INSTALLATION_ID.set(this.json, installationID);
		return this;
	}
	public String getInstallationRegistrationID() {
		return PROPERTY_INSTALLATION_REGISTRATION_ID.get(this.json);
	}
	public KiiPushInstallation setInstallationRegistrationID(String installationRegistrationID) {
		PROPERTY_INSTALLATION_REGISTRATION_ID.set(this.json, installationRegistrationID);
		return this;
	}
	public InstallationType getEnstallationType() {
		if (PROPERTY_INSTALLATION_TYPE.has(this.json)) {
			return Enum.valueOf(InstallationType.class, PROPERTY_INSTALLATION_TYPE.get(this.json));
		}
		return null;
	}
	public KiiPushInstallation setInstallationType(InstallationType installationType) {
		PROPERTY_INSTALLATION_TYPE.set(this.json, installationType.name());
		return this;
	}
	public String getUserID() {
		return PROPERTY_USER_ID.get(this.json);
	}
	public KiiPushInstallation setUserID(String userID) {
		PROPERTY_USER_ID.set(this.json, userID);
		return this;
	}
	public String getThingID() {
		return PROPERTY_THING_ID.get(this.json);
	}
	public KiiPushInstallation setThingID(String thingID) {
		PROPERTY_THING_ID.set(this.json, thingID);
		return this;
	}
	public boolean getDevelopment() {
		if (!PROPERTY_DEVELOPMENT.has(this.json)) {
			return false;
		}
		return PROPERTY_DEVELOPMENT.get(this.json);
	}
	public KiiPushInstallation setDevelopment(boolean development) {
		PROPERTY_DEVELOPMENT.set(this.json, development);
		return this;
	}
}
