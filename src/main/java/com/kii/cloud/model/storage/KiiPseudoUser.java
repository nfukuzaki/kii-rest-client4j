package com.kii.cloud.model.storage;

import com.google.gson.JsonObject;

public class KiiPseudoUser extends KiiUser {
	
	public KiiPseudoUser() {
	}
	public KiiPseudoUser(JsonObject json) {
		super(json);
	}
	
	public String getCountry() {
		return PROPERTY_COUNTRY.get(this.json);
	}
	public KiiPseudoUser setCountry(String country) {
		PROPERTY_COUNTRY.set(this.json, country);
		return this;
	}
	public String getDisplayName() {
		return PROPERTY_DISPLAY_NAME.get(this.json);
	}
	public KiiPseudoUser setDisplayName(String displayName) {
		PROPERTY_DISPLAY_NAME.set(this.json, displayName);
		return this;
	}
	@Override
	public boolean isPseudo() {
		return true;
	}
}
