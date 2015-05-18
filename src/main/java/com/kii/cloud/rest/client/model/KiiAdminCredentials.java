package com.kii.cloud.rest.client.model;

import com.google.gson.JsonObject;

public class KiiAdminCredentials extends KiiUserCredentials {
	public KiiAdminCredentials(JsonObject json) {
		super(json);
	}
	@Override
	public boolean isAdmin() {
		return true;
	}
}
