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
		PROPERTY_USERNAME.set(this.json, username);
		return this;
	}
	public String getEmail() {
		return PROPERTY_EMAIL_ADDRESS.get(this.json);
	}
	public KiiNormalUser setEmail(String email) {
		PROPERTY_EMAIL_ADDRESS.set(this.json, email);
		return this;
	}
	public String getPhone() {
		return PROPERTY_PHONE_NUMBER.get(this.json);
	}
	public KiiNormalUser setPhone(String phone) {
		PROPERTY_PHONE_NUMBER.set(this.json, phone);
		return this;
	}
	public String getCountry() {
		return PROPERTY_COUNTRY.get(this.json);
	}
	public KiiNormalUser setCountry(String country) {
		PROPERTY_COUNTRY.set(this.json, country);
		return this;
	}
	public String getDisplayName() {
		return PROPERTY_DISPLAY_NAME.get(this.json);
	}
	public KiiNormalUser setDisplayName(String displayName) {
		PROPERTY_DISPLAY_NAME.set(this.json, displayName);
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
	public boolean isEmailAddressVerified() {
		return PROPERTY_EMAIL_ADDRESS_VERIFIED.get(this.json);
	}
	public boolean isPhoneNumberVerified() {
		return PROPERTY_PHONE_NUMBER_VERIFIED.get(this.json);
	}
	
	@Override
	public boolean isPseudo() {
		return false;
	}
}
