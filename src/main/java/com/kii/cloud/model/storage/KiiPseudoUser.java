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
		this.json.addProperty(PROPERTY_COUNTRY.getName(), country);
		return this;
	}
	public String getDisplayName() {
		return PROPERTY_DISPLAY_NAME.get(this.json);
	}
	public KiiPseudoUser setDisplayName(String displayName) {
		this.json.addProperty(PROPERTY_DISPLAY_NAME.getName(), displayName);
		return this;
	}
	@Override
	public boolean isPseudo() {
		return true;
	}
}
