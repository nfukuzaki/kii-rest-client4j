package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiUnauthorizedException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiUnauthorizedException(String message) {
		super(message, 401, null);
	}
	public KiiUnauthorizedException(JsonObject body) {
		super(null, 401, body);
	}
	public KiiUnauthorizedException(String message, JsonObject body) {
		super(message, 401, body);
	}

}
