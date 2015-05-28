package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiConflictException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiConflictException(String message) {
		super(message, 409, null, null);
	}
	public KiiConflictException(JsonObject body, Headers httpHeaders) {
		super(null, 409, body, httpHeaders);
	}
	public KiiConflictException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 409, body, httpHeaders);
	}
}
