package com.kii.cloud.model;

import com.kii.cloud.util.GsonUtils;

public class KiiNormalUser extends KiiUser {
	
	public String getUsername() {
		return GsonUtils.getString(this.json, PROPERTY_USERNAME);
	}
	public KiiNormalUser setUsername(String username) {
		this.json.addProperty(PROPERTY_USERNAME, username);
		return this;
	}
	public String getEmail() {
		return GsonUtils.getString(this.json, PROPERTY_EMAIL_ADDRESS);
	}
	public KiiNormalUser setEmail(String email) {
		this.json.addProperty(PROPERTY_EMAIL_ADDRESS, email);
		return this;
	}
	public String getPhone() {
		return GsonUtils.getString(this.json, PROPERTY_PHONE_NUMBER);
	}
	public KiiNormalUser setPhone(String phone) {
		this.json.addProperty(PROPERTY_PHONE_NUMBER, phone);
		return this;
	}
	public String getCountry() {
		return GsonUtils.getString(this.json, PROPERTY_COUNTRY);
	}
	public KiiNormalUser setCountry(String country) {
		this.json.addProperty(PROPERTY_COUNTRY, country);
		return this;
	}
	public String getDisplayName() {
		return GsonUtils.getString(this.json, PROPERTY_DISPLAY_NAME);
	}
	public KiiNormalUser setDisplayName(String displayName) {
		this.json.addProperty(PROPERTY_DISPLAY_NAME, displayName);
		return this;
	}
	public String getIdentifier() {
		if (json.has(PROPERTY_USERNAME)) {
			return this.getUsername();
		}
		if (json.has(PROPERTY_EMAIL_ADDRESS)) {
			return this.getEmail();
		}
		if (json.has(PROPERTY_PHONE_NUMBER)) {
			return this.getPhone();
		}
		return null;
	}
	
	@Override
	public boolean isPseudo() {
		return false;
	}
}
