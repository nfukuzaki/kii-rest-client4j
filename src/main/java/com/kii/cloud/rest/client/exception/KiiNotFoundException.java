package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiNotFoundException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiNotFoundException(String message) {
		super(message, 404, null);
	}
	public KiiNotFoundException(JsonObject body) {
		super(null, 404, body);
	}
	public KiiNotFoundException(String message, JsonObject body) {
		super(message, 404, body);
	}
}
