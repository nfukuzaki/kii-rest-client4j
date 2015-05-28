package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiForbiddenException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiForbiddenException(String message) {
		super(message, 403, null, null);
	}
	public KiiForbiddenException(JsonObject body, Headers httpHeaders) {
		super(null, 403, body, httpHeaders);
	}
	public KiiForbiddenException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 403, body, httpHeaders);
	}
}
