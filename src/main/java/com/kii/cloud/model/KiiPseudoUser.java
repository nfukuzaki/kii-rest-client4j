package com.kii.cloud.model;

import com.kii.cloud.util.GsonUtils;

public class KiiPseudoUser extends KiiUser {
	public String getCountry() {
		return GsonUtils.getString(this.json, PROPERTY_COUNTRY);
	}
	public void setCountry(String country) {
		this.json.addProperty(PROPERTY_COUNTRY, country);
	}
	public String getDisplayName() {
		return GsonUtils.getString(this.json, PROPERTY_DISPLAY_NAME);
	}
	public void setDisplayName(String displayName) {
		this.json.addProperty(PROPERTY_DISPLAY_NAME, displayName);
	}
	@Override
	public boolean isPseudo() {
		return true;
	}
}
