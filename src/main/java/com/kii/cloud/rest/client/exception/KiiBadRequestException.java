package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiBadRequestException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiBadRequestException(String message) {
		super(message, 400, null, null);
	}
	public KiiBadRequestException(JsonObject body, Headers httpHeaders) {
		super(null, 400, body, httpHeaders);
	}
	public KiiBadRequestException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 400, body, httpHeaders);
	}
}
