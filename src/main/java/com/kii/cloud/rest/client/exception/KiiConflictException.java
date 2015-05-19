package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;

public class KiiConflictException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiConflictException(String message) {
		super(message, 409, null);
	}
	public KiiConflictException(JsonObject body) {
		super(null, 409, body);
	}
}
