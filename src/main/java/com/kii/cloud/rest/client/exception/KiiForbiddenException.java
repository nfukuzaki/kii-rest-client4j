package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiForbiddenException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiForbiddenException(String message) {
		super(message, 403, null);
	}
	public KiiForbiddenException(JsonObject body) {
		super(null, 403, body);
	}
}
