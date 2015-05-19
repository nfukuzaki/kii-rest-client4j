package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiBadRequestException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiBadRequestException(String message) {
		super(message, 400, null);
	}
	public KiiBadRequestException(JsonObject body) {
		super(null, 400, body);
	}
}
