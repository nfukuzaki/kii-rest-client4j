package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiNotFoundException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiNotFoundException(String message) {
		super(message, 404, null, null);
	}
	public KiiNotFoundException(JsonObject body, Headers httpHeaders) {
		super(null, 404, body, httpHeaders);
	}
	public KiiNotFoundException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 404, body, httpHeaders);
	}
}
