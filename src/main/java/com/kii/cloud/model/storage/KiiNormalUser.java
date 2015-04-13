package com.kii.cloud.model.storage;

import com.google.gson.JsonObject;

public class KiiNormalUser extends KiiUser {
	
	public KiiNormalUser() {
	}
	public KiiNormalUser(JsonObject json) {
		super(json);
	}
	
	public String getUsername() {
		return PROPERTY_USERNAME.get(this.json);
	}
	public KiiNormalUser setUsername(String username) {
		this.json.addProperty(PROPERTY_USERNAME.getName(), username);
		return this;
	}
	public String getEmail() {
		return PROPERTY_EMAIL_ADDRESS.get(this.json);
	}
	public KiiNormalUser setEmail(String email) {
		this.json.addProperty(PROPERTY_EMAIL_ADDRESS.getName(), email);
		return this;
	}
	public String getPhone() {
		return PROPERTY_PHONE_NUMBER.get(this.json);
	}
	public KiiNormalUser setPhone(String phone) {
		this.json.addProperty(PROPERTY_PHONE_NUMBER.getName(), phone);
		return this;
	}
	public String getCountry() {
		return PROPERTY_COUNTRY.get(this.json);
	}
	public KiiNormalUser setCountry(String country) {
		this.json.addProperty(PROPERTY_COUNTRY.getName(), country);
		return this;
	}
	public String getDisplayName() {
		return PROPERTY_DISPLAY_NAME.get(this.json);
	}
	public KiiNormalUser setDisplayName(String displayName) {
		this.json.addProperty(PROPERTY_DISPLAY_NAME.getName(), displayName);
		return this;
	}
	public String getIdentifier() {
		if (PROPERTY_USERNAME.has(this.json)) {
			return this.getUsername();
		}
		if (PROPERTY_EMAIL_ADDRESS.has(this.json)) {
			return this.getEmail();
		}
		if (PROPERTY_PHONE_NUMBER.has(this.json)) {
			return this.getPhone();
		}
		return null;
	}
	
	@Override
	public boolean isPseudo() {
		return false;
	}
}
