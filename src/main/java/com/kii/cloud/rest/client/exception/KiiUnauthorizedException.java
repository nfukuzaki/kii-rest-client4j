package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiUnauthorizedException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiUnauthorizedException(String message) {
		super(message, 401, null, null);
	}
	public KiiUnauthorizedException(JsonObject body, Headers httpHeaders) {
		super(null, 401, body, httpHeaders);
	}
	public KiiUnauthorizedException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 401, body, httpHeaders);
	}

}
