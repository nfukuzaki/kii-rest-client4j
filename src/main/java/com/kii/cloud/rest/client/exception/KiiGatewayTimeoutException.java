package com.kii.cloud.rest.client.exception;

import com.google.gson.JsonObject;
import com.squareup.okhttp.Headers;

public class KiiGatewayTimeoutException extends KiiRestException {
	private static final long serialVersionUID = 1L;
	
	public KiiGatewayTimeoutException(String message) {
		super(message, 504, null, null);
	}
	public KiiGatewayTimeoutException(JsonObject body, Headers httpHeaders) {
		super(null, 504, body, httpHeaders);
	}
	public KiiGatewayTimeoutException(String message, JsonObject body, Headers httpHeaders) {
		super(message, 504, body, httpHeaders);
	}
}
