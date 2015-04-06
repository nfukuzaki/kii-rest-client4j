package com.kii.cloud.model;

public class KiiPseudoUser extends KiiUser {
	public String getCountry() {
		return PROPERTY_COUNTRY.getString(this.json);
	}
	public void setCountry(String country) {
		this.json.addProperty(PROPERTY_COUNTRY.getName(), country);
	}
	public String getDisplayName() {
		return PROPERTY_DISPLAY_NAME.getString(this.json);
	}
	public void setDisplayName(String displayName) {
		this.json.addProperty(PROPERTY_DISPLAY_NAME.getName(), displayName);
	}
	@Override
	public boolean isPseudo() {
		return true;
	}
}
