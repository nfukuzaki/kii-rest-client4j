package com.kii.cloud.model;

public class KiiThing extends KiiCustomableJsonModel<KiiThing> implements KiiCredentialsContainer {
	@Override
	public String getID() {
		return null;
	}
	@Override
	public String getAccessToken() {
		return null;
	}
	@Override
	public String getRefreshToken() {
		return null;
	}
	@Override
	public boolean isAdmin() {
		return false;
	}
}
