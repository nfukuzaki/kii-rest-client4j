package com.kii.cloud.model;

public class KiiThing extends KiiCustomableJsonModel<KiiThing> implements KiiCredentialsContainer {
	@Override
	public String getAccessToken() {
		return null;
	}
	@Override
	public String getRefreshToken() {
		return null;
	}
}
